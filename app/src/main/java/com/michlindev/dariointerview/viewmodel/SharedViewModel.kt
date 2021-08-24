package com.michlindev.dariointerview.viewmodel

import androidx.lifecycle.ViewModel
import com.michlindev.dariointerview.Movie


class SharedViewModel : ViewModel() {

    companion object{
        enum class Selection {
            SEARCH, FAVORITES
        }
    }

    var movieList = ArrayList<Movie>()
    var menuSwitch = SingleLiveEvent<Selection>()
    var currentState = Selection.SEARCH

    fun setMenu(state: Selection) {
        menuSwitch.postValue(state)
        currentState = state
    }
}