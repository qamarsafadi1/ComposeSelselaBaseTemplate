package com.qamar.composetemplate.ui.screens.auth

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.qamar.composetemplate.ui.core.component.Button
import com.qamar.composetemplate.ui.core.component.InputText
import com.qamar.composetemplate.ui.screens.auth.events.AuthForm
import com.qamar.composetemplate.ui.screens.auth.events.AuthFormEvents
import com.qamar.composetemplate.ui.screens.auth.state.AuthUiState
import com.qamar.composetemplate.ui.theme.ColorAccent
import com.qamar.composetemplate.ui.theme.ColorPrimaryDark
import com.qamar.composetemplate.ui.theme.ColorSecondary
import com.qamar.composetemplate.ui.theme.FieldBg
import com.qamar.composetemplate.ui.theme.boldStyle
import com.qamar.composetemplate.ui.theme.regularStyle
import com.qamar.composetemplate.util.Common
import com.qamar.composetemplate.util.InputWrapper
import com.qamar.composetemplate.util.getActivity
import com.qamar.composetemplate.util.getResource
import com.qamar.composetemplate.util.noRippleClickable
import com.qamar.composetemplate.util.showSuccessTop
import com.selsela.cpapp.R
import de.palm.composestateevents.EventEffect

@Composable
fun ForgetPasswordDialog(
    viewModel: AuthViewModel,
    onNavigate: () -> Unit,
    onEvent: () -> Unit,
) {
    val viewState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Dialog(onDismissRequest = onNavigate) {
        ForgetPasswordContent(
            viewModel.form,
            isLoading = viewState == AuthUiState.Loading,
            isEnabled = viewModel.form.mobile.inputValue.isEmpty()
                .not() && viewModel.form.mobile.validationMessage.isNullOrEmpty(),
            onAuthEvent = viewModel::onAuthEvent,
            onForgetPassword = viewModel::forgetPassword,
            onDismiss = onNavigate
        )
    }


    /**
     * Handle Ui state from flow
     */

    when (val state = viewState) {
        is AuthUiState.Success -> {
            EventEffect(
                event = state.onVerify,
                onConsumed = viewModel::onVerify
            ) {
                context.getActivity()?.showSuccessTop(
                    "${context.getString(R.string.your_code_is)}${it?.activationCode}"
                )
                onEvent()
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
fun ForgetPasswordContent(
    form: AuthForm,
    isLoading: Boolean,
    isEnabled: Boolean,
    onAuthEvent: (AuthFormEvents) -> Unit,
    onForgetPassword: () -> Unit,
    onDismiss: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp)
                .background(Color.White, RoundedCornerShape(23.dp))
                .padding(horizontal = 17.dp, vertical = 21.dp)
        ) {
            Box(Modifier.fillMaxWidth()) {
                Icon(
                    painter = R.drawable.close.getResource(),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .noRippleClickable {
                            onDismiss()
                        }
                )
                Text(
                    text = stringResource(id = R.string.reset_password),
                    style = boldStyle,
                    fontSize = 16.sp,
                    color = ColorPrimaryDark,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(26.dp))

            Text(
                text = stringResource(id = R.string.reset_password_lbl),
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth(),
                color = ColorSecondary,
                style = regularStyle,
                fontSize = 13.sp,
                textAlign = TextAlign.Center

            )
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = stringResource(id = R.string.mobile),
                color = ColorPrimaryDark,
                style = regularStyle,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            InputText(
                text = form.mobile,
                onValueChange = {
                    onAuthEvent(AuthFormEvents.OnMobileChanged(it))
                },
                bgColor = FieldBg,
                hint = stringResource(id = R.string.mobile),
                inputType = KeyboardType.Phone,
                hasBorder = false,
                cornerRaduis = 6.dp,
                height = 40.dp
            ) {

            }
            Spacer(modifier = Modifier.height(27.dp))

            Button(
                onClick = onForgetPassword, title = stringResource(id = R.string.send),
                modifier = Modifier.fillMaxWidth(),
                icon = R.drawable.goicon,
                textColor = ColorAccent,
                gravity = 1,
                paddingBottom = 0.dp,
                isLoading = isLoading,
                isEnabled = isEnabled,
                height = 46.dp
            )
        }
    }

}


@Preview
@Composable
private fun ForgetPasswordPreview() {
    ForgetPasswordContent(form = AuthForm(
        application = Application(), mobile = InputWrapper(
            inputValue = "tritani",
            borderColor = "hendrerit",
            validationMessage = null
        ), email = InputWrapper(
            inputValue = "duis",
            borderColor = "causae",
            validationMessage = null
        ), code = InputWrapper(
            inputValue = "mandamus",
            borderColor = "at",
            validationMessage = null
        ), name = InputWrapper(
            inputValue = "dicant",
            borderColor = "veniam",
            validationMessage = null
        ), avatar = mutableStateOf(null), password = InputWrapper(
            inputValue = "partiendo",
            borderColor = "in",
            validationMessage = null
        ), confirmPassword = InputWrapper(
            inputValue = "cras",
            borderColor = "tacimates",
            validationMessage = null
        ), newPassword = InputWrapper(
            inputValue = "a",
            borderColor = "moderatius",
            validationMessage = null
        )
    ),
        isLoading = false,
        isEnabled = false,
        onAuthEvent = {},
        onForgetPassword = {},
        onDismiss = {})

}
