package com.nakeeljr.olalekanlawalapp.model.groupie

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.nakeeljr.olalekanlawalapp.R
import com.nakeeljr.olalekanlawalapp.model.DiscoveryResponse
import com.nakeeljr.olalekanlawalapp.model.SongResponse
import com.nakeeljr.olalekanlawalapp.utils.AppUtils
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

class NewVideosItem(val songItem: SongResponse, private val mContext: Context) : Item() {

        override fun bind(viewHolder: ViewHolder, position: Int) {
            val videoThumbnailView = viewHolder.itemView.findViewById<ImageView>(R.id.video_thumbnail_img)
            val videoTitleTv = viewHolder.itemView.findViewById<TextView>(R.id.video_title_tv)
//            videoArtistTv.text = songItem.
            videoTitleTv.text = songItem.name
            val thumbnailUrls = songItem.pictures!!.sizes!!
            val thumbnail = thumbnailUrls[thumbnailUrls.size-1]!!.link
            AppUtils.loadImage(thumbnail!!,videoThumbnailView,mContext)



        }


        override fun getLayout() = R.layout.new_videos_item

        override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
            if (other !is NewVideosItem)
                return false
            if (this.songItem != other.songItem)
                return false
            return true
        }

        override fun equals(other: Any?): Boolean {
            return isSameAs(other as? NewVideosItem)
        }

        override fun hashCode(): Int {
            return songItem.hashCode()
        }

    }