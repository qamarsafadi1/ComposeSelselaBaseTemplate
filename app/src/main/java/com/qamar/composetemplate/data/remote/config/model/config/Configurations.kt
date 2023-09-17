package com.qamar.composetemplate.data.remote.config.model.config


import androidx.annotation.Keep
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.gson.annotations.SerializedName
import com.qamar.composetemplate.BaseApp
import com.qamar.composetemplate.data.local.PreferenceHelper.appLocale
import com.qamar.composetemplate.data.local.PreferenceHelper.configurations
import com.qamar.composetemplate.data.local.PreferenceHelper.isFirst
import com.qamar.composetemplate.data.local.PreferenceHelper.payments
import com.qamar.composetemplate.data.remote.config.model.payment.Payment
import com.selsela.cpapp.BuildConfig

@Keep
data class Configurations(
    @SerializedName("address")
    val address: String = "",
    @SerializedName("address_en")
    val addressEn: String = "",
    @SerializedName("android")
    val android: String = "",
    @SerializedName("app_version_andriod")
    val androidVersion: String = "1",
    @SerializedName("app_status_android")
    val appStatusAndroid: String = "",
    @SerializedName("app_status_ios")
    val appStatusIos: String = "",
    @SerializedName("currency_ar")
    val currencyAr: String = "",
    @SerializedName("currency_en")
    val currencyEn: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("facebook")
    val facebook: String = "",
    @SerializedName("instagram")
    val instagram: String = "",
    @SerializedName("snapchat")
    val snapchat: String = "",
    @SerializedName("ios")
    val ios: String = "",
    @SerializedName("ios_version")
    val iosVersion: String = "",
    @SerializedName("linked_in")
    val linkedIn: String = "",
    @SerializedName("mobile")
    val mobile: String = "",
    @SerializedName("name_ar")
    val nameAr: String = "",
    @SerializedName("name_en")
    val nameEn: String = "",
    @SerializedName("twitter")
    val twitter: String = "",
    @SerializedName("update_andriod")
    val updateAndroid: String = "",
    @SerializedName("update_ios")
    val updateIos: String = "",
    @SerializedName("whatsapp")
    val whatsapp: String = "",
    @SerializedName("tax")
    val tax: String = "",
    @SerializedName("sizes_picture")
    val sizesPicture: String = "http://clothesstore.selselatech.com/uploads/1684244321_24122145.png",
    @SerializedName("tax_number")
    val tax_number: String = "",
    @SerializedName("internal_delivery_price")
    val internalDeliveryPrice: String = "0",
    @SerializedName("external_delivery_price")
    val externalDeliveryPrice: String = "0",
    @SerializedName("delivery_period")
    val delivery_period: String = "",
    var appLocaleRefreshed: Boolean? = false,
    var period_allowed_reshipment_days: String = "0",
    val shipment_cost: String = "0",
    @SerializedName("min_order_cost")
    val min_order_cost: String? = "",
) {
    val minOrderCost: Double
        get() = if (min_order_cost.isNullOrEmpty())
            0.0 else min_order_cost.toDouble()
    val taxPercent: Int
        get() = if (tax.isEmpty())
            0
        else tax.toInt()

    companion object {
        var appLocaleRefreshed: Boolean = false
        var appOrderLocaleRefreshed: Boolean = false
        var Config: Configurations?
            get() {
                return BaseApp.LocalData?.configurations
            }
            set(value) {
                BaseApp.LocalData?.configurations = value
            }


        val appLocal: MutableState<String>
            get() {
                return mutableStateOf(BaseApp.LocalData?.appLocale ?: "ar")
            }

        val Currency: String
            get() {
                return if (BaseApp.LocalData?.appLocale == "ar") BaseApp.LocalData?.configurations?.currencyAr
                    ?: "" else BaseApp.LocalData?.configurations?.currencyEn ?: ""
            }

        var isFirst: Boolean
            get() {
                return BaseApp.LocalData?.isFirst ?: true
            }
            set(value) {
                BaseApp.LocalData?.isFirst = value
            }

        var payments: List<Payment>
            get() {
                return BaseApp.LocalData?.payments ?: listOf()
            }
            set(value) {
                BaseApp.LocalData?.payments = value
            }

        val isAppDisabled: Boolean
            get() {
                return BaseApp.LocalData?.configurations?.appStatusAndroid == "0"
            }
        val isForceUpdateEnabled: Boolean
            get() {
                return BaseApp.LocalData?.configurations?.updateAndroid == "1" && (BaseApp.LocalData?.configurations?.androidVersion?.toDouble()!! > BuildConfig.VERSION_NAME.toDouble())
            }
    }

    val hasTax: Boolean
        get() = tax.isEmpty().not() && tax != "0"

}