package com.assignment.common

import android.util.Log

/**
 * Used to log text to Logcat
 * Used only during development and debugging purposes
 */
object Logger {

    private const val generalTag = "App"
    private const val emptyText = "Given Text is empty"

    fun info(text: String?, tag: String = generalTag) {
        Log.i(tag, text ?: emptyText)
    }

}