package dev.rustybite.rustygram.presentation.posts.fetch_posts

import dev.rustybite.rustygram.domain.models.Bookmark
import dev.rustybite.rustygram.domain.models.Post

data class FetchPostsUiState(
    val loading: Boolean = false,
    val feeds: List<Post> = emptyList(),
    val errorMessage: String? = null,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val isLiked: Boolean = false
)
