package com.qamar.composetemplate.ui.screens.auth

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.qamar.composetemplate.ui.screens.auth.components.Header
import com.qamar.composetemplate.ui.screens.auth.components.LoginForm
import com.qamar.composetemplate.ui.screens.auth.events.AuthForm
import com.qamar.composetemplate.ui.screens.auth.events.AuthFormEvents
import com.qamar.composetemplate.ui.screens.auth.events.LoginEvent
import com.qamar.composetemplate.ui.screens.auth.state.AuthUiState
import com.qamar.composetemplate.util.Common
import com.qamar.composetemplate.util.InputWrapper
import com.qamar.composetemplate.util.getActivity
import com.qamar.composetemplate.util.showSuccessTop
import com.selsela.cpapp.R
import de.palm.composestateevents.EventEffect

@Composable
fun LoginScreen(
    goToRoot: Boolean? = true,
    viewModel: AuthViewModel = hiltViewModel(),
    onEvent: (LoginEvent) -> Unit
) {

    val viewState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Box {
        LoginContent(
            form = viewModel.form,
            isLoading = viewState == AuthUiState.Loading,
            isEnabled = viewModel.form.areLoginInputsValid,
            onAuthEvent = viewModel::onAuthEvent,
            onLogin = viewModel::login,
            onEvent = onEvent
        )
    }

    /**
     * Handle Ui state from flow
     */
    when (val state = viewState) {
        is AuthUiState.Success -> {
            EventEffect(
                event = state.onLogin,
                onConsumed = viewModel::onSuccess
            ) {
                if (goToRoot == true)
                    onEvent(LoginEvent.GoToCategories)
                else onEvent(LoginEvent.LoggedIn)
            }

            EventEffect(
                event = state.onVerify,
                onConsumed = viewModel::onVerify
            ) {
                context.getActivity()?.showSuccessTop(
                    "${context.getString(R.string.your_code_is)}${it?.activationCode}"
                )
                onEvent(LoginEvent.GoToVerify)

            }
        }

        is AuthUiState.Failed -> {
            EventEffect(
                event = state.onFailure,
                onConsumed = viewModel::onVerify
            ) { error ->
                Common.handleErrors(
                    error?.responseMessage,
                    error?.errors,
                    context.getActivity()
                )
            }
        }

        else -> {}
    }
}

@Composable
fun LoginContent(
    form: AuthForm,
    isLoading: Boolean,
    isEnabled: Boolean,
    onAuthEvent: (AuthFormEvents) -> Unit,
    onLogin: () -> Unit,
    onEvent: (LoginEvent) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        Header()
        Column(
            Modifier
                .weight(1f)
                .background(Color.White)
                .padding(top = 29.dp)
        ) {
            LoginForm(form, onAuthEvent,isLoading,isEnabled,onLogin,onEvent)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginPreview() {
    LoginContent(form = AuthForm(
        application = Application(), mobile = InputWrapper(
            inputValue = "deserunt",
            borderColor = "aptent",
            validationMessage = null
        ), password = InputWrapper(
            inputValue = "verear",
            borderColor = "tale",
            validationMessage = null
        ), confirmPassword = InputWrapper(
            inputValue = "disputationi",
            borderColor = "imperdiet",
            validationMessage = null
        ), email = InputWrapper(
            inputValue = "sagittis",
            borderColor = "potenti",
            validationMessage = null
        ), code = InputWrapper(
            inputValue = "odio",
            borderColor = "fabulas",
            validationMessage = null
        ), name = InputWrapper(
            inputValue = "dolore",
            borderColor = "cras",
            validationMessage = null
        ), avatar = mutableStateOf(null)
    ), isLoading = false, isEnabled = false, onAuthEvent = {}, onLogin = {}) {}
}