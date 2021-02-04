package com.nakeeljr.olalekanlawalapp.model

import com.google.gson.annotations.SerializedName

data class BannerResponse(

	@field:SerializedName("mobile_artwork")
	val mobileArtwork: String? = null,

	@field:SerializedName("created")
	override val created: Long,

	@field:SerializedName("subtitle")
	val subtitle: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("modified")
	override val modified: Long,

	@field:SerializedName("perms")
	override val perms: List<String?>,

	@field:SerializedName("id")
	override val id: String,

	@field:SerializedName("desktop_artwork")
	val desktopArtwork: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("cp")
	override val cp: String,

	@field:SerializedName("status")
	override val status: String
):BaseItem(created, modified, perms, id, cp, status)
