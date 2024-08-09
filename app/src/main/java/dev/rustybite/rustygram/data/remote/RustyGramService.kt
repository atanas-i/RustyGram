package dev.rustybite.rustygram.data.remote

import com.google.gson.JsonObject
import dev.rustybite.rustygram.data.dtos.auth.UserDto
import dev.rustybite.rustygram.data.dtos.auth.VerifiedUserDto
import dev.rustybite.rustygram.domain.models.RustyResponse
import dev.rustybite.rustygram.util.API_KEY
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface RustyGramService {

    @POST("/auth/v1/signup")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun registerUser(
        @Body body: JsonObject
    ): Response<VerifiedUserDto>

    @POST("/auth/v1/logout")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun logout(
        @Header("Authorization") token: String
    ): Response<RustyResponse>

    @POST("token?grant_type=password")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun login(
        @Body body: JsonObject
    ): Response<UserDto>

    @POST("/auth/v1/token?grant_type=refresh_token")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun refreshToken(
        @Body body: JsonObject
    ): Response<VerifiedUserDto>

}