package com.joesemper.dronesettings.utils

import android.annotation.SuppressLint
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
    val sdf = SimpleDateFormat("dd.MM.yyyy")
    return sdf.format(time)
}

@SuppressLint("SimpleDateFormat")
fun unixTimeToTime(time: Long): String {
    val sdf = SimpleDateFormat("HH:mm")
    return sdf.format(time)
}