package dev.rustybite.rustygram.data.dtos.like

import com.google.gson.annotations.SerializedName

data class LikeDto(
    @SerializedName("like_id")
    val likeId: String,
    @SerializedName("post_id")
    val postId: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("liked_at")
    val likedAt: String
)