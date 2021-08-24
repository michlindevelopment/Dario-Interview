package com.michlindev.dariointerview.api

import com.michlindev.dariointerview.API_KEY
import com.michlindev.dariointerview.API_KEY_REQUEST
import com.michlindev.dariointerview.ObjectsCast
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("$API_KEY_REQUEST$API_KEY")
    fun search(@Query("s") keyword: String?): Observable<ObjectsCast>

    //Add more queries here...

}