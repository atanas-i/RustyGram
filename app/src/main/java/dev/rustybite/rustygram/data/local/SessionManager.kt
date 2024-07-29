package dev.rustybite.rustygram.data.local

import kotlinx.coroutines.flow.Flow

interface SessionManager {
    suspend fun saveAccessToken(accessToken: String)
    val accessToken: Flow<String?>
    suspend fun saveRefreshToken(refreshToken: String)
    val refreshToken: Flow<String?>
    suspend fun saveExpiresAt(expiresAt: Long)
    val expiresAt: Flow<Long?>
    fun isAccessTokenExpired(accessToken: String, expiresAt: Long): Boolean
}