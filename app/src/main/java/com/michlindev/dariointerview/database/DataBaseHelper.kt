package com.michlindev.dariointerview.database

import android.content.Context
import com.michlindev.dariointerview.Movie
import kotlinx.coroutines.*


object DataBaseHelper {


    //Add single movie to DB
    suspend fun addMovieToDB(context: Context, movie: Movie) = withContext(Dispatchers.IO) {
        val db = AppDatabase(context)
        db.movieDao().insert(movie)
    }

    suspend fun getMovieFromDB(context: Context, movie: Movie) = withContext(Dispatchers.IO) {
        val db = AppDatabase(context)
        return@withContext db.movieDao().getMovie(movie.imdbID)
    }

    suspend fun removeMovieFromDB(context: Context, movie: Movie) = withContext(Dispatchers.IO) {
        val db = AppDatabase(context)
        db.movieDao().delete(movie)
    }

    suspend fun getAllMoviesFromDB(context: Context) = withContext(Dispatchers.IO) {
        val db = AppDatabase(context)
        db.movieDao().getAllMovies()
    }

    //Get all movies from db
    /*suspend fun getAllMoviesFromDB(context: Context) = withContext(Dispatchers.IO) {

        val db = AppDatabase(context)
        val moviesArrayList: ArrayList<Movie>? = ArrayList()

        val movies = db.movieDao().getAll()
        movies.forEach {
            val genreList: List<String> = it.genre!!.split(DefaultData.DELIMITER).toList()
            val mov =                    Movie(it.title!!, it.image!!, it.rating!!, it.releaseYear!!, genreList)
            moviesArrayList!!.add(mov)
        }
        moviesArrayList
    }*/


}



