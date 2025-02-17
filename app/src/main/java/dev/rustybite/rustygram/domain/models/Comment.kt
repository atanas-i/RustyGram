package dev.rustybite.rustygram.domain.models

import dev.rustybite.rustygram.data.dtos.comments.CommentDto

data class Comment(
    val commentId: String,
    val postId: String,
    val userId: String,
    val parentCommentId: String?,
    val comment: String,
    val commentedAt: String
)

fun CommentDto.toComment(): Comment = Comment(
    commentId = commentId,
    postId = postId,
    userId = userId,
    parentCommentId = parentCommentId,
    comment = comment,
    commentedAt = commentedAt
)
