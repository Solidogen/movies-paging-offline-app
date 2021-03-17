package com.movies.android.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getMoviesFlow(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<MovieEntity>)

    @Transaction
    @Query("SELECT * FROM movie WHERE movie.id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity
}