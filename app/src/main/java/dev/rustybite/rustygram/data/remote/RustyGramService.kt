package dev.rustybite.rustygram.data.remote

import com.google.gson.JsonObject
import dev.rustybite.rustygram.data.dtos.auth.UserDto
import dev.rustybite.rustygram.domain.models.RustyResponse
import dev.rustybite.rustygram.util.API_KEY
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RustyGramService {
    suspend fun registerUser(email: String, password: String): UserDto

    @POST("/auth/v1/otp")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun requestOtp(
        @Body body: JsonObject
    ): Response<RustyResponse>

    @POST("/auth/v1/verify")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun verifyOtp(
        @Body body: JsonObject
    ): RustyResponse

}