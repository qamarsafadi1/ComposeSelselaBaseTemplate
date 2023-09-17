package com.qamar.composetemplate.ui.screens.auth.events

import java.io.File

sealed interface AuthFormEvents {
    data class OnMobileChanged(val value: String) : AuthFormEvents
    data class OnNameChanged(val value: String) : AuthFormEvents
    data class OnPasswordChanged(val value: String) : AuthFormEvents
    data class OnConfirmPasswordChanged(val value: String) : AuthFormEvents
    data class OnNewPasswordChanged(val value: String) : AuthFormEvents
    data class OnEmailChanged(val value: String) : AuthFormEvents
    data class OnImageChanged(val value: File) : AuthFormEvents
    data class OnOTPChanged(val value: String,val isOtpFilled: Boolean) : AuthFormEvents

}