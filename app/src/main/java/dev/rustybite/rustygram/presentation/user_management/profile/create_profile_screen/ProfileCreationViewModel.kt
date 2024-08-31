package dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen

import android.content.ContentResolver.MimeTypeInfo
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.local.SessionManager
import dev.rustybite.rustygram.data.repository.ProfileRepository
import dev.rustybite.rustygram.data.repository.StorageRepository
import dev.rustybite.rustygram.data.repository.TokenManagementRepository
import dev.rustybite.rustygram.presentation.ui.navigation.OnBoardingRoutes
import dev.rustybite.rustygram.util.ResourceProvider
import dev.rustybite.rustygram.util.RustyEvents
import dev.rustybite.rustygram.util.RustyResult
import dev.rustybite.rustygram.util.getFileFromUri
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.BufferedInputStream
import java.io.File
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val sessionManager: SessionManager,
    private val resProvider: ResourceProvider,
    private val storageRepository: StorageRepository,
    private val tokenRepository: TokenManagementRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()
    private val _event = Channel<RustyEvents>()
    val event = _event.receiveAsFlow()
    private val profilePicturePath = MutableStateFlow("")

    fun createProfile(fullName: String, username: String, birthDate: String, profilePicture: Uri) {
        viewModelScope.launch {
            val accessToken = sessionManager.accessToken.first()
            val body = JsonObject()
            body.addProperty("fullName", fullName)
            body.addProperty("username", username)
            body.addProperty("birthDate", birthDate)
            body.addProperty("profilePicture", profilePicture.toString())

            repository.createProfile("Bearer $accessToken", body).collect { result ->
                when (result) {
                    is RustyResult.Success -> {
                        uploadProfilePicture()
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

    private fun uploadProfilePicture() {
        viewModelScope.launch {
            val uri = _uiState.value.userProfileUri
            if (uri != null) {
                getFileFromUri(uri, resProvider, "").collectLatest { result ->
                    when (result) {
                        is RustyResult.Success -> {
                            val file = result.data
                            storageRepository.uploadProfilePicture(file, "user_id_one", file.name)
                                .collectLatest { uploadResult ->
                                    when (uploadResult) {
                                        is RustyResult.Success -> {
                                            _uiState.value = _uiState.value.copy(
                                                userProfileUrl = uploadResult.data,
                                                loading = false
                                            )
                                        }

                                        is RustyResult.Failure -> {
                                            _uiState.value = _uiState.value.copy(
                                                errorMessage = uploadResult.message
                                                    ?: resProvider.getString(R.string.unknown_error),
                                                loading = false
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

                        is RustyResult.Failure -> {
                            _uiState.value = _uiState.value.copy(
                                errorMessage = result.message
                                    ?: resProvider.getString(R.string.unknown_error)
                            )
                        }

                        is RustyResult.Loading -> Unit
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
        
    fun navigateToProfilePictureScreen(username: String) {
        viewModelScope.launch {
            val accessToken = sessionManager.accessToken.first()
            val refreshToken = sessionManager.refreshToken.first()
            val expiresAt = sessionManager.expiresAt.first()
            if (sessionManager.isAccessTokenExpired(accessToken, expiresAt)) {
                refreshAccessToken(refreshToken)
            }
            if (accessToken != null) {
                repository.getProfiles(accessToken).collectLatest { result ->
                    when(result) {
                        is RustyResult.Success -> {
                            if(result.data.map { it.userName }.contains(username)) {
                                _uiState.value = _uiState.value.copy(
                                    errorMessage = resProvider.getString(R.string.username_available),
                                    loading = false
                                )
                            } else {
                                _uiState.value = _uiState.value.copy(
                                    loading = false
                                )
                                _event.send(RustyEvents.Navigate(OnBoardingRoutes.CreateProfilePicture))
                            }
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


        fun onFullNameChange(fullName: String) {
            _uiState.value = _uiState.value.copy(fullName = fullName)
        }

        fun onUsernameChange(username: String) {
            _uiState.value = _uiState.value.copy(username = username)
        }

        fun onBirthDateChange(birthDate: Long?) {
            _uiState.value = _uiState.value.copy(birthDate = birthDate)
        }

        fun onProfileUriChange(profilePicture: Uri) {
            _uiState.value = _uiState.value.copy(userProfileUri = profilePicture)
        }

        fun navigateToFullNameScreen() {
            viewModelScope.launch {
                _event.send(RustyEvents.Navigate(OnBoardingRoutes.CreateFullName))
            }
        }

        fun onHaveAccountClicked() {
            viewModelScope.launch {
                _event.send(RustyEvents.Navigate(OnBoardingRoutes.Login))
            }
        }

//        fun navigateToProfilePictureScreen() {
//            viewModelScope.launch {
//                _event.send(RustyEvents.Navigate(OnBoardingRoutes.CreateProfilePicture))
//            }
//        }

        fun navigateToUsernameScreen() {
            viewModelScope.launch {
                _event.send(RustyEvents.Navigate(OnBoardingRoutes.CreateUsername))
            }
        }

    fun onAddPictureClicked() {
        viewModelScope.launch {

        }
    }

    fun onSkipClicked() {
        viewModelScope.launch {

        }
    }
}