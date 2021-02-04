package com.nakeeljr.olalekanlawalapp.model

import com.google.gson.annotations.SerializedName

data class VideoThumbnailData(

	@field:SerializedName("sizes")
	val sizes: List<SizesItem?>? = null,

	@field:SerializedName("resource_key")
	val resourceKey: String? = null,

	@field:SerializedName("active")
	val active: Boolean? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("uri")
	val uri: String? = null,

	@field:SerializedName("default_picture")
	val defaultPicture: Boolean? = null
)

data class SizesItem(

	@field:SerializedName("link_with_play_button")
	val linkWithPlayButton: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null
)
