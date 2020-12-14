package com.assignment.viewutils

import android.content.Context
import android.widget.Toast

object MessageType {
    fun showInfoMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}