package com.qamar.composetemplate.data.remote.categories.model.products


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Image(
    @SerializedName("image")
    val image: String = "",
    @SerializedName("image_url")
    val imageUrl: String = "",
    @SerializedName("product_id")
    val productId: Int = 0,
    @SerializedName("color_id")
    val colorId: Int = 0
)