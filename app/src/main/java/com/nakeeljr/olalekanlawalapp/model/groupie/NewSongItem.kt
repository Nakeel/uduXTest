package com.nakeeljr.olalekanlawalapp.model.groupie

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.nakeeljr.olalekanlawalapp.R
import com.nakeeljr.olalekanlawalapp.model.SongResponse
import com.nakeeljr.olalekanlawalapp.utils.AppUtils
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

class NewSongItem(val songItem: SongResponse, private val mContext: Context) : Item() {

        override fun bind(viewHolder: ViewHolder, position: Int) {
            val songArtView = viewHolder.itemView.findViewById<ImageView>(R.id.song_art_img)
            val songArtistTv = viewHolder.itemView.findViewById<TextView>(R.id.new_song_artist_tv)
            val songTitleTv = viewHolder.itemView.findViewById<TextView>(R.id.song_title_tv)
            val songDurationTv = viewHolder.itemView.findViewById<TextView>(R.id.song_duration_tv)
            songArtistTv.text = songItem.artistName
            songTitleTv.text = songItem.name
            AppUtils.loadImage(songItem.artwork!!,songArtView,mContext)

        }


        override fun getLayout() = R.layout.new_song_item

        override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
            if (other !is NewSongItem)
                return false
            if (this.songItem != other.songItem)
                return false
            return true
        }

        override fun equals(other: Any?): Boolean {
            return isSameAs(other as? NewSongItem)
        }

        override fun hashCode(): Int {
            return songItem.hashCode()
        }

    }