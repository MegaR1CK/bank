package com.hfad.worldskillsbank.models

import com.google.gson.annotations.SerializedName

data class ModelTemplate(

	@field:SerializedName("Id")
	val id: Int,

	@field:SerializedName("Name")
	val name: String,

	@field:SerializedName("DestNumber")
	val destNumber: String,

	@field:SerializedName("CardNumber")
	val cardNumber: String,

	@field:SerializedName("Sum")
	val sum: Double,

	@field:SerializedName("Owner")
	val owner: Int,
)
