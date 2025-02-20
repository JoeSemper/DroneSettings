package com.joesemper.dronesettings.usb

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface UsbConnectionManager {
    val connection: StateFlow<Boolean>
    val log: StateFlow<List<UsbConnectionMassage>>
    val deviceLogFlow: SharedFlow<String>
    fun connect()
    fun disconnect()
    fun send(massage: String)
    fun clearLog()
}