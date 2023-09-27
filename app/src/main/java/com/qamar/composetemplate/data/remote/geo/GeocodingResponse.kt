package com.qamar.composetemplate.data.remote.geo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GeocodingResponse(
    @SerializedName("results")
    val results: List<Result>,
    val status: String
)
@Keep
data class Result(
    @SerializedName("formatted_address")
    val formatted_address: String,
    val address_components: List<AddressComponent>,
    val geometry: Geometry
)
@Keep
data class AddressComponent(
    val long_name: String,
    @SerializedName("short_name")
    val short_name: String,
    val types: List<String>
)
@Keep
data class Geometry(
    @SerializedName("location")
    val location: Location
)

@Keep
data class Location(
    @SerializedName("lat")
    val lat: Double,
    val lng: Double
)
