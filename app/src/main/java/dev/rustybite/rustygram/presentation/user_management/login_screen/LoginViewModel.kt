package dev.rustybite.rustygram.presentation.user_management.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.local.SessionManager
import dev.rustybite.rustygram.data.repository.LoginRepository
import dev.rustybite.rustygram.data.repository.ProfileRepository
import dev.rustybite.rustygram.data.repository.TokenManagementRepository
import dev.rustybite.rustygram.data.repository.UserRepository
import dev.rustybite.rustygram.presentation.ui.navigation.BottomNavScreen
import dev.rustybite.rustygram.presentation.ui.navigation.OnBoardingRoutes
import dev.rustybite.rustygram.util.ResourceProvider
import dev.rustybite.rustygram.util.RustyEvents
import dev.rustybite.rustygram.util.RustyResult
import kotlinx.coroutines.Job
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
    private val tokenManagementRepository: TokenManagementRepository,
    private val userRepository: UserRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()
    private val _event = Channel<RustyEvents>()
    val events = _event.receiveAsFlow()
    private var tokenJob: Job? = null

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
                        sessionManager.saveAccessToken(result.data.accessToken)
                        sessionManager.saveRefreshToken(result.data.refreshToken)
                        sessionManager.saveExpiresAt(result.data.expiresAt)
                        getLoggedInUser(result.data.accessToken)
//                        val isUserOnboarded = sessionManager.isUserOnboarded.first()
//                        if (isUserOnboarded != null && isUserOnboarded) {
//                            _event.send(RustyEvents.BottomScreenNavigate(BottomNavScreen.Home))
//                        } else {
//                            _event.send(RustyEvents.OnBoardingNavigate(OnBoardingRoutes.CreateBirthDate))
//                        }
//                        _uiState.value = _uiState.value.copy(
//                            loading = false
//                        )
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
                        _event.send(RustyEvents.OnBoardingNavigate(OnBoardingRoutes.Login))
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

    private fun getLoggedInUser(accessToken: String?) {
        viewModelScope.launch {
            userRepository.getLoggedInUser("Bearer $accessToken").collectLatest { result ->
                when(result) {
                    is RustyResult.Success -> {
                        val userId = result.data.userId
//                        _uiState.value = _uiState.value.copy(
//                            loading = false,
//                        )
                        getUserProfile("Bearer $accessToken", userId)
                    }
                    is RustyResult.Failure -> {
                        _uiState.value = _uiState.value.copy(
                            errorMessage = result.message,
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
            val isUserOnboarded = sessionManager.isUserOnboarded.first()
            profileRepository.getProfile(accessToken, "eq.$userId").collectLatest { result ->
                when(result) {
                    is RustyResult.Success -> {
                        val profile = result.data.firstOrNull()
                        sessionManager.saveIsUserOnboarded(profile != null)
                        _uiState.value = _uiState.value.copy(
                            loading = false
                        )
                        if (profile != null && profile.userId == userId) {
                            _event.send(RustyEvents.BottomScreenNavigate(BottomNavScreen.Home))
                        } else {
                            _event.send(RustyEvents.OnBoardingNavigate(OnBoardingRoutes.CreateBirthDate))
                        }
                    }
                    is RustyResult.Failure -> {
                        _uiState.value = _uiState.value.copy(
                            errorMessage = result.message,
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

    private suspend fun refreshAccessToken(refreshToken: String?) {
        tokenJob?.cancel()
        tokenJob = viewModelScope.launch {
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
        tokenJob?.join()
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
            _event.send(RustyEvents.OnBoardingNavigate(OnBoardingRoutes.Registration))
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
            _event.send(RustyEvents.OnBoardingNavigate(OnBoardingRoutes.ChangePassword))

        }
    }

    fun onShowPasswordClicked() {
        _uiState.value = _uiState.value.copy(
            isPasswordVisible = !_uiState.value.isPasswordVisible
        )
    }
}