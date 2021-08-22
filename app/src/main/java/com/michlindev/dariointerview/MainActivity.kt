package com.michlindev.dariointerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.michlindev.dariointerview.api.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        var compositeDisposable: CompositeDisposable? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compositeDisposable = CompositeDisposable()



        compositeDisposable?.add(
            ApiClient.getClient.search("love")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse)
        )
    }

    /* private fun loadData() {

         compositeDisposable?.add(
             ApiClient.getClient.getMovies("")
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribeOn(Schedulers.io())
                 .subscribe(this::handleResponse)
         )
     }*/

    private fun handleResponse(movieList: ObjectsCast) {
        Log.d("DTAG", "")


    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}