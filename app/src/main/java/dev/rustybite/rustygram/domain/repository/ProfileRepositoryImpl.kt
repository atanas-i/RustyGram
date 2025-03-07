package dev.rustybite.rustygram.domain.repository

import android.util.Log
import com.google.gson.JsonObject
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.dtos.util.ApiErrorDto
import dev.rustybite.rustygram.data.dtos.util.DatabaseResponseErrorDto
import dev.rustybite.rustygram.data.dtos.util.toApiError
import dev.rustybite.rustygram.data.remote.RustyGramService
import dev.rustybite.rustygram.data.repository.ProfileRepository
import dev.rustybite.rustygram.domain.models.Profile
import dev.rustybite.rustygram.domain.models.RustyApiError
import dev.rustybite.rustygram.domain.models.RustyResponse
import dev.rustybite.rustygram.domain.models.toDatabaseResponseError
import dev.rustybite.rustygram.domain.models.toProfile
import dev.rustybite.rustygram.util.ResourceProvider
import dev.rustybite.rustygram.util.RustyResult
import dev.rustybite.rustygram.util.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import javax.inject.Inject
import kotlin.math.log

class ProfileRepositoryImpl @Inject constructor(
    private val service: RustyGramService,
    private val retrofit: Retrofit,
    private val resProvider: ResourceProvider
) : ProfileRepository {
    private val converter = retrofit.responseBodyConverter<DatabaseResponseErrorDto>(DatabaseResponseErrorDto::class.java, arrayOfNulls<Annotation>(0))
    override suspend fun createProfile(
        token: String,
        body: JsonObject
    ): Flow<RustyResult<RustyResponse>> = flow {
        emit(RustyResult.Loading())
        val response = service.createProfile(token, body)
        if (response.isSuccessful) {
            response.body()?.let {
                val data = RustyResponse(
                    success = true,
                    message = resProvider.getString(R.string.profile_created_successfully)
                )
                emit(RustyResult.Success(data))
            }
        } else {
            val errorBody = response.errorBody()
            if (errorBody != null) {
                val error = converter.convert(errorBody)?.toDatabaseResponseError()
                if (error != null) {
                    emit(RustyResult.Failure(error.message))
                } else {
                    emit(RustyResult.Failure(resProvider.getString(R.string.unknown_error)))
                }
            } else {
                emit(RustyResult.Failure(resProvider.getString(R.string.unknown_error)))
            }
        }
    }

    override suspend fun getProfiles(token: String): Flow<RustyResult<List<Profile>>> = flow {
        emit(RustyResult.Loading())
        val response = service.getProfiles(token)
        if (response.isSuccessful) {
            response.body()?.let { profileDtos ->
                val data = profileDtos.map { profileDto -> profileDto.toProfile() }
                emit(RustyResult.Success(data))
            }
        } else {
            val errorBody = response.errorBody()
            if (errorBody != null) {
                val error = converter.convert(errorBody)?.toDatabaseResponseError()
                if (error != null) {
                    emit(RustyResult.Failure(error.message))
                } else {
                    emit(RustyResult.Failure(resProvider.getString(R.string.unknown_error)))
                }
            } else {
                emit(RustyResult.Failure(resProvider.getString(R.string.unknown_error)))
            }
        }
    }

    override suspend fun getProfile(token: String, userId: String): Flow<RustyResult<List<Profile>>> = flow {
        emit(RustyResult.Loading())
        val response = service.getProfile(token, userId)
        if (response.isSuccessful) {
            response.body()?.let { profileDto ->
                val data = profileDto.map { it.toProfile() }
                emit(RustyResult.Success(data))
                Log.d(TAG, "getProfile: ${data[0].fullName}")
            }
        } else {
            val errorBody = response.errorBody()
            Log.d(TAG, "getProfile: Raw error data is ${errorBody?.string()}")
            if (errorBody != null) {
                val error = converter.convert(errorBody)?.toDatabaseResponseError()
                if (error != null) {
                    emit(RustyResult.Failure(error.message))
                    Log.d(TAG, "getProfile: Error occurred is ${error.message}")
                } else {
                    emit(RustyResult.Failure(resProvider.getString(R.string.unknown_error)))
                }
            } else {
                emit(RustyResult.Failure(resProvider.getString(R.string.unknown_error)))
            }
        }
    }
}