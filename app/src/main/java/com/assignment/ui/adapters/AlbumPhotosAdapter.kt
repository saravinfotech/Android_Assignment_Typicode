package com.assignment.ui.adapters

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.assignment.R
import com.assignment.data.models.AlbumPhotosModel
import com.assignment.databinding.SingleRowImageBinding
import com.squareup.picasso.Picasso

/**
 * AlbumPhotosAdapter controls how the list of Album Photos will be shown in the App
 *
 * Takes 2 arguments - ArrayList of AlbumPhotosModel and count of images in single row to show
 */
class AlbumPhotosAdapter(
    private var list: ArrayList<AlbumPhotosModel>,
    private val imagesCountInSingleRow: Int
) : RecyclerView.Adapter<AlbumPhotosAdapter.ViewClass>() {

    private var context: Context? = null

    // view holder class
    inner class ViewClass(val binding: SingleRowImageBinding) :
        RecyclerView.ViewHolder(binding.root)

    // create the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewClass {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: SingleRowImageBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.single_row_image, parent, false)
        context = parent.context
        return ViewClass(binding)
    }

    // how many view items to create
    override fun getItemCount() = list.size

    // bind/set data to view
    override fun onBindViewHolder(holder: ViewClass, position: Int) {
        // Picasso is used for loading images
        val drawable: Drawable? = getPlaceHolderImage()

        if (drawable != null) {
            Picasso.get().load(list[position].thumbnailUrl)
                .placeholder(drawable)
                .resize(
                    Resources.getSystem().displayMetrics.widthPixels / imagesCountInSingleRow,
                    Resources.getSystem().displayMetrics.widthPixels / imagesCountInSingleRow
                ).error(drawable).centerCrop().into(holder.binding.image)
        } else {
            Picasso.get().load(list[position].thumbnailUrl)
                .placeholder(R.drawable.placeholder)
                .resize(
                    Resources.getSystem().displayMetrics.widthPixels / imagesCountInSingleRow,
                    Resources.getSystem().displayMetrics.widthPixels / imagesCountInSingleRow
                ).error(R.drawable.placeholder).centerCrop().into(holder.binding.image)
        }

        Picasso.get().load(list[position].thumbnailUrl)
            .placeholder(R.drawable.placeholder)
            .resize(
                Resources.getSystem().displayMetrics.widthPixels / imagesCountInSingleRow,
                Resources.getSystem().displayMetrics.widthPixels / imagesCountInSingleRow
            ).error(R.drawable.placeholder).centerCrop().into(holder.binding.image)
    }

    private fun getPlaceHolderImage(): Drawable? {
        context?.let {
            val bitmap = BitmapFactory.decodeResource(it.resources, R.drawable.placeholder)
            val image = Bitmap.createScaledBitmap(
                bitmap,
                (Resources.getSystem().displayMetrics.widthPixels / imagesCountInSingleRow),
                (Resources.getSystem().displayMetrics.widthPixels / imagesCountInSingleRow),
                false
            )
            return RoundedBitmapDrawableFactory.create(it.resources, image)
        }
        return null
    }
}