package com.qamar.composetemplate.data.remote.auth.model.user


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AuthResponse(
    @SerializedName("response_message")
    val responseMessage: String = "",
    @SerializedName("status")
    val status: Boolean = false,
    @SerializedName("user")
    val user: User = User(),
    @SerializedName("returned")
    val returned: Any? = null,
)