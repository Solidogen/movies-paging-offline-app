package com.movies.android.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(MovieEntity::class), version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
