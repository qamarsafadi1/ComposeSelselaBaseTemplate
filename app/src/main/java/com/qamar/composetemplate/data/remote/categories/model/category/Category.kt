package com.qamar.composetemplate.data.remote.categories.model.category


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.qamar.composetemplate.data.remote.config.model.config.Configurations

@Keep
data class Category(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image_url")
    val imageUrl: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("products_count")
    val productsCount: Int = 0,
    @SerializedName("childs")
    val childs: List<Children> = listOf(),
    @SerializedName("brands")
    val brands: List<Children> = listOf(),
) {

    val colors = listOf(
        "#E4E1B8",
        "#F0D7D0",
        "#DEDBEC",
        "#9DCDD5",
        "#E6D1D6",
        "#C1BDFF",
        "#FAC0C0",
        "#E9E9E9"
    )
    val bgColor: String
        get() = colors.random()
}

@Keep
data class Children(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("childs")
    val childs: List<Children> = listOf(),
    @SerializedName("brands")
    val brands: List<Children> = listOf(),
    @SerializedName("image_url")
    val imageUrl: String = "",
) {

    val subCategories: List<Children>
        get() {
            val subCategories = childs.toMutableList()
            subCategories.add(
                0, Children(
                    id = -1, name = if (Configurations.appLocal.value == "ar") "الكل" else "All",
                    imageUrl = imageUrl
                )
            )
            return subCategories
        }
}
