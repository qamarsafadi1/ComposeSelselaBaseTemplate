package com.qamar.composetemplate

import android.app.Application
import android.content.SharedPreferences
import com.qamar.composetemplate.data.local.PreferenceHelper
import com.qamar.composetemplate.data.local.PreferenceHelper.appLocale
import com.qamar.composetemplate.util.LocalUtils.setLocale
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApp : Application() {
    companion object {
        var LocalData: SharedPreferences? = null
    }
    override fun onCreate() {
        LocalData = PreferenceHelper.customPreference(applicationContext, "APP_PREFERENCE")
        applicationContext.setLocale(LocalData!!.appLocale ?: "ar")
        super.onCreate()
    }
}