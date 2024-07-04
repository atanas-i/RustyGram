package dev.rustybite.rustygram.domain.repository

import dev.rustybite.rustygram.data.dtos.auth.toUser
import dev.rustybite.rustygram.data.remote.RustyGramService
import dev.rustybite.rustygram.data.repository.UserRegistrationRepository
import dev.rustybite.rustygram.domain.models.User
import dev.rustybite.rustygram.util.RustyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Implementations of the UserRegistrationRepository interface for handling user registration operations.
 */
class UserRegistrationRepositoryImpl @Inject constructor(private val service: RustyGramService) : UserRegistrationRepository {

    /**
     * Register a new user with the provided email and password.
     * @param [email]: The email address of the new user.
     * @param [password]: The password for the user
     * @return A [RustyResult] indicating success or failure of the registration operation.
     */
    override suspend fun registerUser(email: String, password: String): Flow<RustyResult<User>> = flow {
        emit(RustyResult.Loading())
        runCatching {
            service.registerUser(email, password).toUser()
        }.onSuccess { user ->
            emit(RustyResult.Success(user))
        }.onFailure { exception ->
            emit(RustyResult.Failure(exception.localizedMessage ?: "Unknown error occurred"))
        }
    }
}