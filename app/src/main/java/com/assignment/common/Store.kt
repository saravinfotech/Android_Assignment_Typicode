package com.assignment.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.assignment.R
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Store Object contains methods which are used at multiple points
 * So instead of repeating the code again, we have put them into one object
 */
object Store {

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

    fun openURL(context: Context,url: String) {
        var urlToLoad = url
        if (!urlToLoad.startsWith("http://") && !urlToLoad.startsWith("https://")) {
            urlToLoad = "http://$urlToLoad"
        }
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(urlToLoad)
        val chooserIntent = Intent.createChooser(intent, context.getString(R.string.open_using))
        chooserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(chooserIntent)
    }

}