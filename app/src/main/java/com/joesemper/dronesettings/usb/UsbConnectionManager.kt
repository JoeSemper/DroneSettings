package com.joesemper.dronesettings.usb

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.hoho.android.usbserial.driver.UsbSerialDriver
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import com.hoho.android.usbserial.util.SerialInputOutputManager
import com.joesemper.dronesettings.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

private const val ACTION_USB_PERMISSION = "com.joesemper.dronesettings.USB_PERMISSION"

@Composable
fun rememberUsbConnectionManager(
    context: Context,
    coroutineScope: CoroutineScope
) = remember(context) {
    UsbConnectionManager(context, coroutineScope)
}

sealed class UsbConnectionMassage(val text: String) {
    class System(msg: String) : UsbConnectionMassage(msg)
    class Device(msg: String) : UsbConnectionMassage(msg)
    class Error(msg: String) : UsbConnectionMassage(msg)
}

class UsbConnectionManager(
    private val context: Context,
    private val coroutineScope: CoroutineScope
) {

    private val masages = Channel<UsbConnectionMassage>()

    private val _connection = MutableStateFlow(false)
    val connection = _connection.asStateFlow()

    private var serialPort: UsbSerialPort? = null

    private val activity = context.findActivity()
    private val usbManager: UsbManager =
        activity.getSystemService(Context.USB_SERVICE) as UsbManager

    fun connect() {
        if (!_connection.value){
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

    fun disconnect() {
        closePort()
        onDisconnect()
    }

    fun send(massage: String) {
        if (connection.value) {
            sendToDevice(massage)
        } else {
            sendErrorMassage(R.string.device_not_connected)
        }
    }

    fun subscribeOnMassages() = masages.consumeAsFlow()

    private fun sendToDevice(msg: String) {
        serialPort?.write(msg.toByteArray(), 500)
    }

    private fun setUpConnection(driver: UsbSerialDriver) {
        setUpPort(driver)
        sendSystemMassage(R.string.port_configured)
        setDisconnectListener()
        setPortListener()
        _connection.value = true
        sendSystemMassage(R.string.device_connected)
    }

    private fun setPortListener() {
        if (connection.value) {
            serialPort?.let { port ->
                val listener = object : SerialInputOutputManager.Listener {
                    override fun onNewData(data: ByteArray?) {
                        data?.let {
                            sendDeviceMassage(String(data))
                        }
                    }

                    override fun onRunError(e: Exception?) {
                        e?.let {
                            sendErrorMassage(R.string.port_listener_error)
                        }
                    }
                }
                val ioManager = SerialInputOutputManager(port, listener)
                ioManager.start()
            }
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

    private fun sendErrorMassage(@StringRes msgResId: Int) {
        coroutineScope.launch {
            masages.send(UsbConnectionMassage.Error(context.getString(msgResId)))
        }
    }

    private fun sendSystemMassage(@StringRes msgResId: Int) {
        coroutineScope.launch {
            masages.send(UsbConnectionMassage.System(context.getString(msgResId)))
        }
    }

    private fun sendDeviceMassage(msg: String) {
        coroutineScope.launch {
            masages.send(UsbConnectionMassage.Device(msg))
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

internal fun Context.findActivity(): ComponentActivity {

    var context = this

    while (context is ContextWrapper) {
        if (context is ComponentActivity) return context
        context = context.baseContext
    }

    throw IllegalStateException("Permissions should be called in the context of an Activity")
}