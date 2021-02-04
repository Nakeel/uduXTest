package com.nakeeljr.olalekanlawalapp.model.groupie

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.nakeeljr.olalekanlawalapp.R
import com.nakeeljr.olalekanlawalapp.model.SongResponse
import com.nakeeljr.olalekanlawalapp.utils.AppUtils
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

class MoodListItem(val songItem: SongResponse, private val mContext: Context) : Item() {

        override fun bind(viewHolder: ViewHolder, position: Int) {
            val moodArtView = viewHolder.itemView.findViewById<ImageView>(R.id.mood_art_img)
            val moodTitleTv = viewHolder.itemView.findViewById<TextView>(R.id.music_genre_name_title_tv)
            moodTitleTv.text = songItem.name
            AppUtils.loadImage(songItem.artwork!!,moodArtView,mContext)

        }


        override fun getLayout() = R.layout.music_genre_item

        override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
            if (other !is MoodListItem)
                return false
            if (this.songItem != other.songItem)
                return false
            return true
        }

        override fun equals(other: Any?): Boolean {
            return isSameAs(other as? MoodListItem)
        }

        override fun hashCode(): Int {
            return songItem.hashCode()
        }

    }