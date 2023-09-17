package com.qamar.composetemplate

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.qamar.composetemplate.data.remote.config.model.config.Configurations
import com.qamar.composetemplate.navigation.NavigationHost
import com.qamar.composetemplate.ui.theme.ComposeTemplateTheme
import com.qamar.composetemplate.util.applyAppLanguage
import com.qamar.composetemplate.util.requestNotificationPermission
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        applyAppLanguage()
        requestNotificationPermission()
        setContent {
            ComposeTemplateTheme {
                App()
            }
        }
    }

    @OptIn(ExperimentalMaterialNavigationApi::class)
    @Composable
    private fun App(
    ) {
        CompositionLocalProvider(
            LocalLayoutDirection provides
                    if (Configurations.appLocal.value == "ar") LayoutDirection.Rtl
                    else LayoutDirection.Ltr
        ) {
            NavigationHost(
              this,
            )
        }
    }
}