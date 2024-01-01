package com.daylantern.newssphere

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object Util {
    @SuppressLint("SimpleDateFormat")
    fun convertDate(oldDate: String): String? {
        val oldFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val newFormat = SimpleDateFormat("dd MMM, yyyy")
        return oldFormat.parse(oldDate)?.let { newFormat.format(it) }
    }
}