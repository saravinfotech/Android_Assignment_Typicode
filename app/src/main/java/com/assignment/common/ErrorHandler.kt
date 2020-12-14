package com.assignment.common

import android.content.Context
import com.assignment.R
import java.net.SocketTimeoutException
import java.net.UnknownHostException


object ErrorHandler {

    /**
     * Get the readable error message from Exception
     */
    fun getErrorMessage(e: Exception?, context: Context): String {
        Logger.info("$e")
        return when (e) {
            is UnknownHostException -> context.getString(R.string.unknown_host_error)
            is SocketTimeoutException -> context.getString(R.string.connection_time_out)
            else -> e?.message ?: context.getString(R.string.api_error_message)
        }
    }
}