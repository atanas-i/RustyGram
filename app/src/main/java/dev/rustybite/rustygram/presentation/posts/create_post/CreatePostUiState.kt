package dev.rustybite.rustygram.presentation.posts.create_post

import android.net.Uri

data class CreatePostUiState(
    val loading: Boolean = false,
    val uri: Uri? = null,
    val postedAt: Long = System.currentTimeMillis(),
    val postCaption: String? = null,
    val errorMessage: String? = null,
)
