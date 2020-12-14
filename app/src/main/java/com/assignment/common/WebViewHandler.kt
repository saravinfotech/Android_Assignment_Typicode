package com.assignment.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.assignment.R


object WebViewHandler {

    fun openInBrowser(context: Context, url: String) {
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