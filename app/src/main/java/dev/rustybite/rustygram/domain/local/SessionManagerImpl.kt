package dev.rustybite.rustygram.domain.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dev.rustybite.rustygram.data.local.SessionManager
import dev.rustybite.rustygram.util.ACCESS_TOKEN
import dev.rustybite.rustygram.util.EXPIRES_AT
import dev.rustybite.rustygram.util.IS_USER_ONBOARDED
import dev.rustybite.rustygram.util.IS_USER_SIGNED_IN
import dev.rustybite.rustygram.util.REFRESH_TOKEN
import dev.rustybite.rustygram.util.SESSION_MANAGER
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SESSION_MANAGER)
class SessionManagerImpl @Inject constructor(
    private val context: Context
) : SessionManager {
    private val accessTokenKey = stringPreferencesKey(ACCESS_TOKEN)
    private val refreshTokenKey = stringPreferencesKey(REFRESH_TOKEN)
    private val expiresAtKey = longPreferencesKey(EXPIRES_AT)
    private val isUserSignedInKey = booleanPreferencesKey(IS_USER_SIGNED_IN)
    private val isUserOnboardedKey = booleanPreferencesKey(IS_USER_ONBOARDED)

    override suspend fun saveAccessToken(accessToken: String) {
        context.dataStore.edit { preferences ->
            preferences[accessTokenKey] = accessToken
        }
    }

    override val accessToken: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[accessTokenKey]
        }

    override suspend fun saveRefreshToken(refreshToken: String) {
        context.dataStore.edit { preferences ->
            preferences[refreshTokenKey] = refreshToken
        }
    }

    override val refreshToken: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[refreshTokenKey]
        }

    override suspend fun saveExpiresAt(expiresAt: Long) {
        context.dataStore.edit { preferences ->
            preferences[expiresAtKey] = expiresAt
        }
    }

    override val expiresAt: Flow<Long?>
        get() = context.dataStore.data.map { preferences ->
            preferences[expiresAtKey]
        }

    override suspend fun saveIsUserSignedIn(isUsersSignedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[isUserSignedInKey] = isUsersSignedIn
        }
    }

    override val isUserSignedIn: Flow<Boolean?>
        get() = context.dataStore.data.map { preferences ->
            preferences[isUserSignedInKey]
        }

    override suspend fun saveIsUserOnboarded(isUserOnboarded: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[isUserOnboardedKey] = isUserOnboarded
        }
    }

    override val isUserOnboarded: Flow<Boolean?>
        get() = context.dataStore.data.map { preferences ->
            preferences[isUserOnboardedKey]
        }


    override fun isAccessTokenExpired(accessToken: String?, expiresAt: Long?): Boolean {
        if (accessToken.isNullOrEmpty()) {
            return true
        }
        val currentTime = System.currentTimeMillis()
        return currentTime < expiresAt!!
    }
}