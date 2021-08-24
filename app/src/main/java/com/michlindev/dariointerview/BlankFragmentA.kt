package com.michlindev.dariointerview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.michlindev.dariointerview.api.ApiClient
import com.michlindev.dariointerview.databinding.FragmentBlankABinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import android.text.Editable

import android.text.TextWatcher
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.michlindev.dariointerview.BlankFragmentADirections.ActionBlankFragmentAToBlankFragmentB
import java.text.FieldPosition


class BlankFragmentA : Fragment() {

    companion object {
        @JvmStatic
        var compositeDisposable: CompositeDisposable? = null
    }

    private var _binding: FragmentBlankABinding? = null
    private val binding get() = _binding!!
    //private val movieList = arrayListOf<Movie>()
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable = CompositeDisposable()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBlankABinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.buttonTest.setOnClickListener {
            findNavController().navigate(R.id.action_blankFragmentA_to_blankFragmentB)
        }*/

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                getMovies(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        getMovies("love")

        //val movieList = arrayListOf<Movie>()
        binding.recyclerViewList.adapter = MyMovieRecyclerViewAdapter(sharedViewModel.movieList, object : OnItemClickListener {
            override fun onItemClicked(position: Int) {

//                   var bundle = bundleOf(1 to Int)
                //val bundle = bundleOf("myArg" to 35)

                val action = BlankFragmentADirections.actionBlankFragmentAToBlankFragmentB()
                action.myArg = position


                findNavController().navigate(action)



            }
        })
    }

    fun getMovies(query: String) {

        compositeDisposable?.clear()
        compositeDisposable?.add(
            ApiClient.getClient.search(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse)
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleResponse(resultObject: ObjectsCast) {

        sharedViewModel.movieList.clear()
        //movieList.clear()
        if (resultObject.response) {
            //movieList.addAll(resultObject.searchResult)
            sharedViewModel.movieList.addAll(resultObject.searchResult)
        }
        binding.recyclerViewList.adapter?.notifyDataSetChanged()
    }


}