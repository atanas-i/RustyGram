package dev.rustybite.rustygram.data.repository

import com.google.gson.JsonObject
import dev.rustybite.rustygram.domain.models.RustyResponse
import dev.rustybite.rustygram.util.RustyResult
import kotlinx.coroutines.flow.Flow

interface PostsRepository {

    suspend fun createPost(
        token: String,
        body: JsonObject
    ): Flow<RustyResult<RustyResponse>>

}