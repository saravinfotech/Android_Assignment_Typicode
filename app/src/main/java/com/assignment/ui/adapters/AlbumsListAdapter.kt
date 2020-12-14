package com.assignment.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.RecyclerView
import com.assignment.R
import com.assignment.data.models.AlbumModel
import com.assignment.databinding.SingleRowAlbumBinding
import com.assignment.ui.callbacks.AlbumsListAdapterClickEvents
import com.assignment.viewutils.load
import java.util.*
import kotlin.collections.ArrayList

/**
 * Displays list of Albums will be shown in the App
 */
class AlbumsListAdapter(
    private var list: ArrayList<AlbumModel>,
    private val listener: AlbumsListAdapterClickEvents
) : RecyclerView.Adapter<AlbumsListAdapter.ViewClass>() {

    var context: Context? = null
    private var originalList: ArrayList<AlbumModel> = list


    inner class ViewClass(val binding: SingleRowAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: AlbumModel) {
            binding.model = model
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewClass {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: SingleRowAlbumBinding =
            inflate(layoutInflater, R.layout.single_row_album, parent, false)
        context = parent.context
        return ViewClass(binding)
    }

    override fun onBindViewHolder(holder: ViewClass, position: Int) {
        holder.bind(list[position])
        holder.binding.parentView.setOnClickListener {
            listener.onClick(list[position])
        }

        loadImage(position, holder)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    // filter the albums based on user query
    fun searchFilter(query: CharSequence) {
        val charString = query.toString()

        // user query is empty, show all albums
        if (charString.isEmpty()) {
            list = originalList
            notifyDataSetChanged()
        } else {
            val tempList: ArrayList<AlbumModel> = ArrayList()
            for (row in originalList) {
                if (row.title.toLowerCase(Locale.ENGLISH)
                        .contains(charString.toLowerCase(Locale.ENGLISH))
                ) {
                    if (!tempList.contains(row)) {
                        tempList.add(row)
                    }
                }
            }
            list = tempList
            notifyDataSetChanged()
        }
    }

    private fun loadImage(
        position: Int,
        holder: ViewClass
    ) {
        context?.let {
            ContextCompat.getDrawable(it, R.drawable.placeholder)?.let {
                list[position].thumbnailUrl?.let { it1 ->
                    holder.binding.image.load(
                        it1,
                        it
                    )
                }
            }
        }
    }

}
