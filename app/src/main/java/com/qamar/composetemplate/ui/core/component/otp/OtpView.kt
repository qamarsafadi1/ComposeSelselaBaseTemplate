package com.qamar.composetemplate.ui.core.component.otp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qamar.composetemplate.ui.theme.ColorAccent
import com.qamar.composetemplate.ui.theme.ColorPrimaryDark
import com.qamar.composetemplate.ui.theme.ColorSecondary
import com.qamar.composetemplate.ui.theme.FieldBg
import com.qamar.composetemplate.ui.theme.RedColor
import com.qamar.composetemplate.ui.theme.regularStyleWithoutLine

@Composable
fun OtpView(
    index: Int,
    text: String,
    isValid: Boolean,
) {

    val isFocused = text.length == index
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text.reversed()[index].toString()
    }
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = char.ifEmpty { "X" },
            style = regularStyleWithoutLine,
            textAlign = TextAlign.Center,
            color = if (isFocused) {
                if (char.isEmpty())
                    ColorSecondary.copy(0.50f)
                else {
                    if (isValid)
                        ColorPrimaryDark
                    else RedColor
                }
            } else {
                if (char.isEmpty())
                    ColorSecondary.copy(0.50f)
                else {
                    if (isValid)
                        ColorPrimaryDark
                    else RedColor
                }
            },
            fontSize = 20.sp,
            modifier = Modifier
                .width(69.dp)
                .height(54.dp)
                .background(
                    color = if (char.isEmpty())
                        FieldBg else Color.White, shape = RoundedCornerShape(6.dp)
                )
                .border(
                    1.dp,
                    color = if (char.isEmpty())
                        FieldBg else ColorAccent, shape = RoundedCornerShape(6.dp)
                )
                .align(Alignment.Center)
        )
    }

}