package dev.rustybite.rustygram.presentation.user_management.registration_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.local.SessionManager
import dev.rustybite.rustygram.data.repository.UserRegistrationRepository
import dev.rustybite.rustygram.presentation.ui.navigation.OnBoardingRoutes
import dev.rustybite.rustygram.util.ResourceProvider
import dev.rustybite.rustygram.util.RustyEvents
import dev.rustybite.rustygram.util.RustyResult
import dev.rustybite.rustygram.util.TAG
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserRegistrationViewModel @Inject constructor(
    private val registrationRepository: UserRegistrationRepository,
    private val resources: ResourceProvider,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState = _uiState.asStateFlow()
    private val _event = Channel<RustyEvents>()
    val event = _event.receiveAsFlow()

    fun registerUser(email: String, password: String) {
        val body = JsonObject()
        body.addProperty("email", email)
        body.addProperty("password", password)

        viewModelScope.launch {
            registrationRepository.registerUser(body).collectLatest { result ->
                when (result) {
                    is RustyResult.Success -> {
                        sessionManager.saveIsUserSignedIn(true)
                        sessionManager.saveAccessToken(result.data.accessToken)
                        sessionManager.saveRefreshToken(result.data.refreshToken)
                        sessionManager.saveExpiresAt(result.data.expiresAt)
                        _event.send(RustyEvents.Navigate(OnBoardingRoutes.CreateProfile))
                        _uiState.value = _uiState.value.copy(
                            loading = false,
                        )
                    }
                    is RustyResult.Failure -> {
                        _uiState.value = _uiState.value.copy(
                            errorMessage = result.message,
                            loading = false
                        )
                        _event.send(RustyEvents.ShowSnackBar(result.message ?: resources.getString(R.string.unknown_error)))
                    }
                    is RustyResult.Loading -> {
                        _uiState.value = _uiState.value.copy(loading = true)
                    }
                }
            }
        }
    }

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onTokenChange(token: String) {
        _uiState.value = _uiState.value.copy(token = token)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun onHaveAccountClicked() {
        viewModelScope.launch {
            _event.send(RustyEvents.Navigate(OnBoardingRoutes.Login))
        }
    }

    fun onSignUpWithPhone() {
        viewModelScope.launch {
            _event.send(RustyEvents.ShowSnackBar(resources.getString(R.string.phone_login_feature_not_available)))
        }
    }

    fun onShowPasswordClicked() {
        _uiState.value = _uiState.value.copy(
            isPasswordVisible = !_uiState.value.isPasswordVisible
        )
    }


}