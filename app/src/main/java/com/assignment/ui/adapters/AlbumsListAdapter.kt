package com.assignment.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.RecyclerView
import com.assignment.R
import com.assignment.data.models.AlbumModel
import com.assignment.databinding.SingleRowAlbumBinding
import com.assignment.ui.interfaces.AlbumsListAdapterClickEvents
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

/**
 * AlbumsListAdapter controls how the list of Albums will be shown in the App
 *
 * Takes 2 arguments - ArrayList of AlbumModel and listener of type AlbumsListAdapterClickEvents
 */
class AlbumsListAdapter(
    private var list: ArrayList<AlbumModel>,
    private val listener: AlbumsListAdapterClickEvents
) : RecyclerView.Adapter<AlbumsListAdapter.ViewClass>() {

    var context: Context? = null
    var originalList: ArrayList<AlbumModel> = list

    // view holder class
    inner class ViewClass(val binding: SingleRowAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: AlbumModel) {
            binding.model = model
        }
    }

    // create the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewClass {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: SingleRowAlbumBinding =
            inflate(layoutInflater, R.layout.single_row_album, parent, false)
        context = parent.context
        return ViewClass(binding)
    }

    // bind/set data to view
    override fun onBindViewHolder(holder: ViewClass, position: Int) {
        holder.bind(list[position])
        holder.binding.parentView.setOnClickListener {
            listener.onClick(list[position])
        }

        Picasso.get().load(list[position].thumbnailUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder).into(holder.binding.image)
    }

    // how many view items to create
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
            // loop over all albums and find albums with title matching the user query
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
            // notify adapter about data changes
            notifyDataSetChanged()
        }
    }

}
