package dev.rustybite.rustygram.domain.repository

import android.util.Log
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.dtos.auth.toUser
import dev.rustybite.rustygram.data.dtos.util.UserErrorDto
import dev.rustybite.rustygram.data.remote.RustyGramService
import dev.rustybite.rustygram.data.repository.UserRepository
import dev.rustybite.rustygram.domain.models.User
import dev.rustybite.rustygram.domain.models.toUserError
import dev.rustybite.rustygram.util.ResourceProvider
import dev.rustybite.rustygram.util.RustyResult
import dev.rustybite.rustygram.util.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import java.io.EOFException
import java.io.IOException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val service: RustyGramService,
    private val resources: ResourceProvider,
    private val retrofit: Retrofit
) : UserRepository {
    private val converter = retrofit.responseBodyConverter<UserErrorDto>(
        UserErrorDto::class.java,
        arrayOfNulls<Annotation>(0)
    )
    /**
     * Implementation of [UserRepository.getLoggedInUser].
     * @param [token] An authorized token of the logged in user.
     * @return [Flow<RustyResult<User>>] Indicating success or failure of the login operation.
     */
    override suspend fun getLoggedInUser(token: String): Flow<RustyResult<User>> = flow {
        try {
            emit(RustyResult.Loading())
            val response = service.getLoggedInUser(token)
            if (response.isSuccessful) {
                response.body()?.let { userDto ->
                    emit(RustyResult.Success(userDto.toUser()))
                }
            } else {
                val errorBody = response.errorBody()
                Log.d(TAG, "getLoggedInUser: ${errorBody?.string()}")
                if (errorBody != null) {
                    val error = converter.convert(errorBody)?.toUserError()
                    if (error != null) {
                        emit(RustyResult.Failure(error.msg))
                    }
                } else {
                    emit(RustyResult.Failure(resources.getString(R.string.unknown_error)))
                }
            }
        } catch (exception: Exception) {
            when(exception) {
                is EOFException -> {
                    emit(RustyResult.Failure(exception.localizedMessage))
                }
                is IOException -> {
                    emit(RustyResult.Failure(exception.localizedMessage))
                }
                else -> {
                    emit(RustyResult.Failure(exception.localizedMessage))
                }
            }
        }
    }
}