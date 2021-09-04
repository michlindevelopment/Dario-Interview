package com.michlindev.dariointerview.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.michlindev.dariointerview.Movie


class SharedViewModel : ViewModel() {

    companion object{
        enum class Selection {
            SEARCH, FAVORITES
        }
    }

    var movieList = ArrayList<Movie>()
    var menuSwitch = MutableLiveData(Selection.SEARCH)

    //Update menu switch and set current state
    fun setMenu(state: Selection) {
        menuSwitch.postValue(state)
    }
}