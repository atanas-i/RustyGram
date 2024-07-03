package dev.rustybite.rustygram.data.remote

import dev.rustybite.rustygram.data.dtos.auth.UserDto
import dev.rustybite.rustygram.util.RustyResult

interface RustyGramService {
    suspend fun registerUser(email: String, password: String): UserDto
}