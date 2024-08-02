package dev.rustybite.rustygram.presentation.user_management.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.local.SessionManager
import dev.rustybite.rustygram.data.repository.LoginRepository
import dev.rustybite.rustygram.data.repository.TokenManagementRepository
import dev.rustybite.rustygram.presentation.ui.navigation.OnBoardingRoutes
import dev.rustybite.rustygram.util.ResourceProvider
import dev.rustybite.rustygram.util.RustyEvents
import dev.rustybite.rustygram.util.RustyResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val resources: ResourceProvider,
    private val sessionManager: SessionManager,
    private val tokenManagementRepository: TokenManagementRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()
    private val _event = Channel<RustyEvents>()
    val events = _event.receiveAsFlow()

    init {
        viewModelScope.launch {
            val expiresAt = sessionManager.expiresAt.first()
            val accessToken = sessionManager.accessToken.first()
            val refreshToken = sessionManager.refreshToken.first()
            if (sessionManager.isAccessTokenExpired(accessToken, expiresAt)) {
                refreshAccessToken(refreshToken)
            }
        }
    }


    fun login(email: String, password: String) {
        viewModelScope.launch {
            val body = JsonObject()
            body.addProperty("email", email)
            body.addProperty("password", password)

            loginRepository.login(body).collectLatest { result ->
                when (result) {
                    is RustyResult.Success -> {
                        sessionManager.saveIsUserSignedIn(true)
                        _event.send(RustyEvents.Navigate(OnBoardingRoutes.CreateProfile))
                        _uiState.value = _uiState.value.copy(
                            loading = false
                        )
                    }

                    is RustyResult.Failure -> {
                        _uiState.value = _uiState.value.copy(
                            loading = false,
                            errorMessage = result.message
                        )
                        _event.send(
                            RustyEvents.ShowSnackBar(
                                result.message ?: resources.getString(R.string.unknown_error)
                            )
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

    fun logout() {
        viewModelScope.launch {
            val accessToken = sessionManager.accessToken.first()

            loginRepository.logout("Bearer $accessToken").collectLatest { result ->
                when (result) {
                    is RustyResult.Success -> {
                        _uiState.value = _uiState.value.copy(
                            loading = false
                        )
                        sessionManager.saveAccessToken("")
                        sessionManager.saveRefreshToken("")
                        sessionManager.saveExpiresAt(0L)
                        sessionManager.saveIsUserSignedIn(false)
                        _event.send(RustyEvents.Navigate(OnBoardingRoutes.Login))
                        _event.send(RustyEvents.ShowSnackBar(result.data.message))
                    }

                    is RustyResult.Failure -> {
                        _uiState.value = _uiState.value.copy(
                            loading = false,
                            errorMessage = result.message
                        )
                        _event.send(
                            RustyEvents.ShowSnackBar(
                                result.message ?: resources.getString(R.string.unknown_error)
                            )
                        )
                    }

                    is RustyResult.Loading -> {
                        _uiState.value = _uiState.value.copy(loading = true)
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
                tokenManagementRepository.refreshToken(body).collectLatest { result ->
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

    fun onOpenLanguageSelection() {
        _uiState.value = _uiState.value.copy(isLanguageSelectionToggled = true)
    }

    fun onCloseLanguageSelection() {
        _uiState.value = _uiState.value.copy(isLanguageSelectionToggled = false)

    }

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun onSignUpClicked() {
        viewModelScope.launch {
            _event.send(RustyEvents.Navigate(OnBoardingRoutes.Registration))
        }
    }

    fun onOptionSelected(selectedOption: String) {
        _uiState.value = _uiState.value.copy(selectedOption = selectedOption)
    }

    fun onSelectOption(selected: Boolean) {
        _uiState.value = _uiState.value.copy(
            selected = selected
        )
    }

    fun forgotPassword() {
        viewModelScope.launch {
            _event.send(RustyEvents.Navigate(OnBoardingRoutes.ChangePassword))

        }
    }

}