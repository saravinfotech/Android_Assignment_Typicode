package com.assignment.viewutils

import android.content.Context
import android.widget.Toast

/*
 * Dialogs Object provides all types of Alert Dialog and Toast
 * Concept of method overloading is used here
 */
object Message {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}