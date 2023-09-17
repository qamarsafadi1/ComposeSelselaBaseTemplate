package com.qamar.composetemplate.data.remote.auth.model.user


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.qamar.composetemplate.data.remote.config.model.config.Configurations

@Keep
data class Address(
    @SerializedName("address_number")
    val addressNumber: String = "",
    @SerializedName("full_address")
    val addressText: String = "",
    @SerializedName("apartment_number")
    val apartmentNumber: String = "",
    @SerializedName("city")
    val city: City = City(),
    @SerializedName("country")
    val country: CountryX = CountryX(),
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("latitude")
    val latitude: Double = 0.0,
    @SerializedName("longitude")
    val longitude: Double = 0.0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("others")
    val others: String = "",
    @SerializedName("street")
    val street: String = ""
) {

    val deliveryPrice: Double
        get() =
            if (Configurations.Config?.internalDeliveryPrice.isNullOrEmpty().not() &&
                Configurations.Config?.externalDeliveryPrice.isNullOrEmpty().not()
            ) {
                if (city.id == Configurations.Config?.address?.toInt()) {
                    Configurations.Config?.internalDeliveryPrice?.toDouble() ?: 0.0
                } else Configurations.Config?.externalDeliveryPrice?.toDouble() ?: 0.0
            } else 0.0

}