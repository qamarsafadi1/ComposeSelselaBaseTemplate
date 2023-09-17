package com.qamar.composetemplate.util

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.qamar.composetemplate.BaseApp
import com.qamar.composetemplate.data.local.PreferenceHelper.appLocale
import com.qamar.composetemplate.data.remote.config.model.config.Configurations
import java.util.Locale

object LocalUtils {
    fun Context.setLocale(lang: String) {
       BaseApp.LocalData?.appLocale = lang
        Configurations.appLocaleRefreshed = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            this.getSystemService(LocaleManager::class.java)
                .applicationLocales = LocaleList(Locale.forLanguageTag(lang))
        } else {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(lang)
            )
        }
    }
}