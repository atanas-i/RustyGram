package dev.rustybite.rustygram.presentation.user_management.login_screen

import dev.rustybite.rustygram.data.repository.LoginRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) {
}