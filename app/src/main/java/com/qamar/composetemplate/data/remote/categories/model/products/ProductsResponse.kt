package com.qamar.composetemplate.data.remote.categories.model.products


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.qamar.composetemplate.data.remote.categories.model.category.Category

@Keep
data class ProductsResponse(
    @SerializedName("has_more_page")
    val hasMorePage: Boolean = false,
    @SerializedName("products")
    val products: List<Product> = listOf(),
    @SerializedName("category")
    val category: Category? = null,
    @SerializedName("response_message")
    val responseMessage: String = "",
    @SerializedName("status")
    val status: Boolean = false
)