package dev.rustybite.rustygram.util

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import java.io.File

class ResourceProvider(private val context: Context) {
    private val resources = context.resources
    fun getString(id: Int) = resources.getString(id)
    val contentResolver: ContentResolver = context.contentResolver
    val cacheDir: File = context.cacheDir
}