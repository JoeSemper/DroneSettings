package com.joesemper.dronesettings.di

import com.joesemper.dronesettings.usb.UsbConnectionManager
import com.joesemper.dronesettings.usb.UsbConnectionManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mainModule = module {

    single<UsbConnectionManager> {
        UsbConnectionManagerImpl(context = androidContext())
    }

}