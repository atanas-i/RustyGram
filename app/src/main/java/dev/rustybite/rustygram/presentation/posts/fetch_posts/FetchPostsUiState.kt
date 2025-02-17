package dev.rustybite.rustygram.presentation.posts.fetch_posts

import dev.rustybite.rustygram.domain.models.Bookmark
import dev.rustybite.rustygram.domain.models.Comment
import dev.rustybite.rustygram.domain.models.Like
import dev.rustybite.rustygram.domain.models.Post

data class FetchPostsUiState(
    val loading: Boolean = false,
    val loadingComments: Boolean = false,
    val bookmarkLoading: Boolean = false,
    val feeds: List<Post> = emptyList(),
    val bookmarks: List<Bookmark> = emptyList(),
    val comments: List<Comment> = emptyList(),
    val postComments: List<Comment> = emptyList(),
    val likes: List<Like> = emptyList(),
    val isBookmarked: Boolean = false,
    val errorMessage: String? = null,
    val likesCount: Int = 0,
    val isCommentClicked: Boolean = false,
    val commentsCount: Int = 0,
    val commentLikeCounts: Int = 0,
    val isCommentLiked: Boolean = false,
    val isLiked: Boolean = false,
    val comment: String = "",
    val postId: String = "",
    val commentingError: String? = null
)
