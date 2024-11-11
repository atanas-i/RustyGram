package dev.rustybite.rustygram.data.dtos.util


import com.google.gson.annotations.SerializedName

data class AuthTokenErrorDto(
    @SerializedName("code")
    val code: Int,
    @SerializedName("error_code")
    val errorCode: String,
    @SerializedName("msg")
    val msg: String
)