package dev.rustybite.rustygram.presentation.user_management.profile.view_profile_screen

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
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val tokenRepository: TokenManagementRepository,
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository,
    private val resProvider: ResourceProvider
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

            userRepository.getLoggedInUser("Bearer $accessToken").collectLatest { userResult ->
                when (userResult) {
                    is RustyResult.Success -> {
                        _uiState.value = _uiState.value.copy(
                            loading = false,
                        )
                        repository.getProfile("Bearer $accessToken", "eq.${userResult.data.userId}").collectLatest { profileResult ->
                            when (profileResult) {
                                is RustyResult.Success -> {
                                    _uiState.value = _uiState.value.copy(
                                        loading = false,
                                        profile = profileResult.data[0]
                                    )
                                    Log.d(TAG, "Fetch profile: ${profileResult.data} ")
                                }
                                is RustyResult.Failure -> {
                                    _uiState.value = _uiState.value.copy(
                                        loading = false,
                                    )
                                    _event.send(RustyEvents.ShowSnackBar(profileResult.message ?: resProvider.getString(R.string.unknown_error)))
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
                            loading = false,
                        )
                        _event.send(RustyEvents.ShowSnackBar(userResult.message ?: resProvider.getString(R.string.unknown_error)))
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
            } else {
                Log.d(TAG, "refreshAccessToken: Token is null and cannot refresh it")
            }
        }
    }

}