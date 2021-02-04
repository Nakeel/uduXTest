package com.nakeeljr.olalekanlawalapp.model

import com.google.gson.annotations.SerializedName

data class SongResponse(
	@field:SerializedName("pictures")
	val pictures: VideoThumbnailData? = null,

	@field:SerializedName("artist")
	val artist: String? = null,

	@field:SerializedName("year")
	val year: String? = null,

	@field:SerializedName("artist_name")
	val artistName: String? = null,

	@field:SerializedName("rd_hour")
	val rdHour: Int? = null,

	@field:SerializedName("valid_from")
	val validFrom: Long? = null,

	@field:SerializedName("releasedate")
	val releasedate: Long? = null,

	@field:SerializedName("duration")
	val duration: String? = null,

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("artwork_full")
	val artworkFull: String? = null,

	@field:SerializedName("valid_till")
	val validTill: Long? = null,

	@field:SerializedName("genre")
	val genre: String? = null,

	@field:SerializedName("modified")
	 val modified: Long,

	@field:SerializedName("perms")
	 val perms: List<String?>,

	@field:SerializedName("rd_minute")
	val rdMinute: Int? = null,

	@field:SerializedName("id")
	 val  id: String,

	@field:SerializedName("genre_name")
	val genreName: String? = null,

	@field:SerializedName("order")
	val order: String? = null,

	@field:SerializedName("created")
	 val  created: Long,

	@field:SerializedName("sharelink")
	val sharelink: String? = null,

	@field:SerializedName("upc")
	val upc: String? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("artwork")
	val artwork: String? = null,

	@field:SerializedName("cp")
	 val  cp: String,

	@field:SerializedName("rd_year")
	val rdYear: Int? = null,

	@field:SerializedName("rd_month")
	val rdMonth: Int? = null,

	@field:SerializedName("tracks_count")
	val tracksCount: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rd_day")
	val rdDay: Int? = null,

	@field:SerializedName("rd_second")
	val rdSecond: Int? = null,

	@field:SerializedName("status")
	 val  status: String,

	@field:SerializedName("source")
	val  source: String
)
