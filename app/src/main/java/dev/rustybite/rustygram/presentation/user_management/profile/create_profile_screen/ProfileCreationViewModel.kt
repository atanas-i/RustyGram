package dev.rustybite.rustygram.presentation.user_management.profile.create_profile_screen

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.local.SessionManager
import dev.rustybite.rustygram.data.repository.ProfileRepository
import dev.rustybite.rustygram.util.ResourceProvider
import dev.rustybite.rustygram.util.RustyEvents
import dev.rustybite.rustygram.util.RustyResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val sessionManager: SessionManager,
    private val resProvider: ResourceProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()
    private val _event = Channel<RustyEvents>()
    val event = _event.receiveAsFlow()

    fun createProfile(fullName: String, username: String, birthDate: String, profilePicture: Uri) {
        viewModelScope.launch {
            val accessToken = sessionManager.accessToken.first()
            val body = JsonObject()
            body.addProperty("fullName", fullName)
            body.addProperty("username", username)
            body.addProperty("birthDate", birthDate)
            body.addProperty("profilePicture", profilePicture.toString())

            repository.createProfile("Bearer $accessToken", body).collect { result ->
                when(result) {
                    is RustyResult.Success -> {

                    }
                    is RustyResult.Failure -> {
                        _uiState.value = _uiState.value.copy(
                            loading = false,
                            errorMessage = result.message ?: resProvider.getString(R.string.unknown_error)
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

    fun onFullNameChange(fullName: String) {
        _uiState.value = _uiState.value.copy(fullName = fullName)
    }

    fun onUsernameChange(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
    }

    fun onBirthDateChange(birthDate: LocalDateTime) {
        _uiState.value = _uiState.value.copy(birthDate = birthDate.toString())
    }
    fun onProfileUriChange(profilePicture: Uri) {
        _uiState.value = _uiState.value.copy(userProfileUri = profilePicture)
    }

}