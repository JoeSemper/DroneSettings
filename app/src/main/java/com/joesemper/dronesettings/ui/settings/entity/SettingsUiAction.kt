package com.joesemper.dronesettings.ui.settings.entity

sealed class SettingsUiAction {
    class NavigateNext(val argument: Int) : SettingsUiAction()
    object NavigateBack : SettingsUiAction()
    object Close : SettingsUiAction()
}