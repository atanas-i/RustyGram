package dev.rustybite.rustygram.presentation.registration_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.repository.UserRegistrationRepository
import dev.rustybite.rustygram.presentation.ui.navigation.RustyRoutes
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
class RegistrationViewModel @Inject constructor(
    private val registrationRepository: UserRegistrationRepository,
    private val resources: ResourceProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState = _uiState.asStateFlow()
    private val _event = Channel<RustyEvents>()
    val event = _event.receiveAsFlow()

    fun registerUser(email: String, password: String) {
        Log.d(TAG, "registerUser: Received email is $email")
        val body = JsonObject()
        body.addProperty("email", email)
        body.addProperty("password", password)

        viewModelScope.launch {
            registrationRepository.registerUser(body).collectLatest { result ->
                when (result) {
                    is RustyResult.Success -> {
                        val user = result.data
                        Log.d(TAG, "registerUser: Returned user is $user")
                        _uiState.value = _uiState.value.copy(
                            loading = false,
                            email = user.email
                        )
                        if (user.isEmailVerified) {
                            _event.send(RustyEvents.Navigate(RustyRoutes.CreateProfile))
                        } else {
                            _uiState.value = _uiState.value.copy(
                                loading = false,
                                email = user.email
                            )
                            _event.send(RustyEvents.Navigate(RustyRoutes.VerifyOtp))
                        }
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

    fun requestOtp(email: String) {
        viewModelScope.launch {
            val body = JsonObject()
            body.addProperty("email", email)
            registrationRepository.requestOtp(body).collectLatest { result ->
                when (result) {
                    is RustyResult.Success -> {
                        _uiState.value = _uiState.value.copy(
                            loading = false
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
                        _uiState.value = _uiState.value.copy(
                            loading = true
                        )
                    }
                }
            }
        }
    }

    fun verifyOtp(
        type: String = "email",
        email: String,
        token: String
    ) {
        val body = JsonObject()
        body.addProperty("type", type)
        body.addProperty("email", email)
        body.addProperty("token", token)

        viewModelScope.launch {
            registrationRepository.verifyOtp(body).collectLatest { result ->
                when(result) {
                    is RustyResult.Success -> {
                        _event.send(RustyEvents.Navigate(RustyRoutes.CreateProfile))
                        _event.send(RustyEvents.ShowSnackBar(result.data.message))
                        _uiState.value = _uiState.value.copy(
                            loading = false
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
                        _uiState.value = _uiState.value.copy(
                            loading = true
                        )
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
            _event.send(RustyEvents.Navigate(RustyRoutes.Login))
        }
    }

    fun onSignUpWithPhone() {
        viewModelScope.launch {
            _event.send(RustyEvents.ShowSnackBar(resources.getString(R.string.phone_login_feature_not_available)))
        }
    }


}