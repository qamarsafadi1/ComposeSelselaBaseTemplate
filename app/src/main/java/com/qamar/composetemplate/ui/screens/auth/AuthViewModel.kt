package com.qamar.composetemplate.ui.screens.auth

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qamar.composetemplate.data.remote.auth.model.user.User
import com.qamar.composetemplate.data.remote.auth.repository.AuthRepository
import com.qamar.composetemplate.ui.screens.auth.events.AuthForm
import com.qamar.composetemplate.ui.screens.auth.events.AuthFormEvents
import com.qamar.composetemplate.ui.screens.auth.state.AuthUiState
import com.qamar.composetemplate.util.Constants.NOT_VERIFIED
import com.qamar.composetemplate.util.networking.model.ErrorsData
import com.qamar.composetemplate.util.networking.model.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    application: Application
) : ViewModel() {

    var form: AuthForm by mutableStateOf(AuthForm(application))
        private set

    var openForgetPasswordDialog = mutableStateOf(false)
    var openResetPasswordDialog = mutableStateOf(false)


    fun onAuthEvent(event: AuthFormEvents) {
        form = when (event) {
            is AuthFormEvents.OnEmailChanged -> {
                event.value.let {
                    form.validateEmail(it)
                }
            }

            is AuthFormEvents.OnMobileChanged -> {
                event.value.let {
                    form.validateMobile(it)
                }
            }

            is AuthFormEvents.OnPasswordChanged -> {
                event.value.let {
                    form.validatePassword(it)
                }
            }

            is AuthFormEvents.OnNewPasswordChanged -> {
                event.value.let {
                    form.validateNewPassword(it)
                }
            }

            is AuthFormEvents.OnConfirmPasswordChanged -> {
                event.value.let {
                    form.validateRePassword(it)
                }
            }

            is AuthFormEvents.OnOTPChanged -> event.value.let {
                form.validateCode(it, event.isOtpFilled)
            }

            is AuthFormEvents.OnNameChanged -> event.value.let {
                form.validateName(it)
            }

            is AuthFormEvents.OnImageChanged -> event.value.let {
                form.onAvatarChange(it)
            }
        }
    }


    /**
     * State Flows
     */
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.IDLE)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()


    /**
     * API Requests
     */

    fun login() {
        viewModelScope.launch {
            _uiState.update {
                AuthUiState.Loading
            }
            repository.auth(form.mobile.inputValue, form.email.inputValue, form.name.inputValue)
                .collect { result ->
                    val authUiState = when (result.status) {
                        Status.SUCCESS -> {
                            if (result.data?.status == NOT_VERIFIED) {
                                AuthUiState.Success(
                                    user = result.data,
                                    onVerify = triggered(result.data)
                                )
                            } else {
                                result.data.let {
                                    AuthUiState.Success(
                                        user = it,
                                        onLogin = triggered(result.data)
                                    )
                                }
                            }
                        }

                        Status.LOADING ->
                            AuthUiState.Loading

                        Status.ERROR -> AuthUiState.Failed(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                    result.statusCode
                                )
                            )
                        )
                    }
                    _uiState.update {
                        authUiState
                    }
                }
        }
    }

    fun verifyCode() {
        viewModelScope.launch {
            _uiState.update {
                AuthUiState.Loading
            }
            repository.verifyCode(
                form.mobile.inputValue,
                form.code.inputValue
            )
                .collect { result ->
                    val authUiState = when (result.status) {
                        Status.SUCCESS -> {
                            result.data.let {
                                AuthUiState.Success(
                                    user = it,
                                    onLogin = triggered(result.data)
                                )
                            }
                        }

                        Status.LOADING ->
                            AuthUiState.Loading

                        Status.ERROR -> AuthUiState.Failed(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                    result.statusCode
                                )
                            )
                        )
                    }
                    _uiState.update {
                        authUiState
                    }
                }
        }
    }

    fun resendCode() {
        viewModelScope.launch {
            _uiState.update {
                AuthUiState.Loading
            }
            repository.resendCode(User.currentUser?.mobile ?: "")
                .collect { result ->
                    val authUiState = when (result.status) {
                        Status.SUCCESS -> {
                            result.data.let {
                                AuthUiState.Success(
                                    user = it,
                                    onResend = triggered(result.message ?: "")
                                )
                            }
                        }

                        Status.LOADING ->
                            AuthUiState.Loading

                        Status.ERROR -> AuthUiState.Failed(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                    result.statusCode
                                )
                            )
                        )
                    }
                    _uiState.update {
                        authUiState
                    }
                }
        }
    }

    fun forgetPassword() {
        viewModelScope.launch {
            _uiState.update {
                AuthUiState.Loading
            }
            repository.forgetPassword(
                form.mobile.inputValue,
            )
                .collect { result ->
                    val authUiState = when (result.status) {
                        Status.SUCCESS -> {
                            result.data.let {
                                AuthUiState.Success(
                                    user = it,
                                    onVerify = triggered(result.data)
                                )
                            }
                        }

                        Status.LOADING ->
                            AuthUiState.Loading

                        Status.ERROR -> AuthUiState.Failed(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                    result.statusCode
                                )
                            )
                        )
                    }
                    _uiState.update {
                        authUiState
                    }
                }
        }
    }

    fun resetPassword() {
        viewModelScope.launch {
            _uiState.update {
                AuthUiState.Loading
            }
            repository.resetPassword(
                form.mobile.inputValue,
                form.code.inputValue,
                form.password.inputValue,
            )
                .collect { result ->
                    val authUiState = when (result.status) {
                        Status.SUCCESS -> {
                            result.data.let {
                                AuthUiState.Success(
                                    user = it,
                                    onLogin = triggered(result.data)
                                )
                            }
                        }

                        Status.LOADING ->
                            AuthUiState.Loading

                        Status.ERROR -> AuthUiState.Failed(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                    result.statusCode
                                )
                            )
                        )
                    }
                    _uiState.update {
                        authUiState
                    }
                }
        }
    }

    /**
     * Reset handlers when single event
     */

    fun onSuccess() {
        _uiState.update {
            it.onReset()
        }
    }

    fun onVerify() {
        _uiState.update {
            it.onReset()
        }

    }

}