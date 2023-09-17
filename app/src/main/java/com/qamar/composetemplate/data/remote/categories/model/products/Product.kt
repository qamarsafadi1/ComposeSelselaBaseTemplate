package com.qamar.composetemplate.data.remote.categories.model.products


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.qamar.composetemplate.data.remote.categories.model.category.Category

@Keep
data class Product(
    @SerializedName("category")
    var category: Category = Category(),
    @SerializedName("brands")
    var brand: Category? = Category(),
    @SerializedName("discount_ratio")
    var discountRatio: Int = 0,
    @SerializedName("discount_type")
    var discountType: String? = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("rates_count")
    val ratesCount: Int = 0,
    @SerializedName("image_url")
    var imageUrl: String = "",
    @SerializedName("image")
    var image: String = "",
    @SerializedName("in_favorite")
    val inFavorite: Int = 0,
    @SerializedName("in_featured")
    val inFeatured: Int = 0,
    @SerializedName("name")
    var name: String = "",
    var sku: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("feature")
    val featured: String = "",
    @SerializedName("order_count")
    val orderCount: Int = 0,
    @SerializedName("price")
    var price: Double = 0.0,
    @SerializedName("quantity")
    var quantity: Int = 0,
    @SerializedName("available_quantity")
    val availableQuantity: Int = 0,
    @SerializedName("rate")
    val rate: Float = 0f,
    @SerializedName("sell_price")
    var sellPrice: Double = 0.0,
    @SerializedName("properities")
    val properties: List<Properties>? = listOf(),
    @SerializedName("images")
    val images: List<Image> = listOf(),
    @SerializedName("rates")
    val rates: List<Rate> = listOf(),
    @SerializedName("has_variant")
    var hasVariant: Int = 0,

    ) {

    val productImages: List<Image>
        get() = images.ifEmpty { listOf(Image(imageUrl = image)) }
    val hasDiscount: Boolean
        get() = discountRatio > 0

    val isDiscountPercentage: Boolean
        get() = discountType != "value"

    val isEmpty: Boolean
        get() = quantity == 0

    val hasVariants: Boolean
        get() = hasVariant == 1
}

@Keep
data class Rate(
    @SerializedName("user_name")
    var userName: String = "",
    @SerializedName("user_avatar")
    var avatar: String = "",
    @SerializedName("comment")
    var comment: String? = null,
    @SerializedName("date_rate")
    var date: String = "",
    @SerializedName("rate")
    var rate: Float = 0f,
)

@Keep
data class Properties(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("value")
    var property_value: String = "",
    @SerializedName("property")
    var property: Property? = Property(),
    @SerializedName("rate")
    var rate: Float = 0f,
)

@Keep
data class Property(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = "",
)