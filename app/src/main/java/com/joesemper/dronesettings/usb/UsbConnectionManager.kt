package com.joesemper.dronesettings.usb

import kotlinx.coroutines.flow.StateFlow

interface UsbConnectionManager {
    val connection: StateFlow<Boolean>
    val log: StateFlow<List<UsbConnectionMassage>>
    fun connect()
    fun disconnect()
    fun send(massage: String)
}