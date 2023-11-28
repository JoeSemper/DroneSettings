package com.joesemper.dronesettings.usb

sealed class UsbConnectionMassage(val text: String) {
    class System(msg: String) : UsbConnectionMassage(msg)
    class Device(msg: String) : UsbConnectionMassage(msg)
    class Error(msg: String) : UsbConnectionMassage(msg)
    class User(msg: String) : UsbConnectionMassage(msg)
}