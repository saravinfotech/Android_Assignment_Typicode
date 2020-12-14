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
 * Used for displaying the user comments on details page
 */
class UserCommentsAdapter(
    private var list: ArrayList<PostModel>
) : RecyclerView.Adapter<UserCommentsAdapter.ViewClass>() {

    var context: Context? = null

    inner class ViewClass(val binding: SingleRowUserCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: PostModel) {
            binding.model = model
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewClass {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: SingleRowUserCommentBinding =
            inflate(layoutInflater, R.layout.single_row_user_comment, parent, false)
        context = parent.context
        return ViewClass(binding)
    }

    override fun onBindViewHolder(holder: ViewClass, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
