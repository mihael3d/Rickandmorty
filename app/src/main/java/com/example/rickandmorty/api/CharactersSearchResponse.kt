package com.example.rickandmorty.api

import com.google.gson.annotations.SerializedName
import com.example.rickandmorty.model.Character

/**
 * Data class to hold character responses from searchCharacters API calls.
 */

class CharactersSearchResponse (
    @SerializedName("info") val info: Info,
    @SerializedName("results") val results: List<Character> = emptyList(),
    val nextPage: Int? = null
)