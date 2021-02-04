package com.nakeeljr.olalekanlawalapp.model.groupie

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.nakeeljr.olalekanlawalapp.R
import com.nakeeljr.olalekanlawalapp.model.SongResponse
import com.nakeeljr.olalekanlawalapp.utils.AppUtils
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

class MusicPlaylistItem(val songItem: SongResponse, private val mContext: Context) : Item() {

        override fun bind(viewHolder: ViewHolder, position: Int) {
            val playlistArtView = viewHolder.itemView.findViewById<ImageView>(R.id.playlist_art_img)
            val playlistTitleTv = viewHolder.itemView.findViewById<TextView>(R.id.playlist_name_title_tv)
            playlistTitleTv.text = songItem.name
            AppUtils.loadImage(songItem.artwork!!,playlistArtView,mContext)
        }


        override fun getLayout() = R.layout.top_playlist_item

        override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
            if (other !is MusicPlaylistItem)
                return false
            if (this.songItem != other.songItem)
                return false
            return true
        }

        override fun equals(other: Any?): Boolean {
            return isSameAs(other as? MusicPlaylistItem)
        }

        override fun hashCode(): Int {
            return songItem.hashCode()
        }

    }