package dev.rustybite.rustygram.util

import android.content.res.Resources

class ResourceProvider(private val resources: Resources) {
    fun getString(id: Int) = resources.getString(id)
}