package com.nakeeljr.olalekanlawalapp.model

data class DiscoveryResponse(
	val sectionIndex: Int? = null,
	val Q: List<QItem?>? = null,
	val created: Long? = null,
	val name: String? = null,
	val modified: Long? = null,
	val id: String? = null,
	val title: String? = null,
	val type: String? = null,
	val items: List<SongResponse?>? = null,
	val status: String? = null,
	val target: String? = null,
	val displayLimit: Int? = null
)
