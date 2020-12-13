package com.assignment.ui.utils

import android.app.Activity
import android.app.AlertDialog
import com.assignment.R

/*
 * Dialogs Object provides all types of Alert Dialog and Toast
 * Concept of method overloading is used here
 */
object Dialogs {

    fun showMessage(
        activity: Activity,
        message: String
    ) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.ok) { dialogInterface, i ->
            dialogInterface.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

}