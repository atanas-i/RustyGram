package dev.rustybite.rustygram.data.local

import dev.rustybite.rustygram.domain.models.Image
import dev.rustybite.rustygram.util.RustyResult
import kotlinx.coroutines.flow.Flow

interface MediaDataSource {
    suspend fun getImagePaths(): MutableList<Image>
}