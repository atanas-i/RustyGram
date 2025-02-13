package dev.rustybite.rustygram.presentation.posts.fetch_posts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.local.SessionManager
import dev.rustybite.rustygram.data.repository.BookmarkRepository
import dev.rustybite.rustygram.data.repository.LikeRepository
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
    private val likeRepository: LikeRepository,
    private val sessionManager: SessionManager,
    private val resProvider: ResourceProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(FetchPostsUiState())
    val uiState = _uiState.asStateFlow()
    private val _event = Channel<RustyEvents>()
    val event = _event.receiveAsFlow()
    private val isAlreadyBookmarked = MutableStateFlow(false)


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
                        getLikes(accessToken)
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

    private fun getLikes(accessToken: String?) {
        viewModelScope.launch {
            likeRepository.getLikes("Bearer $accessToken").collectLatest { response ->
                when(response) {
                    is RustyResult.Success -> {
                        _uiState.value = _uiState.value.copy(
                            loading = false,
                            likes = response.data,
                            likesCount = response.data.size
                        )
                        Log.d(TAG, "getLikes: Likes got called")
                        Log.d(TAG, "getLikes: ${_uiState.value.likes.size}")
                    }
                    is RustyResult.Failure -> {
                        _uiState.update { state ->
                            state.copy(
                                loading = false,
                            )
                        }
                        _event.send(RustyEvents.ShowSnackBar(response.message ?: resProvider.getString(
                            R.string.unknown_error)))
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
                            bookmarks = response.data,
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

    fun onLikeClicked(postId: String, isLiked: Boolean, userId: String?) {
        viewModelScope.launch {
            val accessToken = sessionManager.accessToken.first()
            val refreshToken = sessionManager.refreshToken.first()
            val expiresAt = sessionManager.expiresAt.first()

            if (sessionManager.isAccessTokenExpired(accessToken, expiresAt)) {
                    refreshAccessToken(refreshToken)
            }
            _uiState.update { state ->
                state.copy(
                    isLiked = isLiked,
                )
            }

            val body = JsonObject()
            body.addProperty("like_id", UUID.randomUUID().toString())
            body.addProperty("post_id", postId)
            body.addProperty("user_id", userId)
            val like = liked(userId, postId)
            val isAlreadyLiked = like != null

            if (isAlreadyLiked) {
                unlikePost(accessToken, like.likeId)
            } else {
                likePost(accessToken, body)
            }
        }
    }

    private fun likePost(accessToken: String?, body: JsonObject) {
        viewModelScope.launch {
            likeRepository.likePost("Bearer $accessToken", body).collectLatest { result ->
                when(result) {
                    is RustyResult.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                loading = false,
                            )
                        }
                        getLikes(accessToken)
                    }
                    is RustyResult.Failure -> {
                        _uiState.update { state ->
                            state.copy(loading = false)
                        }
                        _event.send(RustyEvents.ShowSnackBar(result.message ?: resProvider.getString(
                            R.string.unknown_error))
                        )
                    }
                    is RustyResult.Loading -> {
                        _uiState.update { state ->
                            state.copy(loading = true)
                        }
                    }
                }
            }
        }
    }

    private fun unlikePost(accessToken: String?, likeId: String) {
        viewModelScope.launch {
            likeRepository.unlikePost("Bearer $accessToken", "eq.$likeId").collectLatest { result ->
                when(result) {
                    is RustyResult.Success -> {
                        _uiState.update { state ->
                            state.copy(loading = false)
                        }
                        getLikes(accessToken)
                    }
                    is RustyResult.Failure -> {
                        _uiState.update { state ->
                            state.copy(loading = false)
                        }
                        _event.send(RustyEvents.ShowSnackBar(result.message ?: resProvider.getString(
                            R.string.unknown_error))
                        )
                    }
                    is RustyResult.Loading -> {
                        _uiState.update { state ->
                            state.copy(loading = true)
                        }
                    }
                }
            }
        }
    }

    private fun liked(userId: String?, postId: String): Like? {
        return _uiState.value.likes.find { like -> like.userId == userId && like.postId == postId }
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
                            getBookmarks(accessToken)
                            _event.send(RustyEvents.ShowSnackBar(result.data.message))
                        }

                        is RustyResult.Failure -> {
                            _uiState.update { state ->
                                state.copy(
                                    bookmarkLoading = false
                                )
                            }
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
                        }
                    }
                }
        }
    }

    fun onShareClicked() {
        TODO("Not yet implemented")
    }
}