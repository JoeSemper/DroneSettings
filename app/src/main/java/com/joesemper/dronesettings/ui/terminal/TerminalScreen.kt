package com.joesemper.dronesettings.ui.terminal

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.hardware.usb.UsbManager.ACTION_USB_DEVICE_ATTACHED
import android.hardware.usb.UsbManager.ACTION_USB_DEVICE_DETACHED
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.hoho.android.usbserial.driver.UsbSerialDriver
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import com.hoho.android.usbserial.util.SerialInputOutputManager
import com.hoho.android.usbserial.util.SerialInputOutputManager.Listener
import com.joesemper.dronesettings.usb.UsbConnectionManager
import com.joesemper.dronesettings.usb.findActivity
import com.joesemper.dronesettings.usb.rememberUsbConnectionManager

private const val ACTION_USB_PERMISSION = "com.joesemper.dronesettings.USB_PERMISSION"

@Composable
fun TerminalScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        TerminalContentScreen(
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun TerminalContentScreen(
    modifier: Modifier = Modifier
) {


    val context = LocalContext.current
    val activity = remember { context.findActivity() }

    val manager: UsbConnectionManager =
        rememberUsbConnectionManager(
            context = context,
            onNewMassage = {}
        )

    val usbManager: UsbManager =
        remember { activity.getSystemService(Context.USB_SERVICE) as UsbManager }

    val port = remember { mutableStateOf<UsbSerialPort?>(null) }
    val driver = remember { mutableStateOf<UsbSerialDriver?>(null) }
    val hasPermission = remember { mutableStateOf(false) }
    val connected = remember { mutableStateOf(false) }

    val log = remember { mutableStateListOf<String>() }

    usbManager.deviceList


    val usbReceiver = remember {
        object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                if (ACTION_USB_PERMISSION == intent.action) {
                    synchronized(this) {
                        val device: UsbDevice? = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)

                        if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                            device?.apply {
                                hasPermission.value = true
                            }
                        } else {
                            log.add("Permission denied")
                        }
                    }
                }
            }
        }
    }

    val usbReceiverAttached = remember {
        object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == ACTION_USB_DEVICE_ATTACHED) {
                    synchronized(this) {

                        log.add("Device attached")
                    }

                }
            }
        }
    }

    val usbReceiverDetached = remember {
        object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == ACTION_USB_DEVICE_DETACHED) {
                    synchronized(this) {
                        log.add("Device detached")
                    }

                }
            }
        }
    }

    LaunchedEffect(key1 = activity) {
        val filter = IntentFilter(ACTION_USB_DEVICE_ATTACHED)
        activity.registerReceiver(usbReceiverAttached, filter)
    }

    LaunchedEffect(key1 = activity) {
        val filter = IntentFilter(ACTION_USB_DEVICE_DETACHED)
        activity.registerReceiver(usbReceiverDetached, filter)
    }

    LaunchedEffect(key1 = driver.value) {
        driver.value?.let {
            if (usbManager.hasPermission(it.device)) {
                hasPermission.value = true
            } else {
                requestPermission(activity, it.device, usbManager, usbReceiver)
            }
        }
    }

    DisposableEffect(key1 = hasPermission.value) {
        if (hasPermission.value) {
            driver.value?.let {
                port.value = setUpPort(usbManager, it)
            }
            port.value?.let {
                log.add("Port configured")
                try {
                    read(
                        port = it,
                        onNewData = { msg -> log.add("Device: $msg") }
                    )
                    connected.value = true
                    log.add("Ready to work")
                } catch (e: Throwable) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        onDispose {
            port.value?.close()
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Connection ")
                Surface(
                    modifier = Modifier.size(24.dp),
                    shape = CircleShape,
                    color = if (connected.value) Color.Green else Color.Gray,
                    content = {}
                )
            }

            Button(onClick = {
                log.add("Connecting...")
                connectDevice(
                    usbManager = usbManager,
                    onDriverFound = {
                        driver.value = it
                        log.add("Driver found")
                    },
                    onError = { log.add(it) }
                )
            }) {
                Text(text = "Connect")
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start,
            reverseLayout = true
        ) {
            items(count = log.size) {
                Text(text = log[log.lastIndex - it])
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            var textField by remember { mutableStateOf("") }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                enabled = connected.value,
                value = textField,
                onValueChange = { textField = it }
            )

            Button(
                onClick = {
                    try {
                        port.value?.let {
                            send(
                                msg = textField,
                                port = it
                            )
                            log.add("Me: $textField")
                            textField = ""
                        }
                    } catch (e: Throwable) {
                        log.add(e.message ?: "Send error")
                    }

                },
                enabled = connected.value
            ) {
                Text(text = "Send")
            }
        }
    }


}

fun requestPermission(
    activity: ComponentActivity,
    device: UsbDevice,
    usbManager: UsbManager,
    receiver: BroadcastReceiver
) {
    val flags = PendingIntent.FLAG_MUTABLE
    val usbPermissionIntent = PendingIntent.getBroadcast(
        activity,
        0,
        Intent(ACTION_USB_PERMISSION),
        flags
    )
    val filter = IntentFilter(ACTION_USB_PERMISSION)
    activity.registerReceiver(receiver, filter)
    usbManager.requestPermission(device, usbPermissionIntent)

}

fun connectDevice(
    usbManager: UsbManager,
    onDriverFound: (UsbSerialDriver) -> Unit,
    onError: (String) -> Unit
) {
    try {
        val drivers = checkDrivers(usbManager)

        if (drivers.isNotEmpty()) {
            onDriverFound(drivers.first())
        } else {
            onError("No drivers or device not connected")
        }
    } catch (e: Throwable) {
        onError(e.message ?: "Error")
    }
}

fun setUpPort(usbManager: UsbManager, driver: UsbSerialDriver): UsbSerialPort {
    val connection = usbManager.openDevice(driver.device)
    val port = driver.ports.first()
    port.open(connection)
    port.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE)
    return port
}

fun send(msg: String, port: UsbSerialPort) {
    port.write(msg.toByteArray(), 500)
}

fun read(port: UsbSerialPort, onNewData: (String) -> Unit) {

    val listener = object : Listener {
        override fun onNewData(data: ByteArray?) {
            data?.let {
                onNewData(String(data))
            }
        }

        override fun onRunError(e: Exception?) {
            e?.let {
                onNewData(e.message ?: "Error")
            }
        }
    }

    val ioManager = SerialInputOutputManager(port, listener)
    ioManager.start()
}

fun checkDrivers(
    usbManager: UsbManager
): List<UsbSerialDriver> {
    return UsbSerialProber.getDefaultProber().findAllDrivers(usbManager)
}