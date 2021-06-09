package com.movies.android.data.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.movies.android.data.database.entity.LikedProfileEntity

@Dao
interface LikedProfilesDao {

    @Query("SELECT * FROM liked_profiles ORDER BY api_page_index")
    fun getLikedProfilesPagingSource(): PagingSource<Int, LikedProfileEntity>

    @Query("SELECT * FROM liked_profiles")
    fun getLikedProfiles(): List<LikedProfileEntity>

    @Query("DELETE FROM liked_profiles")
    suspend fun deleteAllLikedProfiles()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<LikedProfileEntity>)

    @Update
    suspend fun updateLikedProfiles(list: List<LikedProfileEntity>)
}