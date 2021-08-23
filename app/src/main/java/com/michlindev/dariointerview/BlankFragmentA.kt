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

class BlankFragmentA : Fragment() {

    companion object {
        @JvmStatic
        var compositeDisposable: CompositeDisposable? = null
    }

    private var _binding: FragmentBlankABinding? = null
    private val binding get() = _binding!!
    val movieList = arrayListOf<Movie>()

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
        //compositeDisposable?.clear()
        compositeDisposable?.add(
            ApiClient.getClient.search("love")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse)
        )

        //val movieList = arrayListOf<Movie>()
        binding.recyclerViewList.adapter = MyMovieRecyclerViewAdapter(movieList, object : OnItemClickListener {
            override fun onItemClicked(item: Int) {

               /* val myViewModel = ViewModelProvider(activity!!).get(ViewModel::class.java)
                myViewModel.movie = moviesArrayList[item]

                //Start details fragment
                val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.container, DetailsFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()*/
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleResponse(resultObject: ObjectsCast) {

        movieList.clear()
        movieList.addAll(resultObject.searchResult)
        binding.recyclerViewList.adapter?.notifyDataSetChanged()


    }


}