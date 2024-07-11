package dev.rustybite.rustygram.domain.repository

import android.util.Log
import com.google.gson.JsonObject
import dev.rustybite.rustygram.data.dtos.auth.toUser
import dev.rustybite.rustygram.data.remote.RustyGramService
import dev.rustybite.rustygram.data.repository.UserRegistrationRepository
import dev.rustybite.rustygram.domain.models.RustyApiError
import dev.rustybite.rustygram.domain.models.RustyResponse
import dev.rustybite.rustygram.domain.models.User
import dev.rustybite.rustygram.util.RustyResult
import dev.rustybite.rustygram.util.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Implementations of the UserRegistrationRepository interface for handling user registration operations.
 */
class UserRegistrationRepositoryImpl @Inject constructor(
    private val service: RustyGramService,
    private val retrofit: Retrofit
) : UserRegistrationRepository {
    private val converter = retrofit.responseBodyConverter<RustyApiError>(
        RustyApiError::class.java,
        arrayOfNulls<Annotation>(0)
    )
    /**
     * Register a new user with the provided email and password.
     * @param [body]: A json body from the user.
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
                val error = converter.convert(errorBody)
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
     * Implementation of [UserRegistrationRepository.requestOtp] for user registration.
     * @param [email]: The email address of the user.
     * @return A [RustyResult] indicating success or failure of the OTP request.
     */
    override suspend fun requestOtp(email: JsonObject): Flow<RustyResult<RustyResponse>> = flow {
        emit(RustyResult.Loading())
        val response = service.requestOtp(email)
        if (response.isSuccessful) {
            response.body()?.let { data ->
                emit(RustyResult.Success(data))
            }

        } else {
            val errorBody = response.errorBody()
            Log.d(TAG, "requestOtp: Error code is ${errorBody?.bytes()?.decodeToString()}")
            if (errorBody != null) {
                val error = converter.convert(errorBody)
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
}