package com.joesemper.dronesettings.usb

sealed class UsbConnectionMassage(
    val text: String,
    val time: String,
) {
    class System(msg: String, time: String) : UsbConnectionMassage(msg, time)
    class Device(msg: String, time: String) : UsbConnectionMassage(msg, time)
    class Error(msg: String, time: String) : UsbConnectionMassage(msg, time)
    class User(msg: String, time: String) : UsbConnectionMassage(msg, time)
}