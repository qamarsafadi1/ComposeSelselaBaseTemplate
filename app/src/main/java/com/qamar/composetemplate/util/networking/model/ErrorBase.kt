package com.qamar.composetemplate.util.networking.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ErrorBase(
    val `data`: Any? = Any(),
    val errors: List<Errors>? = listOf(),
    @SerializedName("response_message")
    val responseMessage: String? = "something went wrong",
    val status: Boolean,
    )