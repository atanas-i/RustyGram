package dev.rustybite.rustygram.presentation.posts.create_post

import android.net.Uri
import dev.rustybite.rustygram.domain.models.Image

data class CreatePostUiState(
    val loading: Boolean = false,
    val uri: Uri? = null,
    val image: Image? = null,
    val postedAt: Long = System.currentTimeMillis(),
    val caption: String = "",
    val postCaption: String? = null,
    val errorMessage: String? = null,
)
