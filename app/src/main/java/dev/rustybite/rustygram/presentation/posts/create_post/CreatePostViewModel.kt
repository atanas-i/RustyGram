package dev.rustybite.rustygram.presentation.posts.create_post

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.local.SessionManager
import dev.rustybite.rustygram.data.repository.GalleryRepository
import dev.rustybite.rustygram.data.repository.StorageRepository
import dev.rustybite.rustygram.data.repository.TokenManagementRepository
import dev.rustybite.rustygram.data.repository.UserRepository
import dev.rustybite.rustygram.presentation.posts.create_post.image_picker.MediaPickerUiState
import dev.rustybite.rustygram.presentation.ui.navigation.BottomNavScreen
import dev.rustybite.rustygram.presentation.ui.navigation.RustyAppRoutes
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
class CreatePostViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val tokenRepository: TokenManagementRepository,
    private val resProvider: ResourceProvider,
    private val galleryRepository: GalleryRepository,
    private val storageRepository: StorageRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreatePostUiState())
    val uiState = _uiState.asStateFlow()
    private val _galleryUiState = MutableStateFlow(MediaPickerUiState())
    val galleryUiState = _galleryUiState.asStateFlow()
    private val _events = Channel<RustyEvents>()
    val events = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            val accessToken = sessionManager.accessToken.first()
            val refreshToken = sessionManager.refreshToken.first()
            val expiresAt = sessionManager.expiresAt.first()

            if (sessionManager.isAccessTokenExpired(accessToken, expiresAt)) {
                refreshAccessToken(refreshToken)
            }
        }
    }

    init {
        viewModelScope.launch {
            galleryRepository.images.collectLatest { result ->
                when(result) {
                    is RustyResult.Success -> {
                        _galleryUiState.value = _galleryUiState.value.copy(
                            images = result.data
                        )
                    }
                    is RustyResult.Failure -> {
                        _galleryUiState.value = _galleryUiState.value.copy(
                            errorMessage = result.message
                        )
                    }
                    is RustyResult.Loading -> {}
                }
            }
        }
    }

    fun createPost() {
        viewModelScope.launch {
            val accessToken = sessionManager.accessToken.first()
            val refreshToken = sessionManager.refreshToken.first()
            val expiresAt = sessionManager.expiresAt.first()

            if (sessionManager.isAccessTokenExpired(accessToken, expiresAt)) {
                refreshAccessToken(refreshToken)
            }

            storageRepository.uploadPostImage(_uiState.value.uri).collectLatest { response ->
                when(response) {
                    is RustyResult.Success -> {
                        _events.send(RustyEvents.ShowSnackBar(response.data))
                    }
                    is RustyResult.Failure -> {
                        _uiState.value = _uiState.value.copy(
                            loading = false,
                        )
                        _events.send(RustyEvents.ShowSnackBar(response.message ?: resProvider.getString(R.string.unknown_error)))
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

    fun onUriChange(uri: Uri) {
        _uiState.value = _uiState.value.copy(
            uri = uri
        )
    }

    fun onCaptionChange(caption: String) {
        _uiState.value = _uiState.value.copy(
            caption = caption
        )
    }

    fun onPostCaptionChange(caption: String) {
        _uiState.value = _uiState.value.copy(
            postCaption = caption
        )
    }

    fun onImageSelected(uri: Uri) {

    }

    fun moveToEditScreen() {
        viewModelScope.launch {
            _events.send(RustyEvents.Navigate(RustyAppRoutes.EditScreen))
        }
    }

    fun moveBack() {
        viewModelScope.launch {
            _events.send(RustyEvents.PopBackStack)
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

    fun goToEdit() {
        viewModelScope.launch {
            _events.send(RustyEvents.ShowSnackBar(resProvider.getString(R.string.unavailable_feature)))
        }
    }

    fun goToFinalizePost() {
        viewModelScope.launch {
            _events.send(RustyEvents.Navigate(RustyAppRoutes.FinalizePostScreen))
        }
    }

    fun onAddLocation() {
        viewModelScope.launch {
            _events.send(RustyEvents.ShowSnackBar(resProvider.getString(R.string.unavailable_feature)))

        }
    }

    fun onTagPeople() {
        viewModelScope.launch {
            _events.send(RustyEvents.ShowSnackBar(resProvider.getString(R.string.unavailable_feature)))

        }
    }

    fun addMusic() {
        viewModelScope.launch {
            _events.send(RustyEvents.ShowSnackBar(resProvider.getString(R.string.unavailable_feature)))
        }
    }

    fun addAudience() {
        viewModelScope.launch {
            _events.send(RustyEvents.ShowSnackBar(resProvider.getString(R.string.unavailable_feature)))
        }
    }

    fun addReminder() {
        viewModelScope.launch {
            _events.send(RustyEvents.ShowSnackBar(resProvider.getString(R.string.unavailable_feature)))
        }
    }

    fun share() {
        Log.d(TAG, "share: button is clicked")
        viewModelScope.launch {
            val body = JsonObject()
            body.addProperty("caption", uiState.value.caption)
            _events.send(RustyEvents.BottomScreenNavigate(BottomNavScreen.HomeGraph))
            _events.send(RustyEvents.ShowSnackBar(resProvider.getString(R.string.share_post_success)))
        }
    }
}