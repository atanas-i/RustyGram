package dev.rustybite.rustygram.domain.repository

import com.google.gson.JsonObject
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.dtos.util.DatabaseResponseErrorDto
import dev.rustybite.rustygram.data.remote.RustyGramService
import dev.rustybite.rustygram.data.repository.LikeRepository
import dev.rustybite.rustygram.domain.models.Like
import dev.rustybite.rustygram.domain.models.RustyResponse
import dev.rustybite.rustygram.domain.models.toDatabaseResponseError
import dev.rustybite.rustygram.domain.models.toLike
import dev.rustybite.rustygram.util.ResourceProvider
import dev.rustybite.rustygram.util.RustyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import java.io.EOFException
import java.io.IOException
import javax.inject.Inject

class LikeRepositoryImpl @Inject constructor(
    private val service: RustyGramService,
    private val retrofit: Retrofit,
    private val resProvider: ResourceProvider
) : LikeRepository {

    /**
     *  A converter for handling error responses from the database.
     *  This converter specifically transforms the raw response body into a [DatabaseResponseErrorDto] object.
     *  It's used by Retrofit to interpret the response body when an error (non-2xx) HTTP status code is received.
     *
     *  This is essential for extracting structured error information from the database API's error responses,
     *  allowing the application to gracefully handle various database-related issues, like authentication failures,
     *  data validation errors, or server-side exceptions, and provide specific feedback to the user.
     */
    private val converter = retrofit.responseBodyConverter<DatabaseResponseErrorDto>(
        DatabaseResponseErrorDto::class.java, arrayOfNulls<Annotation>(0)
    )

    /**
     * Likes a post on the server.
     *
     * This function sends a request to the server to like a specific post.
     * It requires a valid authentication token and the ID of the post to be liked.
     * The result of the operation is returned as a [Flow] of [RustyResult] containing a [RustyResponse].
     *
     * @param token The authentication token of the user performing the action. This token should be obtained through a successful login.
     * @param postId The ID of the post that the user wants to like.
     * @return A [Flow] emitting a [RustyResult] containing a [RustyResponse].
     *   - **RustyResult.Success**: Indicates that the like operation was successful. The [RustyResponse] will typically contain information about the success of the operation.
     *   - **RustyResult.Failure**: Indicates that an error occurred during the like operation. The [RustyResponse] will likely contain an error message or code.
     *   - **RustyResult.Loading**: Indicate that the operation is in progress. This state is often used to update the UI.
     *
     * @throws IllegalArgumentException if either the token or postId is blank or empty.
     */
    override suspend fun likePost(
        token: String,
        body: JsonObject
    ): Flow<RustyResult<RustyResponse>> = flow {
        try {
            emit(RustyResult.Loading())
            val response = service.likePost(token, body)
            if (response.isSuccessful) {
                response.body()?.let {
                    val data = RustyResponse(
                        success = true,
                        message = resProvider.getString(R.string.post_liked_successfully)
                    )
                    emit(RustyResult.Success(data))
                }
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    val error = converter.convert(errorBody)?.toDatabaseResponseError()
                    if (error != null) {
                        emit(RustyResult.Failure(error.message))
                    } else {
                        emit(RustyResult.Failure(resProvider.getString(R.string.unknown_error)))
                    }
                }
            }
        } catch (exception: Exception) {
            when(exception) {
                is EOFException -> {
                    emit(RustyResult.Failure(exception.localizedMessage))
                }
                is IOException -> {
                    emit(RustyResult.Failure(exception.localizedMessage))
                }
                else -> {
                    emit(RustyResult.Failure(exception.localizedMessage))
                }
            }
        }
    }

    /**
     * Unlikes a post.
     *
     * This function sends a request to the server to unlike a specific post, identified by its like ID.
     * It requires a valid authentication token to authorize the request.
     *
     * @param token The authentication token of the user making the request. This token is used to verify
     *              the user's identity and permissions. It is typically obtained after successful user
     *              login or registration.
     * @param likeId The unique identifier of the like that needs to be removed. This ID is usually
     *               provided when the post was initially liked.
     * @return A [Flow] emitting a [RustyResult] that contains a [RustyResponse] on success or an error
     *         on failure.
     *         - **RustyResult.Success:** The [RustyResponse] indicates the server's response to the unliking
     *           operation. It typically includes a status code and an optional message.
     *         - **RustyResult.Failure:** The [RustyResult] will contain an error if the request fails. This could
     *           be due to network issues, invalid token, or server-side errors. Check the [RustyResult]
     *           to determine the type of error that occurred.
     *         - **RustyResult.Loading**: Indicate that the operation is in progress. This state is often used to update the UI.
     *
     * @throws IllegalArgumentException if the token or likeId is empty or blank.
     */
    override suspend fun unlikePost(
        token: String,
        likeId: String
    ): Flow<RustyResult<RustyResponse>> = flow {
        try {
            emit(RustyResult.Loading())
            val response = service.unlikePost(token, likeId)
            if (response.isSuccessful) {
                response.body()?.let {
                    val data = RustyResponse(
                        success = true,
                        message = resProvider.getString(R.string.post_unliked_successfully)
                    )
                    emit(RustyResult.Success(data))
                }
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    val error = converter.convert(errorBody)?.toDatabaseResponseError()
                    if (error != null) {
                        emit(RustyResult.Failure(error.message))
                    } else {
                        emit(RustyResult.Failure(resProvider.getString(R.string.unknown_error)))
                    }
                }
            }
        } catch (exception: Exception) {
            when(exception) {
                is EOFException -> {
                    emit(RustyResult.Failure(exception.localizedMessage))
                }
                is IOException -> {
                    emit(RustyResult.Failure(exception.localizedMessage))
                }
                else -> {
                    emit(RustyResult.Failure(exception.localizedMessage))
                }
            }
        }
    }

    /**
     * Retrieves a list of likes associated with the user identified by the provided token.
     *
     * This function performs a network request to fetch the user's likes. It returns a [Flow]
     * that emits [RustyResult] objects, which encapsulate either a successful list of [Like]
     * objects or an error state.  The function is marked as `suspend`, indicating it should be
     * called within a coroutine or another suspending function.
     *
     * @param token The authentication token used to authorize the request. This token is typically
     *              obtained during the user login process. It's essential for identifying the
     *              correct user whose likes are being requested.
     * @return A [Flow] that emits [RustyResult] objects.
     *         - On success: [RustyResult.Success] containing a [List] of [Like] objects.
     *         - On failure: [RustyResult.Failure] containing an exception that describes the error.
     *
     * @throws IllegalStateException If the token is invalid or empty.
     * @throws Exception Any other exception that may occur during the network request.
     */
    override suspend fun getLikes(token: String): Flow<RustyResult<List<Like>>> = flow {
        try {
            emit(RustyResult.Loading())
            val response = service.getLikes(token)
            if (response.isSuccessful) {
                response.body()?.let { likesDto ->
                    val data = likesDto.map { likeDto -> likeDto.toLike() }
                    emit(RustyResult.Success(data))
                }
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    val error = converter.convert(errorBody)?.toDatabaseResponseError()
                    if (error != null) {
                        emit(RustyResult.Failure(error.message))
                    } else {
                        emit(RustyResult.Failure(resProvider.getString(R.string.unknown_error)))
                    }
                }
            }
        } catch (exception: Exception) {
            when(exception) {
                is EOFException -> {
                    emit(RustyResult.Failure(exception.localizedMessage))
                }
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