package com.nakeeljr.olalekanlawalapp.model

data class BannerItem(override val id: String, override val created: Long,
					  override val modified: Long, override val perms: List<String?>,
					  override val cp: String, override val status: String,
					val mobileArtwork: String,val link:String?, val title: String?,
					val subtitle: String?, val desktopArtwork: String?):BaseItem(created, modified, perms, id, cp, status)
