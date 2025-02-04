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
import dev.rustybite.rustygram.data.repository.UserRepository
import dev.rustybite.rustygram.presentation.ui.navigation.RustyGramNavUiState
import dev.rustybite.rustygram.presentation.ui.navigation.RustyGramRoutes
import dev.rustybite.rustygram.util.ResourceProvider
import dev.rustybite.rustygram.util.RustyEvents
import dev.rustybite.rustygram.util.RustyResult
import dev.rustybite.rustygram.util.TAG
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
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

    init {
        viewModelScope.launch {
            val sessionData = combine(
                sessionManager.accessToken,
                sessionManager.refreshToken,
                sessionManager.expiresAt,
                sessionManager.isUserSignedIn,
                sessionManager.isUserOnboarded
            ) { accessToken, refreshToken, expiresAt, isUserSignedIn, isUserOnboarded ->
                Triple(accessToken, refreshToken, expiresAt) to Pair(
                    isUserSignedIn,
                    isUserOnboarded
                )
            }.first()

            val (tokens, flags) = sessionData
            val (accessToken, refreshToken, expiresAt) = tokens
            val (isUserSignedIn, isUserOnboarded) = flags
            if (sessionManager.isAccessTokenExpired(accessToken, expiresAt)) {
                refreshAccessToken(refreshToken)
            } else {
                getUser(accessToken)
            }
        }
    }


    private fun getUser(accessToken: String?) {
        viewModelScope.launch {
            userRepository.getLoggedInUser("Bearer $accessToken")
                .collectLatest { result ->
                    when (result) {
                        is RustyResult.Success -> {
                            val userId = result.data.userId
                            _uiState.update { state ->
                                state.copy(
                                    loading = false,
                                    isSplashScreenReleased = true,
                                    userId = userId
                                )
                            }
                            getUserProfile(
                                "Bearer $accessToken",
                                userId
                            )
                        }
                        is RustyResult.Failure -> {
                            _uiState.value = _uiState.value.copy(
                                loading = false,
                                isSplashScreenReleased = true
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

    private fun getUserProfile(accessToken: String, userId: String) {
        viewModelScope.launch {
            profileRepository.getProfile(accessToken, "eq.$userId")
                .collectLatest { result ->
                    when (result) {
                        is RustyResult.Success -> {
                            val profile = result.data.firstOrNull()
                            sessionManager.saveIsUserOnboarded(profile != null)
                            _uiState.value = _uiState.value.copy(
                                loading = false,
                                profile = profile,
                                isUserOnboarded = profile != null
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

    fun refreshAccessToken(refreshToken: String?) {
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
                            Log.d(TAG, "refreshAccessToken: token successfull fetched")
                        }

                        is RustyResult.Failure -> {
                            _uiState.value = _uiState.value.copy(
                                loading = false,
                                errorMessage = result.message
                                    ?: resProvider.getString(R.string.unknown_error)
                            )
                            Log.d(TAG, "refreshAccessToken: Fetching token failed")
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

}