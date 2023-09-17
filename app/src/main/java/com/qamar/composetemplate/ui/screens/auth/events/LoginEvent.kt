package com.qamar.composetemplate.ui.screens.auth.events

sealed interface LoginEvent {
    data object GoToRegister: LoginEvent
    data object GoToCategories: LoginEvent
    data object LoggedIn: LoginEvent
    data object GoToLogin: LoginEvent
    data object GoToVerify: LoginEvent
    data object GoToForgetPassword: LoginEvent
}