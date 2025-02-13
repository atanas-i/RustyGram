package dev.rustybite.rustygram.data.repository

import com.google.gson.JsonObject
import dev.rustybite.rustygram.domain.models.Comment
import dev.rustybite.rustygram.domain.models.RustyResponse
import dev.rustybite.rustygram.util.RustyResult
import kotlinx.coroutines.flow.Flow

interface CommentRepository {

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
     * Example of `body` structure :
     * ```json
     * {
     *   "postId": 123,
     *   "content": "This is a new comment!"
     * }
     * ```
     */
    suspend fun createComment(token: String, body: JsonObject): Flow<RustyResult<RustyResponse>>

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
    suspend fun deleteComment(token: String, commentId: String): Flow<RustyResult<RustyResponse>>

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
    suspend fun getComments(token: String): Flow<RustyResult<List<Comment>>>
}