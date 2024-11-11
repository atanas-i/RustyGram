package dev.rustybite.rustygram.data.dtos.util


import com.google.gson.annotations.SerializedName

data class LoginErrorDto(
    @SerializedName("error")
    val error: String,
    @SerializedName("error_description")
    val errorDescription: String
)