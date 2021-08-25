package com.michlindev.dariointerview.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.michlindev.dariointerview.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAllMovies(): List<Movie>

   @Query("SELECT * FROM movie where imdbID = :imdbID LIMIT 1")
   fun getMovie(imdbID: String): Movie?

    @Insert
    fun insert(item: Movie)

    @Delete
    fun delete(item: Movie)
    }