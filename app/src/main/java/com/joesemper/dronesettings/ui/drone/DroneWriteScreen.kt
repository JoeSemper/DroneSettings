package com.joesemper.dronesettings.ui.drone

import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.hoho.android.usbserial.driver.UsbSerialDriver
import com.hoho.android.usbserial.driver.UsbSerialProber


internal fun Context.findActivity(): ComponentActivity {

    var context = this

    while (context is ContextWrapper) {

        if (context is ComponentActivity) return context

        context = context.baseContext

    }

    throw IllegalStateException("Permissions should be called in the context of an Activity")

}

//fun Context.getActivity(): AppCompatActivity? = when (this) {
//    is AppCompatActivity -> this
//    is ContextWrapper -> baseContext.getActivity()
//    else -> null
//}

@Composable
fun DroneWriteScreen(
    navController: NavController,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        DroneWriteContentScreen(
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun DroneWriteContentScreen(
    modifier: Modifier = Modifier
) {

    val ACTION_USB_PERMISSION = "com.joesemper.dronesettings.USB_PERMISSION"

    val context = LocalContext.current
    val activity = context.findActivity()

    lateinit var device: UsbDevice
    val usbManager: UsbManager = activity.getSystemService(Context.USB_SERVICE) as UsbManager

    val availableDrivers: List<UsbSerialDriver> =
        UsbSerialProber.getDefaultProber().findAllDrivers(usbManager)

    var driver: UsbSerialDriver? = null

    if (availableDrivers.isNotEmpty()) {
        driver = availableDrivers.first()
    }

    if (driver !=null && !usbManager.hasPermission(driver.device)) {
        val flags = PendingIntent.FLAG_MUTABLE

        val usbPermissionIntent = PendingIntent.getBroadcast(
            context,
            0,
            Intent(ACTION_USB_PERMISSION),
            flags
        )

        usbManager.requestPermission(driver.device, usbPermissionIntent)
    }


//    val usbReceiver = object : BroadcastReceiver() {
//
//        override fun onReceive(context: Context, intent: Intent) {
//            if (ACTION_USB_PERMISSION == intent.action) {
//                synchronized(this) {
//                    val device: UsbDevice? = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)
//
//                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
//                        device?.apply {
//                            //call method to set up device communication
//
//                        }
//                    } else {
//                        Log.d(TAG, "permission denied for device $device")
//                    }
//                }
//            }
//        }
//    }
//
//
//    val manager = ContextCompat.getSystemService(LocalContext.current, UsbManager::class.java) as UsbManager
//
//    val permissionIntent = PendingIntent.getBroadcast(LocalContext.current, 0, Intent(ACTION_USB_PERMISSION), PendingIntent.FLAG_IMMUTABLE)
//    val filter = IntentFilter(ACTION_USB_PERMISSION)
//    registerReceiver(LocalContext.current, usbReceiver, filter, RECEIVER_NOT_EXPORTED)
//
//    lateinit var device: UsbDevice
//
//    manager.requestPermission(device, permissionIntent)


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Hello drone!")

        Button(onClick = {
            Toast.makeText(
                context,
                availableDrivers.size.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }) {
            Text(text = "Run")
        }
    }
}