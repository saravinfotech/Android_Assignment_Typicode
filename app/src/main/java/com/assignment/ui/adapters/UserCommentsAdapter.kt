package com.assignment.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.RecyclerView
import com.assignment.R
import com.assignment.data.models.PostModel
import com.assignment.databinding.SingleRowUserCommentBinding

/**
 * UserCommentsAdapter controls how the list of user comments will be shown in the App
 *
 * Takes 1 arguments - ArrayList of PostModels
 */
class UserCommentsAdapter(
    private var list: ArrayList<PostModel>
) : RecyclerView.Adapter<UserCommentsAdapter.ViewClass>() {

    var context: Context? = null

    // view holder class
    inner class ViewClass(val binding: SingleRowUserCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: PostModel) {
            binding.model = model
        }
    }

    // create the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewClass {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: SingleRowUserCommentBinding =
            inflate(layoutInflater, R.layout.single_row_user_comment, parent, false)
        context = parent.context
        return ViewClass(binding)
    }

    // bind/set data to view
    override fun onBindViewHolder(holder: ViewClass, position: Int) {
        holder.bind(list[position])
    }

    // how many view items to create
    override fun getItemCount(): Int {
        return list.size
    }

}
