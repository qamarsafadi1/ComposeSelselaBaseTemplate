package com.qamar.composetemplate.navigation

import androidx.annotation.StringRes
import com.selsela.cpapp.R

/**
 * All your app destinations shall be declared her
 */

enum class AppDestinations(
    @StringRes val title: Int = 0,
) {
    Splash,
    Login(title = R.string.login),
    Register(title = R.string.register),
    Categories(
        title = R.string.categories,
    ),
    CategoryProducts(),

    // Sheets
    VerifyCodeSheet(title = R.string.verify_code),
}
