package dev.rustybite.rustygram.domain.local

import android.content.ContentResolver
import android.content.ContentUris
import android.os.Build
import android.provider.MediaStore

import android.util.Log
import dev.rustybite.rustygram.data.local.MediaDataSource
import dev.rustybite.rustygram.domain.models.Image
import dev.rustybite.rustygram.util.TAG
import javax.inject.Inject

class MediaDataSourceImpl @Inject constructor(
    private val contentResolver: ContentResolver
) : MediaDataSource {
    override suspend fun getImagePaths(): MutableList<Image>  {
        var images = mutableListOf<Image>()
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.DATA
        )
        val orderBy = MediaStore.Images.Media.DATE_ADDED

        val query = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            "$orderBy DESC"

        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
            val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val size = cursor.getInt(sizeColumn)
                val date = cursor.getLong(dateColumn)
                //val data = cursor.get
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                images = images.plus(Image(contentUri, name, size, date)).toMutableList()
            }
        }
        return images
    }
}