package com.movies.android.data.database.dao

import androidx.room.*
import com.movies.android.data.database.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKeyEntity)

    @Query("SELECT * FROM remote_keys WHERE title = :title")
    suspend fun remoteKeyByTitle(title: String): RemoteKeyEntity

    @Query("DELETE FROM remote_keys WHERE title = :title")
    suspend fun deleteByTitle(title: String)

}