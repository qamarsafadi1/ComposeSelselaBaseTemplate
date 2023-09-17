package com.qamar.composetemplate.data.remote.categories.model.category


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CategoryResponse(
    @SerializedName("categories")
    val categories: List<Category> = listOf(),
    @SerializedName("status")
    val status: Boolean = false,
    @SerializedName("response_message")
    val responseMessage: String = "",
)