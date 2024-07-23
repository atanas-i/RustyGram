package dev.rustybite.rustygram.data.dtos.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppMetadataX(
    @SerialName("provider")
    val provider: String,
    @SerialName("providers")
    val providers: List<String>
)