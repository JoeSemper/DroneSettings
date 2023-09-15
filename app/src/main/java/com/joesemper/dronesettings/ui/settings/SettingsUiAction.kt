package com.joesemper.dronesettings.ui.settings

sealed class SettingsUiAction {
    class NavigateNext(val argument: Int) : SettingsUiAction()
    object NavigateBack : SettingsUiAction()
    object Close : SettingsUiAction()
}