package com.qamar.composetemplate.data.remote.config.model.config


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class ConfigResponse(
    @SerializedName("configurations")
    val configurations: Configurations = Configurations(),
    @SerializedName("response_message")
    val responseMessage: String = "",
    @SerializedName("status")
    val status: Boolean = false,
    )
