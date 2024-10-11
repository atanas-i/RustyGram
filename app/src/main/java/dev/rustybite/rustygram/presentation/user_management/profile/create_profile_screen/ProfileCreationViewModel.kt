package dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.local.SessionManager
import dev.rustybite.rustygram.data.repository.ProfileRepository
import dev.rustybite.rustygram.data.repository.StorageRepository
import dev.rustybite.rustygram.data.repository.TokenManagementRepository
import dev.rustybite.rustygram.presentation.ui.navigation.BottomNavScreen
import dev.rustybite.rustygram.presentation.ui.navigation.OnBoardingRoutes
import dev.rustybite.rustygram.util.ResourceProvider
import dev.rustybite.rustygram.util.RustyEvents
import dev.rustybite.rustygram.util.RustyResult
import dev.rustybite.rustygram.util.TAG
import dev.rustybite.rustygram.util.getFileFromUri
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID
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

//    fun createProfile(
//
//    ) {
//        viewModelScope.launch {
//
//            //uploadProfilePicture()
//
//            body.addProperty("user_profile_picture", _uiState.value.userProfileUrl)
//
//            Log.d(TAG, "createProfile: $body")
//
//            repository.createProfile().collect { result ->
//                when (result) {
//                    is RustyResult.Success -> {
//                        Log.d(TAG, "createProfile: Profile creation succeeded")
//                        _uiState.value = _uiState.value.copy(
//                            loading = false
//                        )
//                        _event.send(RustyEvents.ShowSnackBar(result.data.message))
//                        sessionManager.saveIsUserOnboarded(true)
//                        _event.send(RustyEvents.BottomScreenNavigate(BottomNavScreen.Home))
//                    }
//
//                    is RustyResult.Failure -> {
//                        _uiState.value = _uiState.value.copy(
//                            loading = false,
//                            errorMessage = result.message
//                                ?: resProvider.getString(R.string.unknown_error)
//                        )
//                        _event.send(RustyEvents.ShowSnackBar(result.message ?: resProvider.getString(R.string.unknown_error)))
//                    }
//
//                    is RustyResult.Loading -> {
//                        _uiState.value = _uiState.value.copy(
//                            loading = true
//                        )
//                    }
//                }
//            }
//        }
//    }

    fun createProfile(
        fullName: String,
        username: String,
        birthDate: String,
    ) {
        //Log.d(TAG, "uploadProfilePicture: Uploading...")
        viewModelScope.launch {
            val accessToken = sessionManager.accessToken.first()
            val refreshToken = sessionManager.refreshToken.first()
            val expiresAt = sessionManager.expiresAt.first()

            if (sessionManager.isAccessTokenExpired(accessToken, expiresAt)) {
                refreshAccessToken(refreshToken)
            }
            val uri = _uiState.value.userProfileUri
            if (uri != null) {
                val fileName = "user_id:profile_id:${LocalDateTime.now()}"
                getFileFromUri(uri, resProvider, fileName).collectLatest { fileResult ->
                    when (fileResult) {
                        is RustyResult.Success -> {
                            val file = fileResult.data
                            Log.d(TAG, "uploadProfilePicture: File retrieved.. ${file.name}")
                            storageRepository.uploadProfilePicture(file, "user_id_one", file.name)
                                .collectLatest { uploadResult ->
                                    when (uploadResult) {
                                        is RustyResult.Success -> {
                                            _uiState.value = _uiState.value.copy(
                                                userProfileUrl = uploadResult.data,
                                                loading = false
                                            )
                                            val body = JsonObject()
                                            body.addProperty("full_name", fullName)
                                            body.addProperty("user_name", username)
                                            body.addProperty("birth_date", birthDate)
                                            body.addProperty("created_at", LocalDateTime.now().toString())
                                            body.addProperty("profile_id", UUID.randomUUID().toString())
                                            body.addProperty("user_profile_picture", uploadResult.data)
                                            Log.d(TAG, "createProfile: $body")
                                            repository.createProfile("Bearer $accessToken", body).collectLatest { result ->
                                                when(result) {
                                                    is RustyResult.Success -> {
                                                        _uiState.value = _uiState.value.copy(
                                                            loading = false
                                                        )
                                                        sessionManager.saveIsUserOnboarded(true)
                                                        _event.send(RustyEvents.BottomScreenNavigate(BottomNavScreen.Home))
                                                    }
                                                    is RustyResult.Failure -> {
                                                        _uiState.value = _uiState.value.copy(
                                                            loading = false
                                                        )
                                                        _event.send(RustyEvents.ShowSnackBar(result.message ?: resProvider.getString(R.string.unknown_error)))
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
                                errorMessage = fileResult.message
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
                    when (result) {
                        is RustyResult.Success -> {
                            if (result.data.map { it.userName }.contains(username)) {
                                _uiState.value = _uiState.value.copy(
                                    errorMessage = resProvider.getString(R.string.username_available),
                                    loading = false
                                )
                            } else {
                                _uiState.value = _uiState.value.copy(
                                    loading = false
                                )
                                _event.send(RustyEvents.OnBoardingNavigate(OnBoardingRoutes.CreateProfilePicture))
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
            _event.send(RustyEvents.OnBoardingNavigate(OnBoardingRoutes.CreateFullName))
        }
    }

    fun onHaveAccountClicked() {
        viewModelScope.launch {
            _event.send(RustyEvents.OnBoardingNavigate(OnBoardingRoutes.Login))
        }
    }

//        fun navigateToProfilePictureScreen() {
//            viewModelScope.launch {
//                _event.send(RustyEvents.Navigate(OnBoardingRoutes.CreateProfilePicture))
//            }
//        }

    fun navigateToUsernameScreen() {
        viewModelScope.launch {
            _event.send(RustyEvents.OnBoardingNavigate(OnBoardingRoutes.CreateUsername))
        }
    }

//    fun onAddPictureClicked() {
//        viewModelScope.launch {
//
//        }
//    }

    fun onSkipClicked() {
        viewModelScope.launch {

        }
    }
}