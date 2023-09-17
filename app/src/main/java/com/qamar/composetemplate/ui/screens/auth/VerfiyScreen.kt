package com.qamar.composetemplate.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.qamar.composetemplate.ui.core.component.Button
import com.qamar.composetemplate.ui.core.component.otp.Countdown
import com.qamar.composetemplate.ui.core.component.otp.OtpTextField
import com.qamar.composetemplate.ui.screens.auth.components.TitleAndSubtitle
import com.qamar.composetemplate.ui.screens.auth.events.AuthFormEvents
import com.qamar.composetemplate.ui.screens.auth.state.AuthUiState
import com.qamar.composetemplate.util.Common
import com.qamar.composetemplate.util.InputWrapper
import com.qamar.composetemplate.util.getActivity
import com.qamar.composetemplate.util.showSuccessTop
import com.selsela.cpapp.R
import de.palm.composestateevents.EventEffect

@Composable
fun VerifySheet(
    viewModel: AuthViewModel,
    goToHome: (startRoot: Boolean) -> Unit
) {
    val viewState: AuthUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isLoading = viewState == AuthUiState.Loading
    val context = LocalContext.current

    VerifyContent(
        viewModel.form.code,
        isCodeValid = viewModel.form.isCodeValid,
        isLoading = isLoading,
        onEvent = viewModel::onAuthEvent,
        onConfirm = viewModel::verifyCode,
        onResend = viewModel::resendCode
    )

    /**
     * Handle Ui state from flow
     */

    when (val state = viewState) {
        is AuthUiState.Success -> {
            EventEffect(
                event = state.onLogin,
                onConsumed = viewModel::onSuccess
            ) {
                goToHome(true)
            }

            EventEffect(
                event = state.onResend,
                onConsumed = viewModel::onSuccess
            ) {
                context.getActivity()?.showSuccessTop(it)
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
private fun VerifyContent(
    code: InputWrapper,
    isCodeValid: Any,
    isLoading: Boolean,
    onEvent: (AuthFormEvents) -> Unit,
    onConfirm: () -> Unit,
    onResend: () -> Unit
) {
    Column(
        Modifier
            .imePadding()
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Bottom)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
            )
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        TitleAndSubtitle()
        OtpTextField(otpText = code) { it, isFill ->
            onEvent(AuthFormEvents.OnOTPChanged(it, isFill))
        }
        Spacer(modifier = Modifier.height(23.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Countdown(seconds = 30, modifier = Modifier) {
                onResend()
            }
        }
        Spacer(modifier = Modifier.height(23.dp))
        Button(
            onClick = onConfirm, title = stringResource(id = R.string.confirm),
            modifier = Modifier.fillMaxWidth(),
            height = 50.dp,
            isEnabled = isCodeValid == true,
            isLoading = isLoading
        )
    }
}

@Preview
@Composable
private fun VerifyPreview() {
    VerifyContent(code = InputWrapper(
        inputValue = "nominal",
        borderColor = "cause",
        validationMessage = null
    ), isCodeValid = false, isLoading = false, onEvent = {}, onConfirm = {}, onResend = {})
}
