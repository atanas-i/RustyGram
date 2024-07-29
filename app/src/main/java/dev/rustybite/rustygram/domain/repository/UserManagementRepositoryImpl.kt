package dev.rustybite.rustygram.domain.repository

import android.util.Log
import com.google.gson.JsonObject
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.dtos.auth.toUser
import dev.rustybite.rustygram.data.dtos.auth.toVerifiedUser
import dev.rustybite.rustygram.data.dtos.util.ApiErrorDto
import dev.rustybite.rustygram.data.dtos.util.toApiError
import dev.rustybite.rustygram.data.remote.RustyGramService
import dev.rustybite.rustygram.data.repository.UserManagementRepository
import dev.rustybite.rustygram.domain.models.RustyResponse
import dev.rustybite.rustygram.domain.models.User
import dev.rustybite.rustygram.domain.models.VerifiedUser
import dev.rustybite.rustygram.util.ResourceProvider
import dev.rustybite.rustygram.util.RustyResult
import dev.rustybite.rustygram.util.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject

/**
 * Implementations of the UserRegistrationRepository interface for handling user registration operations.
 */
class UserManagementRepositoryImpl @Inject constructor(
    private val service: RustyGramService,
    private val retrofit: Retrofit,
    private val resources: ResourceProvider
) : UserManagementRepository {
    private val converter = retrofit.responseBodyConverter<ApiErrorDto>(
        ApiErrorDto::class.java,
        arrayOfNulls<Annotation>(0)
    )
    /**
     * Register a new user with the provided email and password.
     * @param [body]: Json body that carries email address and password of the new user.
     * @return A [Flow<RustyResult>] indicating success or failure of the registration operation.
     */
    override suspend fun registerUser(body: JsonObject): Flow<RustyResult<User>> = flow {
        emit(RustyResult.Loading())
        val response = service.registerUser(body)
        if (response.isSuccessful) {
            response.body()?.let { userDto ->
                emit(RustyResult.Success(userDto.toUser()))
            }
        } else {
            val errorBody = response.errorBody()
            if (errorBody != null) {
                val error = converter.convert(errorBody)?.toApiError()
                if (error != null) {
                    emit(RustyResult.Failure(error.message))
                } else {
                    emit(RustyResult.Failure("An unknown error occurred"))
                }
            } else {
                emit(RustyResult.Failure("An unknown error occurred"))
            }
        }
    }

    /**
     * Implementation of [UserManagementRepository.requestOtp] for user registration.
     * @param [body]: Json body that carries email address of the user.
     * @return A [RustyResult] indicating success or failure of the OTP request.
     */
    override suspend fun requestOtp(body: JsonObject): Flow<RustyResult<RustyResponse>> = flow {
        emit(RustyResult.Loading())
        val response = service.requestOtp(body)
        if (response.isSuccessful) {
            response.body()?.let { data ->
                emit(RustyResult.Success(data))
            }

        } else {
            val errorBody = response.errorBody()
            if (errorBody != null) {
                val error = converter.convert(errorBody)?.toApiError()
                if (error != null) {
                    emit(RustyResult.Failure(error.message))
                } else {
                    emit(RustyResult.Failure("Error is null"))
                }
            } else {
                emit(RustyResult.Failure("Unknown error occurred"))
            }
        }
    }

    /**
     * Verify if the user email is correct by verifying the OTP code sent to the user's email.
     * @param [body] Json body that carries the type (Email/Phone), email address and OTP code sent to the user.
     * @return [Flow<RustyResult<User>>] indicating success or failure of the OTP verification operation.
     */
    override suspend fun verifyOtp(body: JsonObject): Flow<RustyResult<VerifiedUser>> = flow {

        try {
            emit(RustyResult.Loading())
            val response = service.verifyOtp(body)
            if (response.isSuccessful) {
                response.body()?.let { verifiedUserDto ->
                    emit(RustyResult.Success(verifiedUserDto.toVerifiedUser()))
                }
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    val error = converter.convert(errorBody)?.toApiError()
                    if (error != null) {
                        emit(RustyResult.Failure(error.message))
                    } else {
                        emit(RustyResult.Failure(resources.getString(R.string.unknown_error)))
                    }
                }
            }
        } catch (e: IOException) {
            emit(RustyResult.Failure(e.localizedMessage))
        } catch (e: Exception) {
            emit(RustyResult.Failure(e.localizedMessage))
        }
        //Log.d(TAG, "REPO verifyOtp: Debug response ${data.body()}")
    }
}