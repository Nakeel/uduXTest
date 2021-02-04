package com.nakeeljr.olalekanlawalapp.model

open class BaseItem (
	open val created: Long,
	open val modified: Long,
	open val perms: List<String?>,
	open val id: String,
	open val cp: String,
	open val status: String
)
