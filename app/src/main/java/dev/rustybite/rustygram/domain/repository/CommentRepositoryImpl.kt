package dev.rustybite.rustygram.domain.repository

import com.google.gson.JsonObject
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.dtos.util.DatabaseResponseErrorDto
import dev.rustybite.rustygram.data.remote.RustyGramService
import dev.rustybite.rustygram.data.repository.CommentRepository
import dev.rustybite.rustygram.domain.models.Comment
import dev.rustybite.rustygram.domain.models.RustyResponse
import dev.rustybite.rustygram.domain.models.toComment
import dev.rustybite.rustygram.domain.models.toDatabaseResponseError
import dev.rustybite.rustygram.util.ResourceProvider
import dev.rustybite.rustygram.util.RustyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import java.io.EOFException
import java.io.IOException
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val service: RustyGramService,
    private val retrofit: Retrofit,
    private val resProvider: ResourceProvider
) : CommentRepository {

    /**
     * A converter used to convert a Retrofit response body into a [DatabaseResponseErrorDto].
     *
     * This is specifically designed to handle error responses from the database API.
     * It takes a Retrofit instance and uses its `responseBodyConverter` method to create
     * a converter for the `DatabaseResponseErrorDto` class.
     *
     * The `arrayOfNulls<Annotation>(0)` parameter indicates that no custom annotations
     * are being used for this specific conversion. This is typically the case when
     * relying on default Retrofit behavior for response body parsing.
     *
     * @see Retrofit.responseBodyConverter
     * @see DatabaseResponseErrorDto
     */
    private val converter = retrofit.responseBodyConverter<DatabaseResponseErrorDto>(
        DatabaseResponseErrorDto::class.java,
        arrayOfNulls<Annotation>(0)
    )

    /**
     * Creates a new comment.
     *
     * This function sends a request to the server to create a new comment with the provided data.
     * It requires an authentication token for authorization and the comment content in JSON format.
     * The function returns a Flow that emits the result of the operation.
     *
     * @param token The authentication token used to authorize the request. This token is typically obtained
     *              after user login and should be included in the `Authorization` header.
     * @param body A JsonObject containing the data for the new comment. This should include at least the
     *             necessary fields required by the server to create a comment (e.g., post ID, comment text).
     *             The structure of this JsonObject is defined by the server's API specification.
     * @return A Flow that emits a RustyResult containing a RustyResponse.
     *         - On success, the RustyResponse will contain the server's response to the comment creation request (e.g.,
     *           the newly created comment's ID and other details).
     *         - On failure, the RustyResult will contain an error indicating the cause of the failure
     *           (e.g., network error, unauthorized access, invalid input).
     *
     * @throws [Exception] if there are issues during the request building process.
     *
     */
    override suspend fun createComment(
        token: String,
        body: JsonObject
    ): Flow<RustyResult<RustyResponse>> = flow {
        try {
            emit(RustyResult.Loading())
        } catch (exception: Exception) {
            when(exception) {
                is EOFException -> {
                    emit(RustyResult.Failure(exception.localizedMessage))
                    val response = service.createComment(token, body)
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val data = RustyResponse(
                                success = true,
                                message = resProvider.getString(R.string.commented_successful)
                            )
                            emit(RustyResult.Success(data))
                        }
                    } else {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val error = converter.convert(errorBody)?.toDatabaseResponseError()
                            emit(RustyResult.Failure(error?.message))
                        } else {
                            emit(RustyResult.Failure(resProvider.getString(R.string.unknown_error)))
                        }
                    }
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
     * Deletes a comment identified by its ID.
     *
     * This function sends a request to delete a specific comment on the server.
     * It requires a valid authentication token for authorization.
     *
     * @param token The authentication token used to authorize the request. This should be a valid
     *              token provided by the authentication service.
     * @param commentId The ID of the comment to be deleted. This is a unique identifier for the
     *                  comment within the system.
     * @return A [Flow] emitting a [RustyResult] containing a [RustyResponse].
     *         - [RustyResult.Success] will be emitted if the deletion is successful,
     *           containing the [RustyResponse] from the server (e.g., success code, message).
     *         - [RustyResult.Failure] will be emitted if an error occurs during the deletion process.
     *           The failure will contain an [Exception] with details about the error.
     *
     * @throws Exception If there is an issue with network connectivity or if the server returns an error, an exception will be thrown within the Flow.
     *
     *
     */
    override suspend fun deleteComment(
        token: String,
        commentId: String
    ): Flow<RustyResult<RustyResponse>> = flow {
        try {
            emit(RustyResult.Loading())
        } catch (exception: Exception) {
            when(exception) {
                is EOFException -> {
                    emit(RustyResult.Failure(exception.localizedMessage))
                    val response = service.deleteComment(token, commentId)
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val data = RustyResponse(
                                success = true,
                                message = resProvider.getString(R.string.commented_deleted)
                            )
                            emit(RustyResult.Success(data))
                        }
                    } else {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val error = converter.convert(errorBody)?.toDatabaseResponseError()
                            emit(RustyResult.Failure(error?.message))
                        } else {
                            emit(RustyResult.Failure(resProvider.getString(R.string.unknown_error)))
                        }
                    }
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
     * Retrieves a list of comments from a remote data source.
     *
     * This function fetches comments using the provided authentication token.
     * It returns a [Flow] that emits [RustyResult] objects. Each [RustyResult]
     * either contains a list of [Comment] objects on success or an error
     * state indicating a failure.
     *
     * The function is `suspend`, meaning it must be called within a coroutine
     * or another suspending function. This allows for non-blocking network
     * requests.
     *
     * The returned [Flow] can be collected to react to the result of the operation.
     * It may emit multiple [RustyResult] instances, for example, if there are
     * network retries or intermediate loading states. Typically, it will emit
     * a single success or failure result.
     *
     * @param token The authentication token used to authorize the request.
     *              This should be a valid token obtained from a prior
     *              authentication process.
     * @return A [Flow] that emits [RustyResult] objects, each containing
     *         either a [List] of [Comment] on success, or an error state
     *         on failure.
     *
     * @throws Exception if there is an unrecoverable error during the process
     *
     */
    override suspend fun getComments(token: String): Flow<RustyResult<List<Comment>>> = flow {
        try {
            emit(RustyResult.Loading())
        } catch (exception: Exception) {
            when(exception) {
                is EOFException -> {
                    emit(RustyResult.Failure(exception.localizedMessage))
                    val response = service.getComments(token)
                    if (response.isSuccessful) {
                        response.body()?.let { commentsDto ->
                            val data = commentsDto.map { commentDto  -> commentDto.toComment() }
                            emit(RustyResult.Success(data))
                        }
                    } else {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val error = converter.convert(errorBody)?.toDatabaseResponseError()
                            emit(RustyResult.Failure(error?.message))
                        } else {
                            emit(RustyResult.Failure(resProvider.getString(R.string.unknown_error)))
                        }
                    }
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