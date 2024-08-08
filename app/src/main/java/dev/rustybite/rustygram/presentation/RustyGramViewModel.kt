package dev.rustybite.rustygram.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rustybite.rustygram.data.local.SessionManager
import dev.rustybite.rustygram.presentation.ui.navigation.RustyGramNavUiState
import dev.rustybite.rustygram.util.TAG
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

    private val _isSplashScreenReleased = MutableStateFlow(false)
    val isSplashScreenReleased = _isSplashScreenReleased.asStateFlow()

    init {
        viewModelScope.launch {
            val isUserSignedIn = sessionManager.isUserSignedIn.first() != null &&
                    sessionManager.isUserSignedIn.first() == true
            val isUserOnboarded = sessionManager.isUserOnboarded.first() != null &&
                    sessionManager.isUserOnboarded.first() == true
            _isSplashScreenReleased.value = sessionManager.isUserSignedIn.first() != null &&
                    sessionManager.isUserOnboarded.first() != null

            _uiState.value = _uiState.value.copy(
                isUserSignedIn = isUserSignedIn,
                isUserOnboarded = isUserOnboarded
            )

            Log.d(TAG, "Is user logged in: ${sessionManager.isUserSignedIn.first()}")
            Log.d(TAG, "Is user onboarded: ${sessionManager.isUserOnboarded.first()}")
        }
    }

//    fun fetchAuthStatus() {
//        viewModelScope.launch {
//            val isUserSignedIn = sessionManager.isUserSignedIn.first() != null &&
//                    sessionManager.isUserSignedIn.first() == true
//            val isUserOnboarded = sessionManager.isUserOnboarded.first() != null &&
//                    sessionManager.isUserOnboarded.first() == true
//
//            _uiState.value = _uiState.value.copy(
//                isUserSignedIn = isUserSignedIn,
//                isUserOnboarded = isUserOnboarded
//            )
//        }
//    }
}