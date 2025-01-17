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
import dev.rustybite.rustygram.presentation.ui.navigation.BottomNavScreen
import dev.rustybite.rustygram.presentation.ui.navigation.OnBoardingRoutes
import dev.rustybite.rustygram.presentation.ui.navigation.RustyGramNavUiState
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
        private var tokenJob: Job? = null

        private val _isSplashScreenReleased = MutableStateFlow(false)
        val isSplashScreenReleased = _isSplashScreenReleased.asStateFlow()

        init {
                viewModelScope.launch {
                        val sessionData = combine(
                                sessionManager.accessToken,
                                sessionManager.refreshToken,
                                sessionManager.expiresAt,
                                sessionManager.isUserSignedIn,
                                sessionManager.isUserOnboarded
                        ) { accessToken, refreshToken, expiresAt, isUserSignedIn, isUserOnboarded ->
                                Triple(accessToken, refreshToken, expiresAt) to Pair(isUserSignedIn, isUserOnboarded)
                        }.first()

                        val (tokens, flags) = sessionData
                        val (accessToken, refreshToken, expiresAt) = tokens
                        val (isUserSignedIn, isUserOnboarded) = flags

                        when {
                                accessToken == null && refreshToken == null && expiresAt == null -> {
                                        _uiState.value = _uiState.value.copy(
                                                isUserSignedIn = false,
                                                loading = false
                                        )
                                }

                                isUserSignedIn == true && isUserOnboarded == false -> {
                                        _uiState.update { state ->
                                                state.copy(
                                                        isUserOnboarded = false,
                                                        isUserSignedIn = isUserSignedIn,
                                                        loading = false
                                                )
                                        }
                                }

                                isUserSignedIn == true && isUserOnboarded == true -> {
                                        if (sessionManager.isAccessTokenExpired(
                                                        accessToken,
                                                        expiresAt
                                                )
                                        ) {
                                                refreshAccessToken(refreshToken)
                                        }
                                        getUser(accessToken)
                                        _uiState.update { state ->
                                                state.copy(
                                                        isUserSignedIn = isUserSignedIn,
                                                        isUserOnboarded = isUserOnboarded,
                                                        loading = false
                                                )
                                        }
                                }

                                else -> {
                                        _uiState.update { state ->
                                                state.copy(
                                                        isUserOnboarded = false,
                                                        isUserSignedIn = false,
                                                        loading = false
                                                )
                                        }
                                }
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
                                                        _uiState.value = _uiState.value.copy(
                                                                loading = false,
                                                                userId = userId
                                                        )
                                                        getUserProfile(
                                                                "Bearer $accessToken",
                                                                userId
                                                        )
                                                }

                                                is RustyResult.Failure -> {
                                                        _uiState.value = _uiState.value.copy(
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

        private suspend fun refreshAccessToken(refreshToken: String?) {
                tokenJob?.cancel()
                tokenJob = viewModelScope.launch {
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
                                Log.d(
                                        TAG,
                                        "refreshAccessToken: Token is null and cannot refresh it"
                                )
                        }
                }
                //tokenJob?.join()
        }

}