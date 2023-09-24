package com.qamar.composetemplate.ui.screens.auth.events

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.qamar.composetemplate.data.remote.auth.model.user.User
import com.qamar.composetemplate.ui.theme.GrayValidation
import com.qamar.composetemplate.ui.theme.RedValidation
import com.qamar.composetemplate.util.InputWrapper
import com.qamar.composetemplate.util.validateEmail
import com.qamar.composetemplate.util.validatePassword
import com.qamar.composetemplate.util.validatePhone
import com.qamar.composetemplate.util.validateRePassword
import com.qamar.composetemplate.util.validateRequired
import com.selsela.cpapp.R
import java.io.File
import javax.inject.Inject

@Stable
data class AuthForm @Inject constructor(
    private val application: Application,
    @Stable
    val mobile: InputWrapper = InputWrapper(
        inputValue = if (User.currentUser != null) User.currentUser?.mobile ?: ""
        else ""
    ),
    @Stable
    val email: InputWrapper = InputWrapper(
        inputValue = if (User.currentUser != null) User.currentUser?.email ?: ""
        else ""
    ),
    @Stable
    val code: InputWrapper = InputWrapper(""),
    @Stable
    val name: InputWrapper = InputWrapper(
        if (User.currentUser != null) User.currentUser?.name ?: ""
        else ""
    ),
    @Stable
    val avatar: MutableState<File?> = mutableStateOf(null),
    @Stable
    val password: InputWrapper = InputWrapper(
        inputValue = ""
    ),
    @Stable
    val confirmPassword: InputWrapper = InputWrapper(
        inputValue = ""
    ),
    @Stable
    val newPassword: InputWrapper = InputWrapper(
        inputValue = ""
    ),
) {

    fun validateEmail(value: String): AuthForm {
        return if (value.isEmpty().not()) {
            val errorId = value.validateEmail(
                application.applicationContext,
                application.applicationContext.getString(R.string.email)
            )
            this.copy(
                email = this.email.copy(
                    inputValue = value, validationMessage = errorId,
                    borderColor = if (errorId.isNotEmpty()) RedValidation else GrayValidation
                )
            )
        } else {
            this.copy(
                email = this.email.copy(
                    inputValue = value, validationMessage = null,
                    borderColor = GrayValidation
                )
            )
        }
    }

    fun validateMobile(value: String): AuthForm {
        return if (value.isEmpty().not()) {
            val errorId = value.validatePhone(application.applicationContext)
            this.copy(
                mobile = this.mobile.copy(
                    inputValue = value, validationMessage = errorId,
                    borderColor = if (errorId.isNotEmpty()) RedValidation else GrayValidation
                )
            )
        } else {
            this.copy(
                mobile = this.mobile.copy(
                    inputValue = value, validationMessage = null,
                    borderColor = GrayValidation
                )
            )
        }
    }

    fun validateCode(value: String, isOtpFilled: Boolean): AuthForm {
        return if (value.isEmpty().not() || isOtpFilled.not()) {
            val errorId = value.validateRequired(
                application.applicationContext, application.applicationContext.getString(
                    R.string.verify_code
                )
            )
            this.copy(
                code = this.code.copy(
                    inputValue = value, validationMessage = errorId,
                    borderColor = if (errorId.isNotEmpty()) RedValidation else GrayValidation
                )
            )
        } else {
            this.copy(
                code = this.code.copy(
                    inputValue = value, validationMessage = null,
                    borderColor = GrayValidation
                )
            )
        }

    }

    fun validateName(value: String): AuthForm {
        return if (value.isEmpty().not()) {
            val errorId = value.validateRequired(
                application.applicationContext, application.applicationContext.getString(
                    R.string.full_name
                )
            )
            this.copy(
                name = this.name.copy(
                    inputValue = value, validationMessage = errorId,
                    borderColor = if (errorId.isNotEmpty()) RedValidation else GrayValidation
                )
            )
        } else {
            this.copy(
                name = this.name.copy(
                    inputValue = value, validationMessage = null,
                    borderColor = GrayValidation
                )
            )
        }

    }

    fun validatePassword(value: String): AuthForm {
        return if (value.isEmpty().not()) {
            val errorId = value.validatePassword(application.applicationContext)
            this.copy(
                password = this.password.copy(
                    inputValue = value, validationMessage = errorId,
                    borderColor = if (errorId.isNotEmpty()) RedValidation else GrayValidation
                )
            )
        } else {
            this.copy(
                password = this.password.copy(
                    inputValue = value, validationMessage = null,
                    borderColor = GrayValidation
                )
            )
        }
    }

    fun validateNewPassword(value: String): AuthForm {
        return if (value.isEmpty().not()) {
            val errorId = value.validatePassword(application.applicationContext)
            this.copy(
                newPassword = this.newPassword.copy(
                    inputValue = value, validationMessage = errorId,
                    borderColor = if (errorId.isNotEmpty()) RedValidation else GrayValidation
                )
            )
        } else {
            this.copy(
                newPassword = this.newPassword.copy(
                    inputValue = value, validationMessage = null,
                    borderColor = GrayValidation
                )
            )
        }
    }

    fun validateRePassword(value: String): AuthForm {
        return if (value.isEmpty().not()) {
            val errorId =
                value.validateRePassword(application.applicationContext, password.inputValue)
            this.copy(
                confirmPassword = this.confirmPassword.copy(
                    inputValue = value, validationMessage = errorId,
                    borderColor = if (errorId.isNotEmpty()) RedValidation else GrayValidation
                )
            )
        } else {
            this.copy(
                confirmPassword = this.confirmPassword.copy(
                    inputValue = value, validationMessage = null,
                    borderColor = GrayValidation
                )
            )
        }
    }

    fun onAvatarChange(value: File): AuthForm {
        return this.copy(
            avatar = mutableStateOf(value)
        )

    }

    @Stable
    val areLoginInputsValid
        get() = mobile.inputValue.isNotEmpty() && mobile.validationMessage.isNullOrEmpty()
                && password.inputValue.isNotEmpty() && password.validationMessage.isNullOrEmpty()

    @Stable
    val isNotValid
        get() = password.validationMessage.isNullOrEmpty()
            .not() || mobile.validationMessage.isNullOrEmpty().not()
                || name.validationMessage.isNullOrEmpty().not()
                || email.validationMessage.isNullOrEmpty().not()
                || confirmPassword.validationMessage.isNullOrEmpty().not()

    @Stable
    val validationMessage
        get() = if (password.validationMessage.isNullOrEmpty().not()) password.validationMessage
        else if (mobile.validationMessage.isNullOrEmpty().not()) mobile.validationMessage
        else if (name.validationMessage.isNullOrEmpty().not()) name.validationMessage
        else if (email.validationMessage.isNullOrEmpty().not()) email.validationMessage
        else confirmPassword.validationMessage


    @Stable
    val isCodeValid
        get() = code.inputValue.isNotEmpty() && code.validationMessage.isNullOrEmpty() && code.inputValue.length == 4 && (mobile.inputValue.isNotEmpty() && mobile.validationMessage.isNullOrEmpty() || email.inputValue.isNotEmpty() && email.validationMessage.isNullOrEmpty())

}
