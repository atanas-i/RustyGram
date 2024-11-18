package dev.rustybite.rustygram.data.dtos.posts


import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("caption")
    val caption: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("photo_url")
    val photoUrl: String,
    @SerializedName("post_id")
    val postId: String,
    @SerializedName("profile_id")
    val profileId: String
)