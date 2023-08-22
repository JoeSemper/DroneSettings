package com.joesemper.dronesettings.domain.entity

import com.joesemper.dronesettings.data.constants.SettingsConstants

data class TimelineConstants(
    val minTimeDelay: Int = SettingsConstants.MIN_TIME_DELAY,
    val maxTimeDelay: Int = SettingsConstants.MAX_TIME_DELAY,
    val minCockingTime: Int = SettingsConstants.MIN_COCKING_TIME,
    val maxCockingTime: Int = SettingsConstants.MAX_COCKING_TIME,
    val minSelfDestructionTime: Int = SettingsConstants.MIN_SELF_DESTRUCTION_TIME,
    val maxSelfDestructionTime: Int = SettingsConstants.MAX_SELF_DESTRUCTION_TIME
)