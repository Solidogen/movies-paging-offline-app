package com.movies.android.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liked_profiles")
data class LikedProfileEntity(
    @ColumnInfo(name = "bio") val bio: String,
    @ColumnInfo(name = "has_children") val hasChildren: Boolean?,
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "likes") val likes: Long,
    @ColumnInfo(name = "photo_id") val photoId: Long?,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "api_page_index") val apiPageIndex: Int
)