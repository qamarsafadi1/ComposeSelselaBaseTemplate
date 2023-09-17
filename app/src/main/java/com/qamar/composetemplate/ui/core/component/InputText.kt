package com.qamar.composetemplate.ui.core.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.qamar.composetemplate.ui.theme.ColorPrimary
import com.qamar.composetemplate.ui.theme.ColorPrimaryDark
import com.qamar.composetemplate.ui.theme.ColorSecondary
import com.qamar.composetemplate.ui.theme.HintColor
import com.qamar.composetemplate.ui.theme.inputTextStyle
import com.qamar.composetemplate.ui.theme.regularStyle
import com.qamar.composetemplate.util.InputWrapper


@Composable
fun InputText(
    text: InputWrapper,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    height: Dp = 46.dp,
    enabled: Boolean = true,
    hasBorder: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    canFoucs: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    inputType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    cursorColor: Color = ColorPrimary,
    bgColor: Color = Color.White,
    cornerRaduis: Dp = 5.dp,
    fillMax: Float = 1f,
    textStyle: TextStyle = inputTextStyle,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null
) {
    val color: Color by animateColorAsState(
        Color(text.borderColor.toColorInt()), label = ""
    )
    var passwordVisible by remember { mutableStateOf(false) }
    val focus = remember { mutableStateOf(false) }

    BasicTextField(
        value = text.inputValue,
        onValueChange = onValueChange,
        modifier = modifier.then(Modifier.onFocusEvent {
            focus.value = it.hasFocus
        }),
        textStyle = textStyle,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth(fillMax)
                    .defaultMinSize(minHeight = height)
                    .background(
                        if (canFoucs.not()) {
                            if (text.inputValue.isEmpty()) bgColor else bgColor
                        } else if (text.inputValue.isEmpty()) bgColor else Color.White,
                        shape = RoundedCornerShape(cornerRaduis)
                    )
                    .border(
                        if (hasBorder) 1.dp else 0.dp,
                        color = if (hasBorder) color else Color.Transparent,
                        RoundedCornerShape(cornerRaduis)
                    )
                    .padding(start = 14.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (leading != null) {
                        leading()
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                    if (text.inputValue.isEmpty() && focus.value.not()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = hint,
                                color = HintColor,
                                style = textStyle,
                                fontSize = 14.sp,
                                modifier = Modifier.weight(1f)
                            )
                            if (inputType == KeyboardType.Password) {
                                val image = if (passwordVisible) Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(imageVector = image, "", tint = ColorPrimaryDark)
                                }
                            } else if (inputType == KeyboardType.Phone) {
                                Box(
                                    modifier = Modifier
                                        .padding(end = 12.dp)
                                        .width(64.dp)
                                        .height(24.dp)
                                        .background(
                                            ColorSecondary.copy(0.20f),
                                            RoundedCornerShape(5.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "966", style = regularStyle, fontSize = 12.sp,
                                        color = ColorPrimary
                                    )
                                }
                            }
                        }
                    }
                    innerTextField()
                    Spacer(modifier = Modifier.weight(1f))
                    if (inputType == KeyboardType.Password) {
                        val image = if (passwordVisible) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, "", tint = ColorPrimaryDark)
                        }
                    } else if (inputType == KeyboardType.Phone) {
                        Box(
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .width(64.dp)
                                .height(24.dp)
                                .background(
                                    ColorSecondary.copy(0.20f),
                                    RoundedCornerShape(5.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "966", style = regularStyle, fontSize = 12.sp,
                                color = ColorPrimary
                            )
                        }
                    }
                    if (trailing != null) {
                        trailing()
                    }
                }

            }
        },
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        maxLines = maxLines,
        keyboardOptions = KeyboardOptions(
            keyboardType = inputType,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(cursorColor),
        visualTransformation = if (inputType == KeyboardType.Password && !passwordVisible)
            PasswordVisualTransformation()
        else VisualTransformation.None

    )
}



