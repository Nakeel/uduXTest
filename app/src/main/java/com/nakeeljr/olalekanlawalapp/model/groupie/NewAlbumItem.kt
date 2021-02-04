package com.nakeeljr.olalekanlawalapp.model.groupie

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.nakeeljr.olalekanlawalapp.R
import com.nakeeljr.olalekanlawalapp.model.SongResponse
import com.nakeeljr.olalekanlawalapp.utils.AppUtils
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

class NewAlbumItem(val songItem: SongResponse, private val mContext: Context) : Item() {

        override fun bind(viewHolder: ViewHolder, position: Int) {
            val albumArtView = viewHolder.itemView.findViewById<ImageView>(R.id.album_img_view)
            val albumArtistTv = viewHolder.itemView.findViewById<TextView>(R.id.album_artist_tv)
            val albumTitleTv = viewHolder.itemView.findViewById<TextView>(R.id.album_title_tv)
            albumArtistTv.text = songItem.artistName
            albumTitleTv.text = songItem.name
            AppUtils.loadImage(songItem.artwork!!,albumArtView,mContext)

        }


        override fun getLayout() = R.layout.new_album_item

        override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
            if (other !is NewAlbumItem)
                return false
            if (this.songItem != other.songItem)
                return false
            return true
        }

        override fun equals(other: Any?): Boolean {
            return isSameAs(other as? NewAlbumItem)
        }

        override fun hashCode(): Int {
            return songItem.hashCode()
        }

    }