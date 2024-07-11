package dev.rustybite.rustygram.domain.models

import dev.rustybite.rustygram.data.dtos.auth.AppMetadata
import dev.rustybite.rustygram.data.dtos.auth.Identity
import dev.rustybite.rustygram.data.dtos.auth.UserMetadata
import kotlinx.serialization.SerialName

data class User(
    val userId: String,
    val confirmationSentAt: String,
    val email: String,
    val isAnonymous: Boolean,
    val phone: String,
    val role: String,
    val createdAt: String,
    val updatedAt: String
)
