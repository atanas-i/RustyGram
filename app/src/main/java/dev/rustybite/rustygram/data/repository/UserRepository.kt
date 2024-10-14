package dev.rustybite.rustygram.data.repository

import dev.rustybite.rustygram.domain.models.User
import dev.rustybite.rustygram.util.RustyResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getLoggedInUser(token: String): Flow<RustyResult<User>>
}