package com.assignment.viewutils

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.load(url: String, drawableResource: Drawable, imageCount: Int = 3) {
    Picasso.get().load(url)
        .placeholder(drawableResource)
        .resize(
            Resources.getSystem().displayMetrics.widthPixels / imageCount,
            Resources.getSystem().displayMetrics.widthPixels / imageCount
        ).error(drawableResource).centerCrop().into(this)
}