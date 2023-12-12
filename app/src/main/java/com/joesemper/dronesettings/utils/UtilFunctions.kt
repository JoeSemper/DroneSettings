package com.joesemper.dronesettings.utils

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat.getSystemService
import com.joesemper.dronesettings.R

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

@Composable
fun Boolean.toText(): String {
    return if (this) {
        stringResource(R.string.yes)
    } else {
        stringResource(R.string.no)
    }
}

@Composable
fun Int.toText(): String{
    return when (this) {
        1 -> stringResource(id = R.string.cocking)
        2 -> stringResource(id = R.string.activation)
        else -> stringResource(id = R.string.none)
    }
}

fun copyTextToClipboard(context: Context, textCopied:String) {
    val clipboardManager = getSystemService(context, ClipboardManager::class.java) as ClipboardManager
    clipboardManager.setPrimaryClip(ClipData.newPlainText   ("", textCopied))

    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
        Toast.makeText(context, context.getString(R.string.copied), Toast.LENGTH_SHORT).show()
}
