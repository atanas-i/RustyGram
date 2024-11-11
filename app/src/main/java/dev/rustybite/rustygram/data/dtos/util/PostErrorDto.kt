package dev.rustybite.rustygram.data.dtos.util


import com.google.gson.annotations.SerializedName

data class PostErrorDto(
    @SerializedName("code")
    val code: String,
    @SerializedName("details")
    val details: String,
    @SerializedName("hint")
    val hint: Any?,
    @SerializedName("message")
    val message: String
)