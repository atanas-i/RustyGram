package dev.rustybite.rustygram.data.repository

import com.google.gson.JsonObject
import dev.rustybite.rustygram.domain.models.RustyResponse
import dev.rustybite.rustygram.domain.models.User
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
    suspend fun registerUser(body: JsonObject): Flow<RustyResult<User>>

    /**
     * Request OTP for user registration.
     * @param [body]: Json body that carries email address of the user.
     * @return A [Flow<RustyResult>] indicating success or failure of the OTP request operation.
     */
    suspend fun requestOtp(body: JsonObject): Flow<RustyResult<RustyResponse>>

    /**
     * Verify if the user email is correct by verifying the OTP code sent to the user's email.
     * @param [body] Json body that carries the type (Email/Phone), email address and OTP code sent to the user.
     * @return [Flow<RustyResult<User>>] indicating success or failure of the OTP verification operation.
     */

    suspend fun verifyOtp(body: JsonObject): Flow<RustyResult<RustyResponse>>
}