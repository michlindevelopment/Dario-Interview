package com.michlindev.dariointerview.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.michlindev.dariointerview.IMDB_URL
import com.michlindev.dariointerview.database.DataBaseHelper
import com.michlindev.dariointerview.databinding.FragmentSingleMovieBinding
import com.michlindev.dariointerview.viewmodel.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SingleMovieFragment : Fragment() {

    private lateinit var binding: FragmentSingleMovieBinding
    private lateinit var sharedViewModel: SharedViewModel

    private val args: SingleMovieFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSingleMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        //Retrieve the position via args and get the selected movie from sharedViewModel
        val movie = sharedViewModel.movieList[args.position]

        //Check if movie in favorites
        lifecycleScope.launch {
            val dbMovie = DataBaseHelper.getMovieFromDB(movie)
            if (dbMovie!=null)
                withContext(Dispatchers.Main) {
                    binding.checkBox.isChecked = true
                }
        }

        binding.textViewMovieTitle.text = movie.title
        binding.textViewMovieType.text = movie.type
        binding.textViewMovieYear.text = movie.year

        //Using Glide to load an imgae
        Glide.with(requireContext()).load(movie.posterUrl).into(binding.imageViewPoster)

        //Open imdb page
        binding.buttonImdb.setOnClickListener {
            val site = "$IMDB_URL${movie.imdbID}"
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(site))
            requireContext().startActivity(browserIntent)
        }

        //Preferred option to use 'setOnCheckedChangeListener'. But we need only user interaction monitoring
        binding.checkBox.setOnClickListener {

            val checkBox = it as CheckBox
            if (checkBox.isChecked){
                lifecycleScope.launch {
                    DataBaseHelper.addMovieToDB(movie)
                }

            }else{
                lifecycleScope.launch {
                    DataBaseHelper.removeMovieFromDB(movie)
                }

            }
        }
    }
}

