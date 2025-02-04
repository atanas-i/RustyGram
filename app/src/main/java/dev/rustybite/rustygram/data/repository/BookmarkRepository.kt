package dev.rustybite.rustygram.data.repository

import com.google.gson.JsonObject
import dev.rustybite.rustygram.domain.models.Bookmark
import dev.rustybite.rustygram.domain.models.RustyResponse
import dev.rustybite.rustygram.util.RustyResult
import kotlinx.coroutines.flow.Flow

/**
 * Interface for managing bookmarks.
 *
 * This interface defines the contract for interacting with bookmark data,
 * including bookmarking and unbookmarking posts, and retrieving a list of bookmarks.
 */
interface BookmarkRepository {
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
    suspend fun bookmarkPost(
        token: String,
        body: JsonObject
    ): Flow<RustyResult<RustyResponse>>

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
     * @param postId The ID of the post to be unbookmarked. This is a unique identifier
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
    suspend fun unBookmarkPost(
        token: String,
        postId: String
    ): Flow<RustyResult<RustyResponse>>

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
    suspend fun getBookmarks(
        token: String
    ): Flow<RustyResult<List<Bookmark>>>
}