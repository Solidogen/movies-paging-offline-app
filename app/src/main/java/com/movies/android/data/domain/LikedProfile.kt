package com.movies.android.data.domain

import kotlin.random.Random

data class LikedProfile(
    val bio: String,
    val hasChildren: Boolean?,
    val id: Long,
    val likes: Long,
    val photoId: Long?,
    val username: String,
) {
    companion object {
        fun createRandom(): LikedProfile = LikedProfile(
            bio = Random.nextInt().toString(),
            hasChildren = false,
            id = Random.nextLong(),
            likes = Random.nextLong(),
            photoId = Random.nextLong(),
            username = Random.nextInt().toString()
        )
    }
}