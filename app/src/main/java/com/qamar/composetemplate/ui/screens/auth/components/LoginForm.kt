package com.qamar.composetemplate.ui.screens.auth.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qamar.composetemplate.ui.core.component.Button
import com.qamar.composetemplate.ui.core.component.InputText
import com.qamar.composetemplate.ui.screens.auth.events.AuthForm
import com.qamar.composetemplate.ui.screens.auth.events.AuthFormEvents
import com.qamar.composetemplate.ui.screens.auth.events.LoginEvent
import com.qamar.composetemplate.ui.theme.ColorAccent
import com.qamar.composetemplate.ui.theme.ColorPrimary
import com.qamar.composetemplate.ui.theme.ColorSecondary
import com.qamar.composetemplate.ui.theme.boldStyle
import com.qamar.composetemplate.ui.theme.lightStyle
import com.qamar.composetemplate.util.getResource
import com.qamar.composetemplate.util.noRippleClickable
import com.selsela.cpapp.R

@Composable
fun LoginForm(
    form: AuthForm,
    onAuthEvent: (AuthFormEvents) -> Unit,
    isLoading: Boolean,
    isEnabled: Boolean,
    onLogin: () -> Unit,
    onEvent: (LoginEvent) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .border(1.dp, ColorSecondary, RoundedCornerShape(5.dp))
        ) {
            InputText(
                text = form.mobile,
                onValueChange = {
                    onAuthEvent(AuthFormEvents.OnMobileChanged(it))
                },
                bgColor = Color.Transparent,
                leading = {
                    Icon(
                        painter = R.drawable.calling.getResource(),
                        contentDescription = "",
                        tint = ColorSecondary
                    )
                },
                hint = stringResource(id = R.string.mobile),
                inputType = KeyboardType.Phone,
                hasBorder = false
            ) {

            }
            Divider(
                Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = ColorSecondary
            )

            InputText(
                text = form.password,
                onValueChange = {
                    onAuthEvent(AuthFormEvents.OnPasswordChanged(it))
                },
                bgColor = Color.Transparent,
                leading = {
                    Icon(
                        painter = R.drawable.lock.getResource(),
                        contentDescription = "",
                        tint = ColorSecondary
                    )
                },
                hint = stringResource(id = R.string.password),
                inputType = KeyboardType.Password,
                hasBorder = false
            ) {

            }
        }
        androidx.compose.animation.AnimatedVisibility(
            visible = form.mobile.validationMessage.isNullOrEmpty().not(),
            modifier = Modifier.padding(start = 24.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = form.mobile.validationMessage ?: "",
                    style = lightStyle,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
        androidx.compose.animation.AnimatedVisibility(
            visible = form.password.validationMessage.isNullOrEmpty().not(),
            modifier = Modifier.padding(start = 24.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = form.password.validationMessage ?: "",
                    style = lightStyle,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .padding(end = 30.dp,top = 12.dp,bottom = 5.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.forget_password),
                style = boldStyle,
                fontSize = 12.sp,
                color = ColorPrimary,
                modifier = Modifier.noRippleClickable {
                    onEvent(LoginEvent.GoToForgetPassword)
                }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onLogin, title = stringResource(id = R.string.login),
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth(),
            icon = R.drawable.goicon,
            textColor = ColorAccent,
            gravity = 1,
            paddingBottom = 0.dp,
            isLoading = isLoading,
            isEnabled = isEnabled
        )
        Spacer(modifier = Modifier.height(23.dp))

        Text(
            text = stringResource(id = R.string.new_user),
            style = boldStyle,
            fontSize = 12.sp,
            color = ColorSecondary
        )
        Spacer(modifier = Modifier.height(9.dp))
        Text(
            text = stringResource(id = R.string.regiser_new_account),
            style = boldStyle,
            fontSize = 12.sp,
            color = ColorPrimary,
            modifier = Modifier.noRippleClickable {
                onEvent(LoginEvent.GoToRegister)
            }
        )
    }
}
