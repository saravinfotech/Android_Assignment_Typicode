package com.assignment.ui.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import com.assignment.R
import com.assignment.ui.interfaces.AlertDialogInterface

/*
 * Dialogs Object provides all types of Alert Dialog and Toast
 * Concept of method overloading is used here
 */
object Dialogs {

    fun showMessage(
        activity: Activity,
        message: String,
        positiveButtonText: String,
        listener: AlertDialogInterface
    ) {

        val builder = AlertDialog.Builder(activity)
        builder.setMessage(message)
        builder.setPositiveButton(positiveButtonText) { dialogInterface, i ->
            listener.positiveButtonClick()
        }

        val alert = builder.create()
        alert.show()
    }

    fun showMessage(
        context: Context,
        message: String,
        positiveButtonText: String,
        negativeButtonText: String,
        listener: AlertDialogInterface
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
        builder.setPositiveButton(positiveButtonText) { dialogInterface, i ->
            listener.positiveButtonClick()
        }
        builder.setNegativeButton(negativeButtonText) { dialogInterface, i ->
            listener.negativeButtonClick()
        }

        val alert = builder.create()
        alert.show()
    }

    fun showMessage(
        activity: Activity,
        message: String,
        positiveButtonText: String,
        negativeButtonText: String,
        listener: AlertDialogInterface
    ) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(message)
        builder.setPositiveButton(positiveButtonText) { dialogInterface, i ->
            listener.positiveButtonClick()
        }
        builder.setNegativeButton(negativeButtonText) { dialogInterface, i ->
            listener.negativeButtonClick()
        }

        val alert = builder.create()
        alert.show()
    }

    fun showMessage(
        context: Context,
        message: String
    ) {

        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.ok) { dialogInterface, i ->
            dialogInterface.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showToast(context: Context, resId: Int) {
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show()
    }

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