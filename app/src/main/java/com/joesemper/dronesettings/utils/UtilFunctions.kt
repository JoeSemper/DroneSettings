package com.joesemper.dronesettings.utils

import android.icu.text.SimpleDateFormat

fun Int.roundToMinutes(): Int {
    return this / 60
}

fun Int.roundToSeconds(): Int {
    return this % 60
}

fun getSecondsFromMinutesAndSeconds(min: Int, sec: Int): Int {
    return min * 60 + sec
}


fun unixTimeToDate(time: Long): String {
    val sdf = SimpleDateFormat.getDateInstance()
    return sdf.format(time)
}

fun unixTimeToTime(time: Long): String {
    val sdf = SimpleDateFormat.getTimeInstance()
    return sdf.format(time)
}