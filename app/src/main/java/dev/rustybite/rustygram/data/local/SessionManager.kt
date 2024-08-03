package dev.rustybite.rustygram.data.local

import kotlinx.coroutines.flow.Flow

interface SessionManager {
    suspend fun saveAccessToken(accessToken: String)
    val accessToken: Flow<String?>
    suspend fun saveRefreshToken(refreshToken: String)
    val refreshToken: Flow<String?>
    suspend fun saveExpiresAt(expiresAt: Long)
    val expiresAt: Flow<Long?>
    suspend fun saveIsUserSignedIn(isUsersSignedIn: Boolean)
    val isUserSignedIn: Flow<Boolean?>
    suspend fun saveIsUserOnboarded(isUserOnboarded: Boolean)
    val isUserOnboarded: Flow<Boolean?>
    fun isAccessTokenExpired(accessToken: String?, expiresAt: Long?): Boolean
}