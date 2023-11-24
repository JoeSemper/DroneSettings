package com.joesemper.dronesettings.di

import com.joesemper.dronesettings.usb.UsbConnectionManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mainModule = module {

    single {
        UsbConnectionManager(context = androidContext())
    }

}