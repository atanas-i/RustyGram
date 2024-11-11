package dev.rustybite.rustygram.domain.repository

import com.google.gson.JsonObject
import dev.rustybite.rustygram.R
import dev.rustybite.rustygram.data.dtos.auth.toVerifiedUser
import dev.rustybite.rustygram.data.dtos.util.ApiErrorDto
import dev.rustybite.rustygram.data.dtos.util.toApiError
import dev.rustybite.rustygram.data.remote.RustyGramService
import dev.rustybite.rustygram.data.repository.TokenManagementRepository
import dev.rustybite.rustygram.domain.models.RustyApiError
import dev.rustybite.rustygram.domain.models.VerifiedUser
import dev.rustybite.rustygram.util.ResourceProvider
import dev.rustybite.rustygram.util.RustyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit

class TokenManagementRepositoryImpl(
    private val service: RustyGramService,
    private val retrofit: Retrofit,
    private val resources: ResourceProvider
) : TokenManagementRepository {
    private val converter = retrofit.responseBodyConverter<ApiErrorDto>(
        ApiErrorDto::class.java,
        arrayOfNulls<Annotation>(0)
    )
    /**
     * This function is responsible with refreshing access token
     * @param[body] A json body that carries refresh token
     * @return[Flow<RustyResult<VerifiedUser>>] Indicating a success or failure of refreshing the token
     */
    override suspend fun refreshToken(body: JsonObject): Flow<RustyResult<VerifiedUser>> = flow {
        emit(RustyResult.Loading())
        val response = service.refreshToken(body)
        if (response.isSuccessful) {
            response.body()?.let { verifiedUserDto ->
                emit(RustyResult.Success(verifiedUserDto.toVerifiedUser()))
            }
        } else {
            val errorBody = response.errorBody()
            if (errorBody != null) {
                val rustyApiError = converter.convert(errorBody)?.toApiError()
                if (rustyApiError != null) {
                    emit(RustyResult.Failure(rustyApiError.message))
                } else {
                    emit(RustyResult.Failure(resources.getString(R.string.unknown_error)))
                }
            } else {
                emit(RustyResult.Failure(resources.getString(R.string.unknown_error)))

            }
        }
    }
}