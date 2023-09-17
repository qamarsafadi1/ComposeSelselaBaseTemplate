package com.qamar.composetemplate.data.remote.auth.model.user


import androidx.annotation.Keep
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.gson.annotations.SerializedName
import com.qamar.composetemplate.BaseApp.Companion.LocalData
import com.qamar.composetemplate.data.local.PreferenceHelper.accessToken
import com.qamar.composetemplate.data.local.PreferenceHelper.user
import com.qamar.composetemplate.util.Constants.NOT_VERIFIED

@Keep
data class User(
    @SerializedName("accessToken")
    val accessToken: String = "",
    @SerializedName("activation_code")
    val activationCode: String = "",
    @SerializedName("avatar")
    var avatar: String = "",
    @SerializedName("country")
    val country: Country = Country(),
    @SerializedName("country_id")
    val countryId: Int = 0,
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("enable_notification")
    val enableNotification: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("language")
    val language: String = "",
    @SerializedName("mobile")
    val mobile: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("schedule_notification_before")
    val scheduleNotificationBefore: Int = 0,
    @SerializedName("addresses")
    val addresses: MutableList<Address> = mutableListOf(),
    @SerializedName("status")
    val status: String = "disabled"
) {
    companion object {

        fun clearData() {
            currentUser = null
            accessToken = null
            isLoggedIn.value = false
        }

        var currentUser: User?
            get() {
                return LocalData?.user
            }
            set(value) {
                LocalData?.user = value
            }
        var accessToken: String?
            get() {
                return LocalData?.accessToken
            }
            set(value) {
                LocalData?.accessToken = value
            }

        val isLoggedIn: MutableState<Boolean>
            get() {
                return mutableStateOf(
                    LocalData?.accessToken.isNullOrEmpty()
                        .not() && LocalData?.user?.status != NOT_VERIFIED
                )
            }


    }
}