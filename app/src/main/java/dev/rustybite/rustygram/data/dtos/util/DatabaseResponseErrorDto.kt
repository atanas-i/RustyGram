package dev.rustybite.rustygram.data.dtos.util


import com.google.gson.annotations.SerializedName

data class DatabaseResponseErrorDto(
    @SerializedName("code")
    val code: String,
    @SerializedName("details")
    val details: Any?,
    @SerializedName("hint")
    val hint: Any?,
    @SerializedName("message")
    val message: String
)