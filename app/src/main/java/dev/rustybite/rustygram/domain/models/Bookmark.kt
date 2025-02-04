package dev.rustybite.rustygram.domain.models

import dev.rustybite.rustygram.data.dtos.bookmark.BookmarkDto

data class Bookmark(
    val bookmarkId: String,
    val postId: String,
    val userId: String,
    val bookmarkedAt: String? = null
)

fun BookmarkDto.toBookmark() = Bookmark(
    bookmarkId = bookmarkId,
    postId = postId,
    userId = userId,
    bookmarkedAt = bookmarkedAt
)
