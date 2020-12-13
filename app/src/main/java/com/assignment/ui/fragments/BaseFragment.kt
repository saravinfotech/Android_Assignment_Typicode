package com.assignment.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.view_progressbar.*


open class BaseFragment : Fragment() {


    //Initialization of third party libraries
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    fun showLoading(isShowing: Boolean = false){
        try {
            if(isShowing && container!=null){
                container.bringToFront()
                container.visibility = View.VISIBLE
            }else if(container != null){
                container.visibility = View.GONE
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}