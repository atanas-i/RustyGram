package dev.rustybite.rustygram.domain.repository

import dev.rustybite.rustygram.data.repository.StorageRepository
import dev.rustybite.rustygram.util.RustyResult
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import io.github.jan.supabase.storage.upload
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.IOException
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClient,
): StorageRepository {
    override suspend fun uploadProfilePicture(file: File, userId: String, fileName: String): Flow<RustyResult<String>> = flow {
        try {
            emit(RustyResult.Loading())
            val buckets = supabase.storage.retrieveBuckets()
            val bucket = buckets.firstOrNull() { it.name == "media" }
            if (bucket == null) {
                supabase.storage.createBucket("media")
                runCatching {
                    supabase.storage.from("media").upload("profiles/${userId}/${fileName}", file)
                } .onSuccess { uploadResponse ->
                    val imageUrl = supabase.storage.from("media").authenticatedUrl(uploadResponse.path)
                    emit(RustyResult.Success(imageUrl))
                }.onFailure { throwable ->
                    emit(RustyResult.Failure(throwable.localizedMessage))
                }
            } else {
                runCatching {
                    supabase.storage.from("media").upload("profiles/${userId}/${fileName}", file)
                } .onSuccess { uploadResponse ->
                    val imageUrl =  supabase.storage.from("media").authenticatedUrl(uploadResponse.path)
                    emit(RustyResult.Success(imageUrl))
                }.onFailure { throwable ->
                    emit(RustyResult.Failure(throwable.localizedMessage))
                }
            }
        } catch (exception: Exception) {
            emit(RustyResult.Failure(exception.localizedMessage))
        } catch (exception: IOException) {
            emit(RustyResult.Failure(exception.localizedMessage))
        }
    }
}