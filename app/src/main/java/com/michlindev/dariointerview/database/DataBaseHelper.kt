package com.michlindev.dariointerview.database

import com.michlindev.dariointerview.Movie
import com.michlindev.dariointerview.MoviesApp
import kotlinx.coroutines.*


object DataBaseHelper {

    private var db: AppDatabase = AppDatabase(MoviesApp.appContext)

    //Add single movie to DB
    suspend fun addMovieToDB(movie: Movie) = withContext(Dispatchers.IO) {
        db.movieDao().insert(movie)
    }

    suspend fun getMovieFromDB(movie: Movie) = withContext(Dispatchers.IO) {
        return@withContext db.movieDao().getMovie(movie.imdbID)
    }

    suspend fun removeMovieFromDB(movie: Movie) = withContext(Dispatchers.IO) {
        db.movieDao().delete(movie)
    }

    suspend fun getAllMoviesFromDB() = withContext(Dispatchers.IO) {
        db.movieDao().getAllMovies()
    }
}



