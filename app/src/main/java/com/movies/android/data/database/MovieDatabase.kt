package com.movies.android.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.movies.android.data.database.dao.MovieDao
import com.movies.android.data.database.dao.RemoteKeyDao
import com.movies.android.data.database.entity.MovieEntity
import com.movies.android.data.database.entity.RemoteKeyEntity

@Database(
    entities = [MovieEntity::class, RemoteKeyEntity::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}
