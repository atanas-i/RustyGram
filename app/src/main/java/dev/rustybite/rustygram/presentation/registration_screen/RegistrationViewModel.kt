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

    fun requestOtp(email: String) {

        val body = JsonObject()
        body.addProperty("email", email)

        Log.d(TAG, "requestOtp: Message is $body ")
        viewModelScope.launch {
            registrationRepository.requestOtp(body).collectLatest { result ->
                when (result) {
                    is RustyResult.Success -> {
                        _event.send(RustyEvents.Navigate(RustyRoutes.VerifyOtp))
                    }
                    is RustyResult.Failure -> {
                        _uiState.value = _uiState.value.copy(
                            errorMessage = result.message,
                            loading = false
                        )
                        Log.d(TAG, "requestOtp: Message is ${result.message} ")
                        _event.send(RustyEvents.ShowSnackBar(result.message ?: ""))
                    }
                    is RustyResult.Loading -> {
                        _uiState.value = _uiState.value.copy(loading = true)
                    }
                }
            }
        }
    }

    fun verifyOtp(otp: String) {
        TODO("Not yet implemented")
    }

    fun createPassword(password: String) {
        TODO("Not yet implemented")
    }

    fun registerUser(
        fullName: String,
        birthday: String,
        username: String,
        profilePicture: String?
    ) {}

    fun navigateToLogin() {
        TODO("Not yet implemented")
    }

    fun navigateToOtp() {
        TODO("Not yet implemented")
    }

    fun navigateToPassword() {
        TODO("Not yet implemented")
    }

    fun navigateToRegistration() {
        TODO("Not yet implemented")
    }

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onOtpChange(otp: String) {
        _uiState.value = _uiState.value.copy(otp = otp)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
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