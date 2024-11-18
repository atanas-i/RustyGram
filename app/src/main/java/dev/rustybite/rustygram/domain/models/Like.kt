package dev.rustybite.rustygram.domain.models

data class Like(
    val likeId: String,
    val postId: String,
    val userId: String,
    val likedAt: String? = null
)
