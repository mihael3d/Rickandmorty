package com.example.rickandmorty.api

import com.google.gson.annotations.SerializedName

data class Info(
    @field:SerializedName("count") val  count: Int,
    @field:SerializedName("pages") val  pages: Int,
    @field:SerializedName("next") val next:String?,
    @field:SerializedName("prev") val prev: String? = null
)