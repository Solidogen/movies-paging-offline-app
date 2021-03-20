package com.movies.android.data.database.entity

import androidx.room.*

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey
    @ColumnInfo(name = "title", collate = ColumnInfo.NOCASE)
    val title: String,
    @ColumnInfo(name = "next_page")
    val nextPage: Int?
)