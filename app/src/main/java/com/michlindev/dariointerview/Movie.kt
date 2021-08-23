package com.michlindev.dariointerview

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjectsCast(
    var totalResults: Int,
    @SerializedName("Response") var response: Boolean,
    @SerializedName("Search") val searchResult: List<Movie>
) : Serializable


data class Movie(
    @SerializedName("Title") var title: String,
    @SerializedName("Year") var year: String,
    var imdbID: String,
    @SerializedName("Type") var type: String,
    @SerializedName("Poster") var posterUrl: String,
) : Serializable
