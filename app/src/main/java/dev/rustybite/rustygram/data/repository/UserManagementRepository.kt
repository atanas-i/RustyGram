package dev.rustybite.rustygram.data.repository

import com.google.gson.JsonObject
import dev.rustybite.rustygram.data.dtos.auth.VerifiedUserDto
import dev.rustybite.rustygram.domain.models.RustyResponse
import dev.rustybite.rustygram.domain.models.User
import dev.rustybite.rustygram.domain.models.VerifiedUser
import dev.rustybite.rustygram.util.RustyResult
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for handling user registration operations.
 */
interface UserManagementRepository {

    /**
     * Register a new user with the provided email and password.
     * @param [body]: Json body that carries email address and password of the new user.
     * @return A [Flow<RustyResult>] indicating success or failure of the registration operation.
     */
    suspend fun registerUser(body: JsonObject): Flow<RustyResult<VerifiedUser>>
}