package dev.rustybite.rustygram.data.repository

import dev.rustybite.rustygram.domain.models.RustyResponse
import dev.rustybite.rustygram.domain.models.User
import dev.rustybite.rustygram.util.RustyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.JsonObject
import retrofit2.Response

/**
 * Repository interface for handling user registration operations.
 */
interface UserRegistrationRepository {

    /**
     * Register a new user with the provided email and password.
     * @param [email]: The email address of the new user.
     * @param [password]: The password for the user
     * @return A [RustyResult] indicating success or failure of the registration operation.
     */
    suspend fun registerUser(email: String, password: String): Flow<RustyResult<User>>

    /**
     * Request OTP for user registration.
     * @param [email]: The email address of the user.
     * @return A [RustyResult] indicating success or failure of the OTP request operation.
     */
    suspend fun requestOtp(email: JsonObject): Flow<RustyResult<RustyResponse>>
}