package com.movies.android.data.mapper

import com.movies.android.data.api.MovieDto
import com.movies.android.data.database.MovieEntity
import com.movies.android.data.domain.MovieDomain

fun MovieEntity.mapToDomain(): MovieDomain = MovieDomain(
    id = id,
    title = title,
    posterPath = posterPath
)

fun MovieDto.mapToEntity(): MovieEntity = MovieEntity(
    id = id,
    title = title,
    posterPath = posterPath
)