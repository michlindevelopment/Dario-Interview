package com.michlindev.dariointerview

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ObjectsCast(
    var totalResults: Int,
    @SerializedName("Response") var response: Boolean,
    @SerializedName("Search") val searchResult: List<Movie>
) : Serializable


@Entity
data class Movie(
    @PrimaryKey @SerializedName("imdbID") var imdbID: String,
    @SerializedName("Title") var title: String,
    @SerializedName("Year") var year: String,
    @SerializedName("Type") var type: String,
    @SerializedName("Poster") var posterUrl: String,
) : Serializable
