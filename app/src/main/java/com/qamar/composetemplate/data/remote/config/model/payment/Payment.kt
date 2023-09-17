package com.qamar.composetemplate.data.remote.config.model.payment


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.qamar.composetemplate.data.remote.config.model.config.Configurations
import kotlinx.collections.immutable.toImmutableList

@Keep
data class Payment(
    @SerializedName("image_url")
    val iconUrl: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("status")
    val status: String = "",
    @SerializedName("commission")
    val commission: Double = 0.0,

    ) {
    val isWallet: Boolean
        get() = id == 5

    val isEnabled: Boolean
        get() = status == "enabled"

    companion object {
        fun getPaymentCommission(selectedPaymentID: Int): Double {
            val payments = Configurations.payments.filter {
                it.id != 4 && it.id != 3
            }.toImmutableList()
            return payments.find { it.id == selectedPaymentID }?.commission ?: 0.0

        }
    }
}