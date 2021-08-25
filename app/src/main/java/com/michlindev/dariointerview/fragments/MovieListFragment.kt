package com.michlindev.dariointerview.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.michlindev.dariointerview.Movie
import com.michlindev.dariointerview.ObjectsCast
import com.michlindev.dariointerview.api.ApiClient
import com.michlindev.dariointerview.database.DataBaseHelper
import com.michlindev.dariointerview.databinding.FragmentMovieListBinding
import com.michlindev.dariointerview.viewmodel.SharedViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MovieListFragment : Fragment() {

    companion object {
        var compositeDisposable: CompositeDisposable? = null
    }

    private lateinit var binding: FragmentMovieListBinding
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable = CompositeDisposable()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        //Observing menu switch (triggered only once using SingleLiveEvent)
        sharedViewModel.menuSwitch.observe(viewLifecycleOwner, {
            when (it) {
                SharedViewModel.Companion.Selection.SEARCH -> {
                    binding.linearLayoutSearch.visibility = View.VISIBLE
                    //Clear list
                    refreshMovieList(null)

                }
                SharedViewModel.Companion.Selection.FAVORITES -> {
                    binding.linearLayoutSearch.visibility = View.GONE
                    getListFromDB()
                }
            }
        })

        binding.buttonSearch.setOnClickListener {
            getMovies(binding.editTextSearchField.text.toString())
        }

        binding.recyclerViewList.adapter = MovieListRecyclerViewAdapter(sharedViewModel.movieList, object : OnItemClickListener {
            override fun onItemClicked(position: Int) {

                //Single item click, pass position to Single movie fragment
                val action = MovieListFragmentDirections.actionMovieListItemClick()
                action.myArg = position
                findNavController().navigate(action)
            }
        })
    }

    private fun getMovies(query: String) {
        compositeDisposable?.clear()
        compositeDisposable?.add(
            ApiClient.getClient.search(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse)
        )
    }

    //Handle compositeDisposable response
    private fun handleResponse(resultObject: ObjectsCast) {
        if (resultObject.response) {
            refreshMovieList(resultObject.searchResult)
        }
    }

    //Refresh movies
    @SuppressLint("NotifyDataSetChanged")
    private fun refreshMovieList(searchResult: List<Movie>?) {
        sharedViewModel.movieList.clear()
        searchResult?.let { sharedViewModel.movieList.addAll(it) }
        binding.recyclerViewList.adapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        //Show supportActionBar with menu
        (activity as AppCompatActivity?)?.supportActionBar?.show()

        //When click back from single movie, DB refresh requred in case movie added or removed
        when (sharedViewModel.currentState){
            SharedViewModel.Companion.Selection.SEARCH -> binding.linearLayoutSearch.visibility = View.VISIBLE
            SharedViewModel.Companion.Selection.FAVORITES -> {
                binding.linearLayoutSearch.visibility = View.GONE
                getListFromDB()
            }
        }
    }

    private fun getListFromDB() {
        lifecycleScope.launch {
            val movies = DataBaseHelper.getAllMoviesFromDB()
            withContext(Dispatchers.Main) {
                refreshMovieList(movies)

            }
        }
    }

    override fun onStop() {
        super.onStop()
        //Hide supportActionBar with menu
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }


}