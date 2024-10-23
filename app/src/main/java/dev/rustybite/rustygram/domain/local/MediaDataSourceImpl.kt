package dev.rustybite.rustygram.domain.local

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media
import dev.rustybite.rustygram.data.local.MediaDataSource
import dev.rustybite.rustygram.domain.models.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MediaDataSourceImpl @Inject constructor(
    private val contentResolver: ContentResolver
) : MediaDataSource {
    override suspend fun getImagePaths(): MutableList<Image>  {
        val contentResolver = contentResolver
        val images = mutableListOf<Image>()
        val projection = arrayOf(
            Media._ID,
            Media.DISPLAY_NAME,
            Media.SIZE,
            Media.DATE_ADDED
        )
        val orderBy = Media.DATE_ADDED

        val query = contentResolver.query(
            Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            "$orderBy DESC"

        )
        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(Media.DISPLAY_NAME)
            val sizeColumn = cursor.getColumnIndexOrThrow(Media.SIZE)
            val dateColumn = cursor.getColumnIndexOrThrow(Media.DATE_ADDED)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val size = cursor.getInt(sizeColumn)
                val date = cursor.getLong(dateColumn)
                val contentUri = ContentUris.withAppendedId(
                    Media.EXTERNAL_CONTENT_URI,
                    id
                )
                images += Image(contentUri, name, size, date)
            }
        }
        return images
    }
}