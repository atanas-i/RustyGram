package dev.rustybite.rustygram.domain.repository

import android.util.Log
import dev.rustybite.rustygram.data.local.MediaDataSource
import dev.rustybite.rustygram.data.repository.GalleryRepository
import dev.rustybite.rustygram.domain.models.Image
import dev.rustybite.rustygram.util.RustyResult
import dev.rustybite.rustygram.util.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val mediaDataSource: MediaDataSource
) : GalleryRepository {
    override val images: Flow<RustyResult<List<Image>>>
        get() = flow {
            try {
                emit(RustyResult.Loading())
                val imagesResult = mediaDataSource.getImagePaths()
                Log.d(TAG, "Gallery Repo: image name is ${mediaDataSource.getImagePaths().size}")
                emit(RustyResult.Success(imagesResult.toList()))
            } catch (exception: Exception) {
                Log.d(TAG, "Gallery Repo: Exception ${exception.localizedMessage}")
                emit(RustyResult.Failure(exception.localizedMessage))
            } catch (exception: IOException) {
                Log.d(TAG, "Gallery Repo: IO exception ${exception.localizedMessage}")
                emit(RustyResult.Failure(exception.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)
}