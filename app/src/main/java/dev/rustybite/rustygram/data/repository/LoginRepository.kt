package dev.rustybite.rustygram.data.repository

import com.google.gson.JsonObject
import dev.rustybite.rustygram.domain.models.RustyResponse
import dev.rustybite.rustygram.domain.models.User
import dev.rustybite.rustygram.util.RustyResult
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    /**
     * Login user with email and password.
     * @param [body] Json body that carries email and password of the user.
     * @return [Flow<RustyResult<User>>] Indicating success or failure of the login operation.
     */
    suspend fun login(body: JsonObject): Flow<RustyResult<User>>

    /**
     * Logout user.
     * @param [token] An authorized token of the logged in user.
     * @return [Flow<RustyResult<RustyResponse>>] Indicating success or failure of the logout operation.
     */
    suspend fun logout(token: String): Flow<RustyResult<RustyResponse>>
}