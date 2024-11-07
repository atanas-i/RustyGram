package dev.rustybite.rustygram.data.remote

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.util.ResourceProvider
import dev.rustybite.rustygram.util.TAG
import java.io.File
import java.io.IOException
import javax.inject.Inject

class FirebaseService @Inject constructor(
    private val storage: FirebaseStorage,
    private val resProvider: ResourceProvider
) {
    private val storageRef = storage.reference

    fun uploadImage(uri: Uri?): String? {
        var imageUrl: String? = null
        if (uri != null) {
            val file = Uri.fromFile(File(uri.path!!))
            Log.d(TAG, "uploadImage: file path: ${file.path}")
            Log.d(TAG, "uploadImage: URI: $uri")
            Log.d(TAG, "uploadImage: URI Path: ${uri.path}")
            val uploadTask = storageRef.child("posts/${file.lastPathSegment}").putFile(file)
            uploadTask.addOnSuccessListener { uploadTaskSnapshot ->
                uploadTaskSnapshot.storage.downloadUrl.addOnSuccessListener { downloadedUri ->
                    imageUrl = downloadedUri.path
                    Log.d(TAG, "uploadImage: downloaded url: $downloadedUri, downloaded path ${downloadedUri.path}")
                }.addOnFailureListener {
                    Log.d(TAG, "uploadImage: Download error $it")
                    imageUrl = null//resProvider.getString(R.string.firebase_downlaod_failed)
                }
            }.addOnFailureListener { exception ->
                when(exception) {
                    is StorageException -> {
                        exception.errorCode
                        Log.d(TAG, "uploadImage: Upload error: Error code ${exception.errorCode}")
                        Log.d(TAG, "uploadImage: Upload error: Http result code ${exception.httpResultCode}")
                        Log.d(TAG, "uploadImage: Upload error: Cause ${exception.cause}")
                    }
                    else -> {
                        Log.d(TAG, "uploadImage: Upload error $exception")
                    }
                }
                imageUrl = null//resProvider.getString(R.string.firebase_upload_failed)
            }
        }
        return imageUrl
    }
}