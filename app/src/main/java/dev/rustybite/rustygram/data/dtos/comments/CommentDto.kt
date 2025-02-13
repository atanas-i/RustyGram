package dev.rustybite.rustygram.data.dtos.comments

import com.google.gson.annotations.SerializedName

data class CommentDto(
    @SerializedName("comment_id")
    val commentId: String,
    @SerializedName("post_id")
    val postId: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("parent_comment_id")
    val parentCommentId: String?,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("commented_at")
    val commentedAt: String
)
