package com.qamar.composetemplate.data.local

import android.content.Context
import android.content.SharedPreferences
import com.qamar.composetemplate.data.remote.auth.model.user.User
import com.qamar.composetemplate.data.remote.config.model.config.Configurations
import com.qamar.composetemplate.data.remote.config.model.payment.Payment
import com.qamar.composetemplate.util.fromJson
import com.qamar.composetemplate.util.toJson

object PreferenceHelper {

    private const val APP_LOCALE = "APP_LOCALE"
    private const val FCM_TOKEN = "fcm_token"
    private const val CONFIGURATIONS = "configurations"
    private const val PAYMENTS = "payments"
    private const val USER = "user"
    private const val TOKEN = "token"

    fun customPreference(context: Context, name: String): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    private inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.appLocale
        get() = getString(APP_LOCALE, "ar")
        set(value) {
            editMe {
                it.putString(APP_LOCALE, value)
            }
        }

    var SharedPreferences.isFirst
        get() = getBoolean("isFirst", true)
        set(value) {
            editMe {
                it.putBoolean("isFirst", value)
            }
        }

    var SharedPreferences.configurations
        get() = getString(CONFIGURATIONS, null)?.fromJson<Configurations>()
        set(value) {
            editMe {
                it.putString(CONFIGURATIONS, value?.toJson())
            }
        }

    var SharedPreferences.user
        get() = getString(USER, null)?.fromJson<User>()
        set(value) {
            editMe {
                it.putString(USER, value?.toJson())
            }
        }
    var SharedPreferences.accessToken
        get() = getString(TOKEN, null)
        set(value) {
            editMe {
                it.putString(TOKEN, value)
            }
        }

    var SharedPreferences.payments
        get() = getString(PAYMENTS, null)?.fromJson<List<Payment>>()
        set(value) {
            editMe {
                it.putString(PAYMENTS, value?.toJson())
            }
        }


    var SharedPreferences.fcmToken
        get() = getString(FCM_TOKEN, "")
        set(value) {
            editMe {
                it.putString(FCM_TOKEN, value)
            }
        }

}