package com.joesemper.dronesettings.ui.settings

sealed class PresetUiAction {
    class NavigateNext(val argument: Int) : PresetUiAction()
    object NavigateBack : PresetUiAction()
    object Close : PresetUiAction()
}