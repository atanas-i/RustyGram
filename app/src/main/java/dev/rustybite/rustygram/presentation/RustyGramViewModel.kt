package dev.rustybite.rustygram.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.local.SessionManager
import dev.rustybite.rustygram.data.repository.ProfileRepository
import dev.rustybite.rustygram.data.repository.TokenManagementRepository
import dev.rustybite.rustygram.data.repository.LoginRepository
import dev.rustybite.rustygram.data.repository.UserRepository
import dev.rustybite.rustygram.presentation.ui.navigation.RustyGramNavUiState
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
class RustyGramViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val profileRepository: ProfileRepository,
    private val tokenRepository: TokenManagementRepository,
    private val userRepository: UserRepository,
    private val resProvider: ResourceProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(RustyGramNavUiState())
    val uiState = _uiState.asStateFlow()
    private val _event = Channel<RustyEvents>()
    val event = _event.receiveAsFlow()

    private val _isSplashScreenReleased = MutableStateFlow(false)
    val isSplashScreenReleased = _isSplashScreenReleased.asStateFlow()

    init {
        viewModelScope.launch {
            val isUserSignedIn = sessionManager.isUserSignedIn.first() != null &&
                    sessionManager.isUserSignedIn.first() == true
            val isUserOnboarded = sessionManager.isUserOnboarded.first() != null &&
                    sessionManager.isUserOnboarded.first() == true
            _isSplashScreenReleased.value = sessionManager.isUserSignedIn.first() != null &&
                    sessionManager.isUserOnboarded.first() != null

            _uiState.value = _uiState.value.copy(
                isUserSignedIn = isUserSignedIn,
                isUserOnboarded = isUserOnboarded
            )
        }
    }

    init {
        viewModelScope.launch {
            val accessToken = sessionManager.accessToken.first()
            val refreshToken = sessionManager.refreshToken.first()
            val expiresAt = sessionManager.expiresAt.first()
            if (sessionManager.isAccessTokenExpired(accessToken, expiresAt)) {
                refreshAccessToken(refreshToken)
            }
//            if (_uiState.value.userId.isEmpty()) {
//                userRepository.getLoggedInUser(accessToken!!).collectLatest { result ->
//                    when(result) {
//                        is RustyResult.Success -> {
//                            _uiState.value = _uiState.value.copy(
//                                userId = result.data.userId,
//                                loading = false
//                            )
//                            val body = JsonObject()
//                            body.addProperty("user_id", _uiState.value.userId)
//                            profileRepository.getProfile(accessToken, body).collectLatest { profileResult ->
//                                when(profileResult) {
//                                    is RustyResult.Success -> {
//                                        _uiState.value = _uiState.value.copy(
//                                            userProfilePicture = profileResult.data.userProfilePicture,
//                                            loading = false
//                                        )
//                                    }
//                                    is RustyResult.Failure -> {
//                                        _uiState.value = _uiState.value.copy(
//                                            loading = false,
//                                            errorMessage = profileResult.message
//                                                ?: resProvider.getString(R.string.unknown_error)
//                                        )
//                                        _event.send(RustyEvents.ShowSnackBar(profileResult.message
//                                            ?: resProvider.getString(R.string.unknown_error)))
//                                    }
//                                    is RustyResult.Loading -> {
//                                        _uiState.value = _uiState.value.copy(
//                                            loading = true
//                                        )
//                                    }
//                                }
//                            }
//                        }
//                        is RustyResult.Failure -> {
//                            _uiState.value = _uiState.value.copy(
//                                loading = false,
//                                errorMessage = result.message
//                                    ?: resProvider.getString(R.string.unknown_error)
//                            )
//                            _event.send(RustyEvents.ShowSnackBar(result.message
//                                ?: resProvider.getString(R.string.unknown_error)))
//                        }
//                        is RustyResult.Loading -> {
//                            _uiState.value = _uiState.value.copy(
//                                loading = true
//                            )
//                        }
//                    }
//                }
//            } else {
//                val body = JsonObject()
//                body.addProperty("user_id", _uiState.value.userId)
//                profileRepository.getProfile(refreshToken!!, body).collectLatest { result ->
//                    when(result) {
//                        is RustyResult.Success -> {
//                            _uiState.value = _uiState.value.copy(
//                                userProfilePicture = result.data.userProfilePicture,
//                                loading = false
//                            )
//                        }
//                        is RustyResult.Failure -> {
//                            _uiState.value = _uiState.value.copy(
//                                loading = false,
//                                errorMessage = result.message
//                                    ?: resProvider.getString(R.string.unknown_error)
//                            )
//                            _event.send(RustyEvents.ShowSnackBar(result.message
//                                ?: resProvider.getString(R.string.unknown_error)))
//                        }
//                        is RustyResult.Loading -> {
//                            _uiState.value = _uiState.value.copy(
//                                loading = true
//                            )
//                        }
//                    }
//                }
//            }
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
            } else {
                Log.d(TAG, "refreshAccessToken: Token is null and cannot refresh it")
            }
        }
    }

}