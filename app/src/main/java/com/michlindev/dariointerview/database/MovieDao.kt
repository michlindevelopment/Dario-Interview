package com.michlindev.dariointerview.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.michlindev.dariointerview.Movie

@Dao
interface MovieDao {
   /* @Query("SELECT * FROM  movieentity ORDER BY release_year DESC")
    fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM movieentity WHERE uid IN (:movieIds)")
    fun loadAllByIds(movieIds: IntArray): List<MovieEntity>

    @Query("SELECT count(*) FROM movieentity")
    fun countElements(): Int

    @Query("SELECT count(*) FROM movieentity WHERE title = :title AND release_year=:releaseYear")
    fun countSingle(title: String,releaseYear: Int): Int

    @Insert
    fun insertAll(vararg items: MovieEntity)*/

    @Query("SELECT * FROM movie")
    fun getAllMovies(): List<Movie>


   @Query("SELECT * FROM movie where imdbID = :imdbID LIMIT 1")
   fun getMovie(imdbID: String): Movie?

    @Insert
    fun insert(item: Movie)

    @Delete
    fun delete(item: Movie)

    }