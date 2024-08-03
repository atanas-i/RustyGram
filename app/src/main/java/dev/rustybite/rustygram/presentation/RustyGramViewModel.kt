package dev.rustybite.rustygram.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rustybite.rustygram.data.local.SessionManager
import dev.rustybite.rustygram.presentation.ui.navigation.RustyGramNavUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RustyGramViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(RustyGramNavUiState())
    val uiState = _uiState.asStateFlow()

    fun initialize() {
        viewModelScope.launch {
            val isUserSignedIn = sessionManager.isUserSignedIn.first()
            val isUserOnboarded = sessionManager.isUserOnboarded.first()

            _uiState.value = _uiState.value.copy(
                isUserSignedIn = isUserSignedIn ?: false,
                isUserOnboarded = isUserOnboarded ?: true
            )
        }
    }
}