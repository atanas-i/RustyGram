package dev.rustybite.rustygram.domain.models

import dev.rustybite.rustygram.data.dtos.like.LikeDto

data class Like(
    val likeId: String,
    val postId: String,
    val userId: String,
    val likedAt: String? = null
)

fun LikeDto.toLike(): Like = Like(
    likeId = likeId,
    postId = postId,
    userId = userId,
    likedAt = likedAt
)
