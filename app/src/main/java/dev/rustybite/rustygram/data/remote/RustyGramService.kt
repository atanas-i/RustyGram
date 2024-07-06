package dev.rustybite.rustygram.data.remote

import dev.rustybite.rustygram.data.dtos.auth.UserDto
import dev.rustybite.rustygram.domain.models.RustyResponse
import dev.rustybite.rustygram.util.API_KEY
import kotlinx.serialization.json.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers

interface RustyGramService {
    suspend fun registerUser(email: String, password: String): UserDto

    @GET("/auth/v1/otp")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun requestOtp(
        @Body body: JsonObject
    ): RustyResponse
}