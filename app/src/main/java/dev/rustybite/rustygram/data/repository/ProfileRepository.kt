package dev.rustybite.rustygram.data.repository

import com.google.gson.JsonObject
import dev.rustybite.rustygram.domain.models.Profile
import dev.rustybite.rustygram.domain.models.RustyResponse
import dev.rustybite.rustygram.util.RustyResult
import kotlinx.coroutines.flow.Flow

/**
 * A repository responsible for profile management.
 */
interface ProfileRepository {
    /**
     * A function responsible for creating a profile.
     * @param [token] An authorized token of the logged in user.
     * @param [body] Json body that carries user profile details.
     * @return [Flow<RustyResult<RustyResponse>>] Indicating success or failure of the profile creation operation.
     */
    suspend fun createProfile(token: String, body: JsonObject): Flow<RustyResult<RustyResponse>>

    suspend fun getProfiles(token: String): Flow<RustyResult<List<Profile>>>

    suspend fun getProfile(token: String, userId: String): Flow<RustyResult<List<Profile>>>
}