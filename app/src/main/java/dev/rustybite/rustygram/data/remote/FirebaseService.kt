package dev.rustybite.rustygram.data.remote

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import dev.rustybite.rustygram.domain.models.Image
import dev.rustybite.rustygram.util.ResourceProvider
import dev.rustybite.rustygram.util.RustyResult
import dev.rustybite.rustygram.util.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseService @Inject constructor(
    private val storage: FirebaseStorage,
    private val resProvider: ResourceProvider
) {
    private val storageRef = storage.reference

    suspend fun uploadImage(image: Image?): String? {
        return if (image != null) {
            val uploadTask = storageRef.child("posts/${image.imageName}").putFile(image.uri)
            val downloadTask = uploadTask.await().storage.downloadUrl.await()
            downloadTask.path
        } else {
            null
        }
    }
}