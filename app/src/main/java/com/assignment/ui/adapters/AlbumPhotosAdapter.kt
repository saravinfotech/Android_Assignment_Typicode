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
import com.assignment.viewutils.load

/**
 * Displays list of Photos in Album details page
 */
class AlbumPhotosAdapter(
    private var list: ArrayList<AlbumPhotosModel>,
    private val imagesCountInSingleRow: Int
) : RecyclerView.Adapter<AlbumPhotosAdapter.ViewClass>() {

    private var context: Context? = null

    inner class ViewClass(val binding: SingleRowImageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewClass {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: SingleRowImageBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.single_row_image, parent, false)
        context = parent.context
        return ViewClass(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewClass, position: Int) {
        loadAlbumPhotos(holder, position)
    }

    private fun loadAlbumPhotos(
        holder: ViewClass,
        position: Int
    ) {
        var drawable: Drawable? = getPlaceHolderImage()
        if (drawable == null) {
            drawable = context?.getDrawable(R.drawable.placeholder)
        }
        drawable?.let {
            holder.binding.image.load(
                list[position].thumbnailUrl,
                it, imagesCountInSingleRow
            )
        }
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