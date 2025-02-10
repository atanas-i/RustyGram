package dev.rustybite.rustygram.presentation.posts.fetch_posts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.local.SessionManager
import dev.rustybite.rustygram.data.repository.BookmarkRepository
import dev.rustybite.rustygram.data.repository.PostsRepository
import dev.rustybite.rustygram.data.repository.TokenManagementRepository
import dev.rustybite.rustygram.domain.models.Bookmark
import dev.rustybite.rustygram.domain.models.Like
import dev.rustybite.rustygram.util.ResourceProvider
import dev.rustybite.rustygram.util.RustyEvents
import dev.rustybite.rustygram.util.RustyResult
import dev.rustybite.rustygram.util.TAG
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class GetPostsViewModel @Inject constructor(
    private val repository: PostsRepository,
    private val tokenRepository: TokenManagementRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val sessionManager: SessionManager,
    private val resProvider: ResourceProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(FetchPostsUiState())
    val uiState = _uiState.asStateFlow()
    private val _event = Channel<RustyEvents>()
    val event = _event.receiveAsFlow()
    private val isAlreadyBookmarked = MutableStateFlow(false)

//    init {
//        viewModelScope.launch {
//            val accessToken = sessionManager.accessToken.first()
//            val refreshToken = sessionManager.refreshToken.first()
//            val expiresAt = sessionManager.expiresAt.first()
//
//            if (sessionManager.isAccessTokenExpired(accessToken, expiresAt)) {
//                refreshAccessToken(refreshToken)
//            }
//
//        }
//    }

    init {
        viewModelScope.launch {
            val accessToken = sessionManager.accessToken.first()
            val refreshToken = sessionManager.refreshToken.first()
            val expiresAt = sessionManager.expiresAt.first()

            if (sessionManager.isAccessTokenExpired(accessToken, expiresAt)) {
                refreshAccessToken(refreshToken)
            }
            repository.getFeeds("Bearer $accessToken").collectLatest { response ->
                when (response) {
                    is RustyResult.Success -> {
                        getBookmarks(accessToken)
                        _uiState.update { state ->
                            state.copy(
                                loading = true,
                                feeds = response.data
                            )
                        }
                    }
                    is RustyResult.Failure -> {
                        _uiState.update { state ->
                            state.copy(
                                loading = false
                            )
                        }
                        _event.send(
                            RustyEvents.ShowSnackBar(
                                response.message ?: resProvider.getString(R.string.unknown_error)
                            )
                        )
                    }

                    is RustyResult.Loading -> {
                        _uiState.update { state ->
                            state.copy(
                                loading = true
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getBookmarks(accessToken: String?) {
        viewModelScope.launch {
            bookmarkRepository.getBookmarks("Bearer $accessToken").collectLatest { response ->
                when (response) {
                    is RustyResult.Success -> {
                        _uiState.value = _uiState.value.copy(
                            loading = false,
                            bookmarks = response.data
                        )
                        Log.d(TAG, "getBookmarks: Fetch bookmarks succeeded")
                    }

                    is RustyResult.Failure -> {
                        _uiState.value = _uiState.value.copy(
                            loading = false,
                            errorMessage = response.message
                        )
                        Log.d(TAG, "getBookmarks: Fetch bookmarks failed")
                    }

                    is RustyResult.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            loading = true
                        )
                        Log.d(TAG, "getBookmarks: Fetching bookmarks loading is ${_uiState.value.isBookmarked}")
                    }
                }
            }
        }
    }

    private fun refreshAccessToken(refreshToken: String?) {
        viewModelScope.launch {
            if (refreshToken != null) {
                val body = JsonObject()
                body.addProperty("refresh_token", refreshToken)
                tokenRepository.refreshToken(body).collectLatest { result ->
                    when (result) {
                        is RustyResult.Success -> {
                            sessionManager.saveAccessToken(result.data.accessToken)
                            sessionManager.saveRefreshToken(result.data.refreshToken)
                            _uiState.value = _uiState.value.copy(
                                loading = false,
                            )
                        }

                        is RustyResult.Failure -> {
                            _uiState.value = _uiState.value.copy(
                                loading = false,
                                errorMessage = result.message
                                    ?: resProvider.getString(R.string.unknown_error)
                            )
                        }

                        is RustyResult.Loading -> {
                            _uiState.value = _uiState.value.copy(
                                loading = true
                            )
                        }
                    }
                }
            }
        }
    }

    fun onLikeClicked(like: Like) {
        viewModelScope.launch {
            val body = JsonObject()
            body.addProperty("like_id", like.likeId)
            body.addProperty("post_id", like.postId)
            body.addProperty("user_id", like.userId)
            body.addProperty("bookmarked_at", like.likedAt)
            Log.d(TAG, "onBookmarkClicked: Like body: $body")
        }
    }

    fun onBookmarkClicked(postId: String, isBookmarked: Boolean, userId: String?) {
        viewModelScope.launch {
            val accessToken = sessionManager.accessToken.first()
            val refreshToken = sessionManager.refreshToken.first()
            val expiresAt = sessionManager.expiresAt.first()


            if (sessionManager.isAccessTokenExpired(accessToken, expiresAt)) {
                refreshAccessToken(refreshToken)
            }
            _uiState.update { state ->
                state.copy(isBookmarked = isBookmarked)
            }
            val body = JsonObject()
            body.addProperty("bookmark_id", UUID.randomUUID().toString())
            body.addProperty("post_id", postId)
            body.addProperty("user_id", userId)

            val bookmark = bookmarked(userId, postId)
            val isAlreadyBookmarked = bookmark != null
            if (isAlreadyBookmarked) {
                unBookmarkPost(accessToken, bookmark.bookmarkId)
            } else {
                bookmarkPost(body, accessToken)
            }
        }
    }

    private fun bookmarked(userId: String?, postId: String): Bookmark? {
        Log.d(TAG, "bookmarked: User Id is $userId")
        return _uiState.value.bookmarks.find { bookmark -> bookmark.userId == userId && bookmark.postId == postId }
    }

    private fun bookmarkPost(
        body: JsonObject,
        accessToken: String?,
    ) {
        viewModelScope.launch {
            bookmarkRepository.bookmarkPost("Bearer $accessToken", body).collectLatest { result ->
                when (result) {
                    is RustyResult.Success -> {
                        Log.d(TAG, "onBookmarkClicked: bookmarking post succeeded")
                        _uiState.update { state ->
                            state.copy(bookmarkLoading = false)
                        }
                        getBookmarks(accessToken)
                        _event.send(RustyEvents.ShowSnackBar(result.data.message))
                    }

                    is RustyResult.Failure -> {
                        Log.d(
                            TAG,
                            "onBookmarkClicked: bookmarking post failed with msg: ${result.message}"
                        )
                        _uiState.update { state ->
                            state.copy(bookmarkLoading = false)
                        }
                        isAlreadyBookmarked.value = false
                        _event.send(
                            RustyEvents.ShowSnackBar(
                                result.message ?: resProvider.getString(R.string.unknown_error)
                            )
                        )
                    }

                    is RustyResult.Loading -> {
                        _uiState.update { state ->
                            state.copy(bookmarkLoading = true)
                        }
                    }
                }
            }
        }
    }

    private fun unBookmarkPost(
        accessToken: String?,
        bookmarkId: String
    ) {
        viewModelScope.launch {
            bookmarkRepository.unBookmarkPost("Bearer $accessToken", "eq.$bookmarkId")
                .collectLatest { result ->
                    when (result) {
                        is RustyResult.Success -> {
                            _uiState.update { state ->
                                state.copy(
                                    bookmarkLoading = false,
                                )
                            }
                            //Log.d(TAG, "unBookmarkPost: Removing bookmark succeeded")
                            getBookmarks(accessToken)
                            //Log.d(TAG, "unBookmarkPost: fetch bookmarks succeeded")
                            _event.send(RustyEvents.ShowSnackBar(result.data.message))
                            Log.d(TAG, "unBookmarkPost: ${result.data.message}")
                        }

                        is RustyResult.Failure -> {
                            _uiState.update { state ->
                                state.copy(
                                    bookmarkLoading = false
                                )
                            }
                            //Log.d(TAG, "unBookmarkPost: Removing bookmark failed: Error ${result.message}")
                            _event.send(
                                RustyEvents.ShowSnackBar(
                                    result.message ?: resProvider.getString(R.string.unknown_error)
                                )
                            )
                        }

                        is RustyResult.Loading -> {
                            _uiState.update { state ->
                                state.copy(
                                    bookmarkLoading = true
                                )
                            }
                            //Log.d(TAG, "unBookmarkPost: Removing bookmark is ${_uiState.value.loading}")
                        }
                    }
                }
        }
    }

    fun onShareClicked() {
        TODO("Not yet implemented")
    }
}