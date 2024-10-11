package dev.rustybite.rustygram.data.repository

import android.net.Uri
import dev.rustybite.rustygram.util.RustyResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface StorageRepository {
    suspend fun uploadProfilePicture(file: File, userId: String, fileName: String): Flow<RustyResult<String>>
}