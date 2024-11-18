package dev.rustybite.rustygram.domain.models

import com.google.gson.annotations.SerializedName
import dev.rustybite.rustygram.data.dtos.posts.PostDto

data class Post(
    val caption: String,
    val createdAt: String,
    val photoUrl: String,
    val postId: String,
    val profileId: String
)

fun PostDto.toPost(): Post = Post(
    caption = caption,
    createdAt = createdAt,
    photoUrl = photoUrl,
    postId = postId,
    profileId = profileId
)
