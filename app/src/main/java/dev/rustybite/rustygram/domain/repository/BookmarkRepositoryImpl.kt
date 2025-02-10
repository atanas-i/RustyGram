package dev.rustybite.rustygram.domain.repository

import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.dtos.util.DatabaseResponseErrorDto
import dev.rustybite.rustygram.data.remote.RustyGramService
import dev.rustybite.rustygram.data.repository.BookmarkRepository
import dev.rustybite.rustygram.domain.models.Bookmark
import dev.rustybite.rustygram.domain.models.RustyResponse
import dev.rustybite.rustygram.domain.models.toBookmark
import dev.rustybite.rustygram.domain.models.toDatabaseResponseError
import dev.rustybite.rustygram.util.ResourceProvider
import dev.rustybite.rustygram.util.RustyResult
import dev.rustybite.rustygram.util.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import java.io.EOFException
import java.io.IOException
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val service: RustyGramService,
    private val retrofit: Retrofit,
    private val resProvider: ResourceProvider
) : BookmarkRepository {

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
     * Bookmarks a post for a user.
     *
     * This function sends a request to bookmark a specific post using the provided token
     * for authentication and a JSON object containing the necessary post identifier.
     * It returns a flow emitting the result of the operation, which can be either a success
     * with a RustyResponse or a failure with an error encapsulated within RustyResult.
     *
     * @param token The authentication token for the user making the request.
     *              This token is typically obtained after user login and is used to authorize
     *              the bookmarking operation.
     * @param body  A JSON object containing the details necessary to identify the post to bookmark.
     *              This object should at minimum contain a "postId" field with the ID of the post.
     *              Example: `{"postId": "some-unique-post-id"}`
     * @return A Flow emitting a RustyResult.
     *         - On success, the flow emits a RustyResult.Success containing a RustyResponse.
     *         - On failure, the flow emits a RustyResult.Failure containing an error indicating
     *           why the bookmarking operation failed (e.g., network error, invalid token,
     *           post not found, server error).
     *
     * @throws IllegalArgumentException if the `token` is empty or blank.
     * @throws IllegalArgumentException if the `body` is missing the essential keys like "postId"
     * ```
     */
    override suspend fun bookmarkPost(
        token: String,
        body: JsonObject
    ): Flow<RustyResult<RustyResponse>> = flow {
        try {
            emit(RustyResult.Loading())
            val response = service.bookmarkPost(token, body)
            if (response.isSuccessful) {
                response.body()?.let {
                    val data = RustyResponse(
                        success = true,
                        message = resProvider.getString(R.string.post_bookmarked_successfully)
                    )
                    emit(RustyResult.Success(data))
                }
            } else {
                val errorBody = response.errorBody()
                Log.d(TAG, "bookmarkPost: error body ${errorBody?.string()}")
                if (errorBody != null) {
                    val error = converter.convert(errorBody)?.toDatabaseResponseError()
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
            when(exception) {
                is IllegalArgumentException -> {
                    emit(RustyResult.Failure(exception.localizedMessage))
                }
                is IOException -> {
                    emit(RustyResult.Failure(exception.localizedMessage))
                }
                else -> {
                    emit(RustyResult.Failure(resProvider.getString(R.string.unknown_error)))
                }
            }
        }
    }

    /**
     * Unbookmarks a specific post for the authenticated user.
     *
     * This function sends a request to the server to remove a post from the user's bookmarks.
     * The server will verify the provided token to ensure the user is authenticated and authorized
     * to perform this action.
     *
     * @param token The authentication token for the user. This token is typically obtained
     *              after a successful login. It's used to identify the user and verify their
     *              permissions.
     * @param bookmarkId The ID of the post to be unbookmarked. This is a unique identifier
     *               assigned to each post.
     * @return A Flow emitting a RustyResult containing a RustyResponse.
     *         - RustyResult.Success: If the unbookmarking operation is successful, the Flow will emit
     *                                a RustyResult.Success containing the RustyResponse returned by the server.
     *                                The RustyResponse typically includes a status code indicating success.
     *         - RustyResult.Failure: If the unbookmarking operation fails, the Flow will emit a
     *                                RustyResult.Failure containing an error object that describes the reason
     *                                for the failure (e.g., network error, server error, invalid token).
     *
     * @throws Exception If there's an issue during the network request.
     *
     * @see RustyResult
     * @see RustyResponse
     */
    override suspend fun unBookmarkPost(
        token: String,
        bookmarkId: String
    ): Flow<RustyResult<RustyResponse>> = flow {
        try {
            emit(RustyResult.Loading())
            val response = service.unBookmarkPost(token, bookmarkId)
            if (response.isSuccessful) {
                Log.d(TAG, "unBookmarkPost: Un Bookmarking: Responses isSuccessful ${response.isSuccessful}")
                val data = RustyResponse(
                    success = true,
                    message = resProvider.getString(R.string.post_unbookmarked_successfully)
                )
                emit(RustyResult.Success(data))
//                response.body()?.let {
//                    Log.d(TAG, "unBookmarkPost: Un Bookmarking: Response Body ${response.body().toString()}")
//
//                }
            } else {
                val errorBody = response.errorBody()
                Log.d(TAG, "unBookmarkPost: error body ${errorBody?.string()}")
                if (errorBody != null) {
                    val error = converter.convert(errorBody)?.toDatabaseResponseError()
                    if (error != null) {
                        Log.d(TAG, "unBookmarkPost: error body ${errorBody.string()}")
                        emit(RustyResult.Failure(error.message))
                    } else {
                        emit(RustyResult.Failure(resProvider.getString(R.string.unknown_error)))
                    }
                } else {
                    emit(RustyResult.Failure(resProvider.getString(R.string.unknown_error)))
                }
            }
        } catch (exception: Exception) {
            Log.d(TAG, "unBookmarkPost: Un Bookmarking catches an exception")
            when(exception) {
                is EOFException -> {
                    Log.d(TAG, "unBookmark: error body ${exception.localizedMessage}")
                    emit(RustyResult.Failure(exception.localizedMessage))
                }
                is IOException -> {
                    Log.d(TAG, "unBookmark: error body ${exception.localizedMessage}")
                    emit(RustyResult.Failure(exception.localizedMessage))
                }
                else -> {
                    Log.d(TAG, "unBookmark: error body ${exception.localizedMessage}")
                    emit(RustyResult.Failure(resProvider.getString(R.string.unknown_error)))
                }
            }
        }
    }

    /**
     * Retrieves a list of bookmarks associated with the provided authentication token.
     *
     * This function makes a network request to fetch bookmarks. It returns a Flow
     * that emits a [RustyResult] containing either a list of [Bookmark] objects
     * on success or an error state on failure.
     *
     * @param token The authentication token used to authorize the request.
     *              This token should be valid and have the necessary permissions
     *              to access the user's bookmarks.
     * @return A Flow that emits a [RustyResult].
     *         - On success, the [RustyResult] contains a List<[Bookmark]> representing the retrieved bookmarks.
     *         - On failure, the [RustyResult] contains an error indicating the cause of the failure.
     *
     * @throws Exception if any unexpected error happens during the process.
     *         It might be related to network issues, data parsing problems, etc.
     *
     */
    override suspend fun getBookmarks(token: String): Flow<RustyResult<List<Bookmark>>> = flow {
        try {
            emit(RustyResult.Loading())
            val response = service.getBookmarks(token)
            if (response.isSuccessful) {
                response.body()?.let { bookmarkDtos ->
                    val data = bookmarkDtos.map { it.toBookmark() }
                    emit(RustyResult.Success(data))
                }
            }
        } catch (exception: Exception) {
            when(exception) {
                is IllegalArgumentException -> {
                    emit(RustyResult.Failure(exception.localizedMessage))
                }
                is IOException -> {
                    emit(RustyResult.Failure(exception.localizedMessage))
                }
                else -> {
                    emit(RustyResult.Failure(resProvider.getString(R.string.unknown_error)))
                }
            }
        }
    }
}