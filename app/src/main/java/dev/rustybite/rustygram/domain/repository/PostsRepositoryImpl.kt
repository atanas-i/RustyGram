package dev.rustybite.rustygram.domain.repository

import android.util.Log
import com.google.gson.JsonObject
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.dtos.util.DatabaseResponseErrorDto
import dev.rustybite.rustygram.data.dtos.util.PostErrorDto
import dev.rustybite.rustygram.data.remote.RustyGramService
import dev.rustybite.rustygram.data.repository.PostsRepository
import dev.rustybite.rustygram.domain.models.Post
import dev.rustybite.rustygram.domain.models.RustyResponse
import dev.rustybite.rustygram.domain.models.toDatabaseResponseError
import dev.rustybite.rustygram.domain.models.toPost
import dev.rustybite.rustygram.domain.models.toPostError
import dev.rustybite.rustygram.util.ResourceProvider
import dev.rustybite.rustygram.util.RustyResult
import dev.rustybite.rustygram.util.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import java.io.IOException
import javax.annotation.meta.When

class PostsRepositoryImpl(
    private val service: RustyGramService,
    private val retrofit: Retrofit,
    private val resProvider: ResourceProvider
) : PostsRepository {
    private val converter = retrofit.responseBodyConverter<PostErrorDto>(
        PostErrorDto::class.java,
        arrayOfNulls(0)
    )

    override suspend fun createPost(
        token: String,
        body: JsonObject
    ): Flow<RustyResult<RustyResponse>> = flow {
        try {
            emit(RustyResult.Loading())
            val response = service.createPost(token, body)
            if (response.isSuccessful) {
                response.body()?.let {
                    val data = RustyResponse(
                        success = true,
                        message = resProvider.getString(R.string.post_created_successfully)
                    )
                    emit(RustyResult.Success(data))
                }
            } else {
                val errorBody = response.errorBody()
                Log.d(TAG, "createPost: error body ${errorBody?.string()}")
                if (errorBody != null) {
                    val error = converter.convert(errorBody)?.toPostError()
                    if (error != null) {
                        emit(RustyResult.Failure(error.message))
                    } else {
                        emit(RustyResult.Failure(resProvider.getString(R.string.unknown_error)))
                    }
                } else {
                    emit(RustyResult.Failure(resProvider.getString(R.string.unknown_error)))
                }
            }
        } catch (exception: Exception) {
            when (exception) {
                is IOException -> {
                    emit(RustyResult.Failure(exception.localizedMessage))
                }

                else -> {
                    emit(RustyResult.Failure(resProvider.getString(R.string.unknown_error)))
                }
            }
        }
    }

    override suspend fun getFeeds(token: String): Flow<RustyResult<List<Post>>> = flow {
        try {
            emit(RustyResult.Loading())
            val response = service.getFeeds(token)
            if (response.isSuccessful) {
                response.body()?.let { postsDto ->
                    val posts = postsDto.map { it.toPost() }.sortedBy { it.createdAt }
                    emit(RustyResult.Success(posts))
                }
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    val error = converter.convert(errorBody)?.toPostError()
                    if (error != null) {
                        emit(RustyResult.Failure(error.message))
                    }
                } else {
                    emit(RustyResult.Failure(resProvider.getString(R.string.unknown_error)))
                }
            }
        } catch (exception: Exception) {
            when (exception) {
                is IOException -> {
                    emit(RustyResult.Failure(exception.localizedMessage))
                }

                else -> {
                    emit(RustyResult.Failure(exception.localizedMessage))
                }
            }
        }
    }
}