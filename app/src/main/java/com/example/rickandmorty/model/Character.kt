package com.example.rickandmorty.model

import com.google.gson.annotations.SerializedName

data class Character (@field:SerializedName("id") val id: Int,
                      @field:SerializedName("name") val name: String,
                      @field:SerializedName("status")val status: String,
                      @field:SerializedName("species") val species: String,
                      @field:SerializedName("type") val type: String,
                      @field:SerializedName("gender") val gender: String,
                      @field:SerializedName("origin") val origin: Origin,
                      @field:SerializedName("location") val location: Location,
                      @field:SerializedName("image") val image: String,
                      @field:SerializedName("episode") val episode: List<String>
) {
    data class Location(
        @field:SerializedName("name") val name: String,
        @field:SerializedName("url") val url: String
    )

    data class Origin(
        @field:SerializedName("name") val name: String,
        @field:SerializedName("url") val url: String
    )

}