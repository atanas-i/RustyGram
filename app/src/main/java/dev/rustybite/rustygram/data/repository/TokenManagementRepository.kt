package dev.rustybite.rustygram.data.repository

import com.google.gson.JsonObject
import dev.rustybite.rustygram.domain.models.VerifiedUser
import dev.rustybite.rustygram.util.RustyResult
import kotlinx.coroutines.flow.Flow

interface TokenManagementRepository {
    /**
     * This function is responsible with refreshing access token
     * @param[body] A json body that carries refresh token
     * @return[Flow<RustyResult<VerifiedUser>>] Indicating a success or failure of refreshing the token
     */
    suspend fun refreshToken(body: JsonObject): Flow<RustyResult<VerifiedUser>>
}