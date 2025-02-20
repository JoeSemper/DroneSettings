package com.joesemper.dronesettings.usb

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.util.Log
import androidx.annotation.StringRes
import com.hoho.android.usbserial.driver.UsbSerialDriver
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import com.hoho.android.usbserial.util.SerialInputOutputManager
import com.joesemper.dronesettings.R
import com.joesemper.dronesettings.utils.unixTimeToTime
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

private const val ACTION_USB_PERMISSION = "com.joesemper.dronesettings.USB_PERMISSION"
private const val STRING_END = '\n'
private const val CARRIAGE_RETURN = '\r'

class UsbConnectionManagerImpl(
    private val context: Context,
    defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : UsbConnectionManager {

    private val _log = MutableStateFlow<List<UsbConnectionMassage>>(mutableListOf())
    override val log = _log.asStateFlow()

    private val _deviceLogFlow = MutableSharedFlow<String>(replay = 0)
    override val deviceLogFlow: SharedFlow<String> = _deviceLogFlow

    private val _connection = MutableStateFlow(false)
    override val connection = _connection.asStateFlow()

    private var serialPort: UsbSerialPort? = null

    private val calendar = Calendar.getInstance()

    private var serialPortQueue = String()

    private val activity = context
    private val usbManager: UsbManager =
        activity.getSystemService(Context.USB_SERVICE) as UsbManager

    private val scope = CoroutineScope(defaultDispatcher)

    private val listener = object : SerialInputOutputManager.Listener {
        override fun onNewData(data: ByteArray?) {
            data?.let {
                val newString = String(data)

                Log.d("Debug", newString)

                serialPortQueue += newString.filter { it != CARRIAGE_RETURN }

                while (serialPortQueue.isNotBlank() && serialPortQueue.contains(STRING_END)) {

                    val response = serialPortQueue.takeWhile { it != STRING_END }

                    if(response.isNotBlank()) {
                        scope.launch {
                            _deviceLogFlow.emit(response)
                        }
                    }

                    serialPortQueue = serialPortQueue.removePrefix(response)

                    while (serialPortQueue.isNotBlank() && serialPortQueue.first() == STRING_END) {
                        serialPortQueue = serialPortQueue.substring(1)
                    }

                }

            }
        }

        override fun onRunError(e: Exception?) {
            e?.let {
                sendErrorMassage(e.message ?: "Port listener error")
            }
        }
    }

    override fun connect() {
        if (!_connection.value) {
            try {
                checkDrivers(usbManager).firstOrNull()?.let { driver ->
                    sendSystemMassage(R.string.driver_found)

                    if (hasPermission(driver)) {
                        setUpConnection(driver)
                    } else {
                        requestPermission(driver)
                    }


                } ?: {
                    onConnectionFailure()
                }

            } catch (e: Throwable) {
                sendErrorMassage(R.string.connection_error)
            }
        }
    }

    override fun disconnect() {
        closePort()
        onDisconnect()
    }

    override fun send(massage: String) {
        if (_connection.value) {
            sendUserMassage(massage)
            sendToDevice(massage)
        } else {
            sendErrorMassage(
                R.string.device_not_connected
            )
        }
    }

    override fun clearLog() {
        _log.value = listOf()
    }

    private fun sendToDevice(msg: String) {
        serialPort?.write(msg.toByteArray(), 500)
    }

    private fun setUpConnection(driver: UsbSerialDriver) {
        serialPortQueue = ""
        setUpPort(driver)
        sendSystemMassage(R.string.port_configured)
        setDisconnectListener()
        setPortListener()
        _connection.value = true
        sendSystemMassage(R.string.device_connected)
    }

    private fun setPortListener() {
        serialPort?.let { port ->
            val ioManager = SerialInputOutputManager(port, listener)
            ioManager.start()
            sendSystemMassage(R.string.listener_attached)
        }
    }

    private val permissionReceiver = PermissionBroadcastReceiver(
        onPermissionResult = { hasPermission ->
            if (hasPermission) {
                sendSystemMassage(R.string.permission_granted)
                checkDrivers(usbManager).firstOrNull()?.let { driver ->
                    setUpConnection(driver)
                }
            } else {
                sendErrorMassage(R.string.permission_denied)
            }
        }
    )

    private fun hasPermission(driver: UsbSerialDriver) = usbManager.hasPermission(driver.device)

    private fun onConnectionFailure() {
        _connection.value = false
        sendErrorMassage(R.string.no_drivers_or_device_not_connected)
    }

    private fun setUpPort(driver: UsbSerialDriver) {
        try {
            val connection = usbManager.openDevice(driver.device)
            driver.ports.firstOrNull()?.let { port ->
                serialPort = port
                port.open(connection)
                port.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE)
            }
        } catch (e: Throwable) {
            sendErrorMassage(R.string.port_setup_failure)
        }
    }

    private fun setDisconnectListener() {
        val filter = IntentFilter(UsbManager.ACTION_USB_DEVICE_DETACHED)
        activity.registerReceiver(disconnectListener, filter)
    }

    private val disconnectListener = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == UsbManager.ACTION_USB_DEVICE_DETACHED) {
                onDisconnect()
            }
        }
    }

    private fun onDisconnect() {
        serialPortQueue = ""
        _connection.value = false
        sendSystemMassage(R.string.device_disconnected)
    }

    private fun closePort() {
        try {
            serialPort?.close()
        } catch (e: Throwable) {
            sendErrorMassage(R.string.error_closing_port)
        }
    }

    private fun checkDrivers(
        usbManager: UsbManager
    ): List<UsbSerialDriver> {
        return UsbSerialProber.getDefaultProber().findAllDrivers(usbManager)
    }

    private fun requestPermission(
        driver: UsbSerialDriver,
    ) {
        val flags = PendingIntent.FLAG_MUTABLE
        val usbPermissionIntent = PendingIntent.getBroadcast(
            activity,
            0,
            Intent(ACTION_USB_PERMISSION),
            flags
        )
        val filter = IntentFilter(ACTION_USB_PERMISSION)
        activity.registerReceiver(permissionReceiver, filter)
        usbManager.requestPermission(driver.device, usbPermissionIntent)
    }

    private fun sendErrorMassage(
        @StringRes msgResId: Int
    ) {
        scope.launch {
            _log.value = _log.value.plus(
                UsbConnectionMassage.Error(
                    msg = context.getString(msgResId),
                    time = unixTimeToTime(calendar.timeInMillis)
                )
            )
        }
    }

    private fun sendErrorMassage(
        msg: String
    ) {
        scope.launch {
            _log.value = _log.value.plus(
                UsbConnectionMassage.Error(
                    msg = msg,
                    time = unixTimeToTime(calendar.timeInMillis)
                )
            )
        }
    }

    private fun sendSystemMassage(
        @StringRes msgResId: Int
    ) {
        scope.launch {
            _log.value = _log.value.plus(
                UsbConnectionMassage.System(
                    msg = context.getString(msgResId),
                    time = unixTimeToTime(calendar.timeInMillis)
                )
            )
        }
    }

    private fun sendSystemMassage(
        msg: String
    ) {
        scope.launch {
            _log.value = _log.value.plus(
                UsbConnectionMassage.System(
                    msg = msg,
                    time = unixTimeToTime(calendar.timeInMillis)
                )
            )
        }
    }

    private fun sendDeviceMassage(
        msg: String
    ) {
        scope.launch {
            _log.value = _log.value.plus(
                UsbConnectionMassage.Device(
                    msg = msg,
                    time = unixTimeToTime(calendar.timeInMillis)
                )
            )
        }
    }

    private fun sendDeviceMassage(
        msg: List<String>
    ) {
        scope.launch {
            _log.value = _log.value.plus(
                msg.map {
                    Log.d("Debug", "Send: $it")
                    UsbConnectionMassage.Device(
                        msg = it,
                        time = unixTimeToTime(calendar.timeInMillis)
                    )
                }

            )


//                UsbConnectionMassage.Device(
//                    msg = it,
//                    time = unixTimeToTime(calendar.timeInMillis)
//                )


        }
    }

    private fun sendUserMassage(msg: String) {
        scope.launch {
            _log.value = _log.value.plus(
                UsbConnectionMassage.User(
                    msg = msg,
                    time = unixTimeToTime(calendar.timeInMillis)
                )
            )
        }
    }

}

private class PermissionBroadcastReceiver(
    val onPermissionResult: (Boolean) -> Unit
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (ACTION_USB_PERMISSION == intent.action) {
            synchronized(this) {
                val device: UsbDevice? = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)

                if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                    device?.apply {
                        onPermissionResult(true)
                    }
                } else {
                    onPermissionResult(false)
                }

            }
        }
    }
}
