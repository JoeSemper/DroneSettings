package com.joesemper.dronesettings.ui.settings.state

sealed class SettingsUiAction {
    class NavigateNext(val argument: Int) : SettingsUiAction()
    object NavigateBack : SettingsUiAction()
    object Close : SettingsUiAction()
}