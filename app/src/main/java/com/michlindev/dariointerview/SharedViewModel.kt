package com.michlindev.dariointerview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class SharedViewModel : ViewModel() {

    var movieList = ArrayList<Movie>()


    /*var networkConnection = MutableLiveData(GenUtils.Connection.NO_CONNECTION)
    var activation = MutableLiveData(ActiveStatus.UNRECOGNIZED)
    var transport = MutableLiveData(GenUtils.Transport.NO_CONNECTION)
    var progress = MutableLiveData<Int>()

    fun setConnected(value: GenUtils.Connection) {
        networkConnection.postValue(value)
    }

    fun setActivation(value: ActiveStatus) {
        activation.postValue(value)
    }

    fun setTransport(value: GenUtils.Transport) {
        transport.postValue(value)
    }

    fun setProgress(value: Int) {
        progress.postValue(value)
    }*/
}