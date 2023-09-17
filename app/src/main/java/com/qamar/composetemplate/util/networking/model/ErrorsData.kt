package com.qamar.composetemplate.util.networking.model

import androidx.annotation.Keep
import com.qamar.composetemplate.util.networking.model.Errors

@Keep
data class ErrorsData(
    val errors: List<Errors>?= listOf(),
    val responseMessage: String? =null,
    val status: Int? = 0
)