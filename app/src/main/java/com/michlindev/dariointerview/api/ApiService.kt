package com.michlindev.dariointerview.api

import com.michlindev.dariointerview.Movie
import com.michlindev.dariointerview.ObjectsCast
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("?apikey=9278e7bc")
    fun search(@Query("s") keyword: String?): Observable<ObjectsCast>

    @GET("?apikey=9278e7bc")
    fun getMovies(@Query("s") keyword: String?): Observable<List<Movie>>
}