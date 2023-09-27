package com.qamar.composetemplate.util

import androidx.annotation.Keep
import com.google.android.gms.maps.model.LatLng
import com.qamar.composetemplate.data.remote.config.model.config.Configurations
import com.qamar.composetemplate.data.remote.geo.GeocodingResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


@Keep
class GeocodingUtils(val currentLocation: LatLng) {
    companion object {
        private const val BASE_URL = "https://maps.googleapis.com/maps/api/geocode/"

        // Retrofit service interface
        private interface GeocodingService {
            @GET("json")
            suspend fun reverseGeocode(
                @Query("latlng") latLng: String,
                @Query("key") apiKey: String,
                @Query("language") language: String? = Configurations.appLocal.value
            ): GeocodingResponse

            @GET("json")
            suspend fun nationalNumberQuery(
                @Query("address") address: String,
                @Query("key") apiKey: String,
                @Query("language") language: String? = Configurations.appLocal.value
            ): GeocodingResponse
        }


        // Retrofit instance
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }


    // Geocoding service instance
    private val geocodingService: GeocodingService = retrofit.create(GeocodingService::class.java)
    lateinit var response: GeocodingResponse


    suspend fun reverseGeocode(): String? {
        val latLng = "${currentLocation.latitude},${currentLocation.longitude}"
        response =
            geocodingService.reverseGeocode(latLng, "AIzaSyDD7WH1Qz2mvUUDuDvSSY6uUxQdOV2J3do")
        if (response.status == "OK" && response.results.isNotEmpty()) {
            return response.results[0].formatted_address
        }
        return null
    }

    suspend fun nationalNumberQuery(nationalNumber: String): GeocodingResponse? {
        response = geocodingService.nationalNumberQuery(
            nationalNumber,
            "AIzaSyDD7WH1Qz2mvUUDuDvSSY6uUxQdOV2J3do"
        )
        return response
    }

    fun getFullAddress(): String = "${
        if (getCountryName() != null)
            getCountryName()
        else ""
    },${
        if (getCityName() != null)
            getCityName()
        else ""
    },${
        if (getNeighborhood() != null)
            getNeighborhood()
        else ""
    },${
        if (getStreet() != null)
            getStreet() else ""
    }"

    fun getCountryName(): String? {
        if (response.status == "OK" && response.results.isNotEmpty()) {
            val addressComponents = response.results[0].address_components
            for (component in addressComponents) {
                if (component.types.contains("country")) {
                    return component.long_name
                }
            }
        }
        return null
    }

    fun getLatLng(): LatLng? {
        if (response.status == "OK" && response.results.isNotEmpty()) {
            val addressComponents = response.results[0].geometry
            "${addressComponents}".log("addressComponents")
            return LatLng(addressComponents.location.lat, addressComponents.location.lng)
        }
        return null
    }

    fun getAreaName(): String? {
        if (response.status == "OK" && response.results.isNotEmpty()) {
            val addressComponents = response.results[0].address_components
            for (component in addressComponents) {
                if (component.types.contains("administrative_area_level_1")) {
                    return component.long_name
                }
            }
        }
        return null
    }

    fun getCityName(): String? {
        if (response.status == "OK" && response.results.isNotEmpty()) {
            val addressComponents = response.results[0].address_components
            for (component in addressComponents) {
                if (component.types.contains("locality")) {
                    return component.long_name
                }
            }
        }
        return null
    }

    fun getNeighborhood(): String? {
        if (response.status == "OK" && response.results.isNotEmpty()) {
            val addressComponents = response.results[0].address_components
            for (component in addressComponents) {
                if (component.types.contains("sublocality")) {
                    return component.long_name
                }
            }
        }
        return null
    }

    fun getStreet(): String? {
        if (response.status == "OK" && response.results.isNotEmpty()) {
            val addressComponents = response.results[0].address_components
            for (component in addressComponents) {
                if (component.types.contains("route")) {
                    return component.long_name
                }
            }
        }
        return null
    }
}
