package com.michlindev.dariointerview

import java.io.Serializable

data class ObjectsCast(
    var totalResults: Int,
    val Search: List<Movie>
) : Serializable


data class Movie(
    var Title: String,
    var year: String,
) : Serializable
