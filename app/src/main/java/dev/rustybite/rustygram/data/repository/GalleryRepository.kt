package dev.rustybite.rustygram.data.repository

import dev.rustybite.rustygram.domain.models.Image
import dev.rustybite.rustygram.util.RustyResult
import kotlinx.coroutines.flow.Flow

interface GalleryRepository {
    val images: Flow<RustyResult<List<Image>>>
}