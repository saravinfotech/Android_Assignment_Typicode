package com.assignment.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.assignment.common.ErrorHandler
import com.assignment.common.GenericResponse
import com.assignment.data.enums.Status
import com.assignment.viewutils.MessageType
import kotlinx.android.synthetic.main.view_progressbar.*


open class BaseFragment : Fragment() {


    //Initialization of third party libraries
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    fun showLoading(isShowing: Boolean = false){
        try {
            if (isShowing && container != null) {
                container.bringToFront()
                container.visibility = View.VISIBLE
            } else if (container != null) {
                container.visibility = View.GONE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Handling the API response
     */
    fun consumeResponse(genericResponse: GenericResponse, layout: LinearLayout?) {
        when (genericResponse.status) {
            // api is in loading state
            Status.LOADING -> {
                showLoading(true)
                if (layout != null) {
                    layout.visibility = View.GONE
                }
            }

            // api request completed
            Status.SUCCESS -> {
                showLoading(false)
                if (layout != null) {
                    layout.visibility = View.VISIBLE
                }
            }

            // something went wrong while calling api
            Status.ERROR -> {
                showLoading(false)
                if (layout != null) {
                    layout.visibility = View.VISIBLE
                }
                MessageType.showInfoMessage(
                    requireActivity(),
                    ErrorHandler.getErrorMessage(genericResponse.error, requireContext())
                )
            }
        }
    }

    fun hideKeyboard() {
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            view?.windowToken,
            0
        )
    }
}