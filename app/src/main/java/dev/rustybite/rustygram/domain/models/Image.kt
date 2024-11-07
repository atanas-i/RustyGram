package dev.rustybite.rustygram.domain.models

import android.net.Uri

data class Image(
    val uri: Uri,
    val imageName: String,
    val imageSize: Int,
    val dateCaptured: Long
)
