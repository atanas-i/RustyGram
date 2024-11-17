package dev.rustybite.rustygram.domain.models

data class Bookmark(
    val bookmarkId: String,
    val postId: String,
    val userId: String,
    val bookmarkedAt: String? = null
)
