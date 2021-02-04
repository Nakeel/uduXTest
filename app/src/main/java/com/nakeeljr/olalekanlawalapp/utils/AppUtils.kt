package com.nakeeljr.olalekanlawalapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.nakeeljr.olalekanlawalapp.R

class AppUtils {
    companion object {
        fun loadImage(imagePath: String, intoView: ImageView, context: Context) {
            Glide.with(context)
                .load(imagePath.replace("\\", ""))
                .into(intoView)
        }
    }
}