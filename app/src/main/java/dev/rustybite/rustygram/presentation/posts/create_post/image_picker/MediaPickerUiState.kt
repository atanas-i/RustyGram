package dev.rustybite.rustygram.presentation.posts.create_post.image_picker

import dev.rustybite.rustygram.domain.models.Image

data class MediaPickerUiState(
    val loading: Boolean = false,
    val images: List<Image> = emptyList(),
    val errorMessage: String? = null,
)
