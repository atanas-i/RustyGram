package dev.rustybite.rustygram.presentation.posts.fetch_posts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.local.SessionManager
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetPostsViewModel @Inject constructor(
    private val repository: PostsRepository,
    private val tokenRepository: TokenManagementRepository,
    private val sessionManager: SessionManager,
    private val resProvider: ResourceProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(FetchPostsUiState())
    val uiState = _uiState.asStateFlow()
    private val _event = Channel<RustyEvents>()
    val event = _event.receiveAsFlow()

    init {
        viewModelScope.launch {
            val accessToken = sessionManager.accessToken.first()
            val refreshToken = sessionManager.refreshToken.first()
            val expiresAt = sessionManager.expiresAt.first()

            if (sessionManager.isAccessTokenExpired(accessToken, expiresAt)) {
                refreshAccessToken(refreshToken)
            }

            repository.getFeeds("Bearer $accessToken").collectLatest { response ->
                when(response) {
                    is RustyResult.Success -> {
                        Log.d(TAG, "Feeds: ${response.data} ")
                        _uiState.value = _uiState.value.copy(
                            loading = false,
                            feeds = response.data
                        )
                    }
                    is RustyResult.Failure -> {
                        _uiState.value = _uiState.value.copy(
                            loading = false,
                            errorMessage = response.message
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

    fun onBookmarkClicked(bookmark: Bookmark) {
        viewModelScope.launch {
            val body = JsonObject()
            body.addProperty("bookmark_id", bookmark.bookmarkId)
            body.addProperty("post_id", bookmark.postId)
            body.addProperty("user_id", bookmark.userId)
            body.addProperty("bookmarked_at", bookmark.bookmarkedAt)
            Log.d(TAG, "onBookmarkClicked: Bookmark body: $body")
        }
    }

    fun onShareClicked() {
        TODO("Not yet implemented")
    }
}