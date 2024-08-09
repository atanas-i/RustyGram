package dev.rustybite.rustygram.domain.repository

import com.google.gson.JsonObject
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.dtos.auth.toUser
import dev.rustybite.rustygram.data.dtos.auth.toVerifiedUser
import dev.rustybite.rustygram.data.dtos.util.ApiErrorDto
import dev.rustybite.rustygram.data.dtos.util.toApiError
import dev.rustybite.rustygram.data.remote.RustyGramService
import dev.rustybite.rustygram.data.repository.LoginRepository
import dev.rustybite.rustygram.data.repository.UserRegistrationRepository
import dev.rustybite.rustygram.domain.models.RustyResponse
import dev.rustybite.rustygram.domain.models.User
import dev.rustybite.rustygram.domain.models.VerifiedUser
import dev.rustybite.rustygram.util.ResourceProvider
import dev.rustybite.rustygram.util.RustyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val service: RustyGramService,
    private val retrofit: Retrofit,
    private val resources: ResourceProvider
) : LoginRepository {

    private val converter = retrofit.responseBodyConverter<ApiErrorDto>(
        ApiErrorDto::class.java,
        arrayOfNulls<Annotation>(0)
    )

    /**
     * Implementation of [UserRegistrationRepository.login].
     * @param [body] Json body that carries email and password of the user.
     * @return [Flow<RustyResult<User>>] Indicating success or failure of the login operation.
     */
    override suspend fun login(body: JsonObject): Flow<RustyResult<VerifiedUser>> = flow {
        emit(RustyResult.Loading())
        val response = service.registerUser(body)
        if (response.isSuccessful) {
            response.body()?.let { userDto ->
                emit(RustyResult.Success(userDto.toVerifiedUser()))
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
            } else {
                emit(RustyResult.Failure(resources.getString(R.string.unknown_error)))
            }
        }
    }

    /**
     * Implementation of [UserRegistrationRepository.logout].
     * @return [Flow<RustyResult<RustyResponse>>]
     */
    override suspend fun logout(token: String): Flow<RustyResult<RustyResponse>>  = flow {
        emit(RustyResult.Loading())
        val response = service.logout(token)
        if (response.isSuccessful) {
            response.body()?.let {
                val data = RustyResponse(
                    success = true,
                    message = resources.getString(R.string.logout_success)
                )
                emit(RustyResult.Success(data))
            }
        } else {
            val errorBody = response.errorBody()
            if (errorBody != null) {
                val error = converter.convert(errorBody)?.toApiError()
                if (error != null) {
                    emit(RustyResult.Failure(error.message))
                }
            } else {
                emit(RustyResult.Failure(resources.getString(R.string.unknown_error)))
            }
        }
    }
}