package dev.rustybite.rustygram.data.dtos.bookmark

import com.google.gson.annotations.SerializedName

data class BookmarkDto(
    @SerializedName("bookmark_id")
    val bookmarkId: String,
    @SerializedName("post_id")
    val postId: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("bookmarked_at")
    val bookmarkedAt: String,
)
