package com.movies.android.data.mapper

import com.movies.android.data.api.MovieDto
import com.movies.android.data.database.entity.LikedProfileEntity
import com.movies.android.data.database.entity.MovieEntity
import com.movies.android.data.domain.MovieDomain
import com.movies.android.data.domain.LikedProfile
import com.movies.android.util.BaseApiUrlProvider

fun MovieEntity.mapToDomain(): MovieDomain = MovieDomain(
    id = id,
    title = title,
    posterPath = posterPath,
    overview = overview.orEmpty(),
    releaseDate = releaseDate.orEmpty()
)

fun MovieDto.mapToEntity(apiPageIndex: Int): MovieEntity = MovieEntity(
    id = id,
    title = title,
    posterPath = posterPath,
    overview = overview,
    releaseDate = releaseDate,
    apiPageIndex = apiPageIndex
)

fun MovieDto.mapToDomain(): MovieDomain = MovieDomain(
    id = id,
    title = title,
    posterPath = posterPath,
    overview = overview.orEmpty(),
    releaseDate = releaseDate.orEmpty()
)

fun LikedProfileEntity.mapToDomain(baseApiUrlProvider: BaseApiUrlProvider): LikedProfile = LikedProfile(
    bio = bio,
    hasChildren = hasChildren,
    id = id,
    likes = likes,
    photoId = photoId,
    username = username,
)

fun LikedProfile.mapToEntity(apiPageIndex: Int): LikedProfileEntity = LikedProfileEntity(
    bio = bio,
    hasChildren = hasChildren,
    id = id,
    likes = likes,
    photoId = photoId,
    username = username,
    apiPageIndex = apiPageIndex
)