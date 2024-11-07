package dev.rustybite.rustygram.domain.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import dev.rustybite.rustygram.data.remote.FirebaseService
import dev.rustybite.rustygram.data.repository.StorageRepository
import dev.rustybite.rustygram.util.RustyResult
import dev.rustybite.rustygram.util.TAG
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
    private val firebaseService: FirebaseService,
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
        } catch (exception: StorageException) {
            Log.d(TAG, "uploadProfilePicture: Storage exception: ${exception.errorCode}")
        }
    }

    override suspend fun uploadPostImage(uri: Uri?): Flow<RustyResult<String>> = flow {
        try {
            val imageUrl = firebaseService.uploadImage(uri)
            emit(RustyResult.Loading())
            if (imageUrl != null) {
                emit(RustyResult.Success(imageUrl))
            } else {
                Log.d(TAG, "uploadPostImage: uploading failed")
            }
        } catch (exception: IOException) {
            emit(RustyResult.Failure(exception.localizedMessage))
        } catch (exception: Exception) {
            emit(RustyResult.Failure(exception.localizedMessage))
        }
    }
}