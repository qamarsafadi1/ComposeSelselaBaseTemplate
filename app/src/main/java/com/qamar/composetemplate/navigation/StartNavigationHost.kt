package com.qamar.composetemplate.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.qamar.composetemplate.data.remote.auth.model.user.User
import com.qamar.composetemplate.data.remote.config.model.config.Configurations
import com.qamar.composetemplate.ui.screens.splash.SplashScreen
import com.qamar.composetemplate.util.disableApp
import com.qamar.composetemplate.util.forceUpdateApp

val SPLASH_SCREEN = AppDestinations.Splash.name

fun NavGraphBuilder.startNavigationHost(navController: NavHostController) {
    composable(
        route = SPLASH_SCREEN,
    ) {
        val context = LocalContext.current
        SplashScreen {
            if (Configurations.isAppDisabled.not()) {
                if (Configurations.isForceUpdateEnabled.not()) {
                    if (User.isLoggedIn.value) navController.navigateToCategories()
                    else {
                        navController.navigateToAuth()
                    }
                } else {
                    forceUpdateApp(context)
                }
            } else {
                disableApp(context)
            }
        }
    }
}

/**
 * Navigation Actions
 */

fun NavHostController.navigateToAuth() {
    navigate(AppDestinations.Login.name) {
        launchSingleTop = true
        popUpTo(graph.id) {
            inclusive = true
        }
    }
}

fun NavHostController.navigateToCategories() {
    navigate(AppDestinations.Categories.name) {
        launchSingleTop = true
        popUpTo(graph.id) {
            inclusive = true
        }
    }
}
