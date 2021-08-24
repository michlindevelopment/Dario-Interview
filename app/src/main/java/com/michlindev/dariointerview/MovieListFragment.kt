package com.michlindev.dariointerview

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
import com.michlindev.dariointerview.api.ApiClient
import com.michlindev.dariointerview.database.DataBaseHelper
import com.michlindev.dariointerview.databinding.FragmentMovieListBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MovieListFragment : Fragment() {

    companion object {
        @JvmStatic
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
                    Log.d("DTAG", "Pupu1")
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

        /* binding.editText.addTextChangedListener(object : TextWatcher {
             override fun afterTextChanged(s: Editable?) {
                 Log.d("DTAG","Fires?")
                 getMovies(s.toString())
             }

             override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
             override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                 Log.d("DTAG","Fires2?")
             }
         })*/

        //getMovies("love")

        //val movieList = arrayListOf<Movie>()
        binding.recyclerViewList.adapter = MyMovieRecyclerViewAdapter(sharedViewModel.movieList, object : OnItemClickListener {
            override fun onItemClicked(position: Int) {

                val action = MovieListFragmentDirections.actionBlankFragmentAToBlankFragmentB()
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


    private fun handleResponse(resultObject: ObjectsCast) {
        if (resultObject.response) {
            refreshMovieList(resultObject.searchResult)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refreshMovieList(searchResult: List<Movie>?) {
        sharedViewModel.movieList.clear()
        searchResult?.let { sharedViewModel.movieList.addAll(it) }
        binding.recyclerViewList.adapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.show()

        when (sharedViewModel.currentState){
            SharedViewModel.Companion.Selection.SEARCH -> binding.linearLayoutSearch.visibility = View.VISIBLE
            SharedViewModel.Companion.Selection.FAVORITES -> {
                binding.linearLayoutSearch.visibility = View.GONE
                getListFromDB()
            }
        }
    }

    private fun getListFromDB() {
        Log.d("DTAG","getListFromDB")
        lifecycleScope.launch {
            val movies = DataBaseHelper.getAllMoviesFromDB(requireContext())
            withContext(Dispatchers.Main) {
                refreshMovieList(movies)

            }
        }
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }


}