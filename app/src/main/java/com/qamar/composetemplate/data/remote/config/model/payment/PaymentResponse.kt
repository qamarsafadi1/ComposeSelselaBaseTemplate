package com.qamar.composetemplate.data.remote.config.model.payment


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PaymentResponse(
    @SerializedName("payments")
    val payments: List<Payment> = listOf(),
    @SerializedName("response_message")
    val responseMessage: String = "",
    @SerializedName("status")
    val status: Boolean = false
)