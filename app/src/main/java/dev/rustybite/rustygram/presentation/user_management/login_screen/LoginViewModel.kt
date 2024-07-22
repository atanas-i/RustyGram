package dev.rustybite.rustygram.presentation.user_management.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.repository.LoginRepository
import dev.rustybite.rustygram.presentation.ui.navigation.RustyRoutes
import dev.rustybite.rustygram.util.ResourceProvider
import dev.rustybite.rustygram.util.RustyEvents
import dev.rustybite.rustygram.util.RustyResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val resources: ResourceProvider
) : ViewModel() {
    //val languages = listOf("English (US)", "French", "Portuguese", "Spanish", "Kiswahili")

//        private val languages = mutableStateOf(
//            mutableListOf(
//                CheckboxData(isChecked = true, language = "English (US)"),
//                CheckboxData(isChecked = false, language = "French"),
//                CheckboxData(isChecked = false, language = "Portuguese"),
//                CheckboxData(isChecked = false, language = "Spanish"),
//                CheckboxData(isChecked = false, language = "Kiswahili"),
//            )
//        )
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()
    private val _event = Channel<RustyEvents>()
    val events = _event.receiveAsFlow()


    fun login(email: String, password: String) {
        viewModelScope.launch {
            val body = JsonObject()
            body.addProperty("email", email)
            body.addProperty("password", password)

            loginRepository.login(body).collectLatest { result ->
                when (result) {
                    is RustyResult.Success -> {
                        _event.send(RustyEvents.Navigate(RustyRoutes.CreateProfile))
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
            loginRepository.logout("Bearer {Preference.getToken()}").collectLatest { result ->
                when (result) {
                    is RustyResult.Success -> {
                        _uiState.value = _uiState.value.copy(
                            loading = false
                        )
                        _event.send(RustyEvents.Navigate(RustyRoutes.Login))
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
            _event.send(RustyEvents.Navigate(RustyRoutes.Registration))
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
            _event.send(RustyEvents.Navigate(RustyRoutes.ChangePassword))

        }
    }

}