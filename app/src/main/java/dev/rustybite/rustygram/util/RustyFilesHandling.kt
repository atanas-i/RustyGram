package dev.rustybite.rustygram.util

import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import dev.rustybite.rustygram.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


fun getFileFromUri(uri: Uri, resProvider: ResourceProvider, fileName: String): Flow<RustyResult<File>> = flow {
    val fileType = resProvider.contentResolver.getType(uri)
    val fileExtension = MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType)
    val fileNameWithExt = fileName + if (fileExtension != null) ".$fileExtension" else ""
    val tempFile = File(resProvider.cacheDir, fileNameWithExt)
    tempFile.createNewFile()
    val inputStream = resProvider.contentResolver.openInputStream(uri)
    val outputStream = FileOutputStream(tempFile)
    try {
        inputStream?.use { copyFile(inputStream, outputStream) }
        outputStream.flush()
        Log.d(TAG, "getFileFromUri: Retrieving file from URI")
        emit(RustyResult.Success(tempFile))
    } catch (exception: IOException) {
        Log.d(TAG, "getFileFromUri: ${exception.localizedMessage}")
        emit(RustyResult.Failure(exception.message ?: resProvider.getString(R.string.unknown_error)))
    }
}.flowOn(Dispatchers.IO)

private fun copyFile(source: InputStream, target: OutputStream) {
    val buffer = ByteArray(8192)
    var length: Int

    while (source.read(buffer).also { length = it } != -1) {
        target.write(buffer, 0, length)
    }
}