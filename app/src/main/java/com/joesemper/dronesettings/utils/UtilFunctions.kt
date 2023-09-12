package com.joesemper.dronesettings.utils

fun Int.roundToMinutes(): Int {
    return this / 60
}

fun Int.roundToSeconds(): Int {
    return this % 60
}

fun getSecondsFromMinutesAndSeconds(min: Int, sec: Int): Int {
    return min * 60 + sec
}