package com.qamar.composetemplate.ui.core.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qamar.composetemplate.ui.theme.ColorAccent
import com.qamar.composetemplate.ui.theme.ColorPrimary
import com.qamar.composetemplate.ui.theme.DisableColor
import com.qamar.composetemplate.ui.theme.buttonText
import com.selsela.cpapp.R

@Composable
fun Button(
    onClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    icon: Int? = null,
    gravity: Int? = 0,
    buttonBg: Color = ColorPrimary,
    textColor: Color = Color.White,
    borderColor: Color = Color.White,
    borderWidth: Dp = 1.dp,
    paddingBottom: Dp = 37.dp,
    height: Dp = 50.dp,
    raduis: Dp = 26.dp,
    fontSize: TextUnit = 16.sp,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    hasBoarder: Boolean = false,
    tint: Color = ColorAccent
) {
    Button(
        enabled = isEnabled,
        onClick = {
            onClick()
        },
        modifier = modifier.then(
            Modifier
                .padding(bottom = paddingBottom)
                .height(height)

        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
        ),
        shape = RoundedCornerShape(raduis),
        colors = ButtonDefaults.buttonColors(backgroundColor = buttonBg,
            disabledBackgroundColor = DisableColor),
        border = if (hasBoarder) BorderStroke( borderWidth, if(isEnabled) borderColor else Color.LightGray) else BorderStroke(
            0.dp,
            Color.Transparent
        ),
    ) {
        AnimatedVisibility(
            visible = isLoading.not()
        ) {
            Box {
                if (gravity == 0) {
                    if (icon != null) {
                        Image(
                            painter = painterResource(id = icon)
                            , contentDescription = "",
                            colorFilter = ColorFilter.tint(tint),
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                    }
                }
                AutoResizeText(
                    text = title, style = buttonText,
                    textAlign = TextAlign.Center,
                    color = textColor,
                    maxLines = 1,
                    fontSizeRange = FontSizeRange(
                        min = 10.sp,
                        max = fontSize,
                    ),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(1f)
                        .wrapContentHeight())
                if (gravity == 1) {
                    if (icon != null) {
                        Image(
                            painter = painterResource(id = icon), contentDescription = "",
                            colorFilter = ColorFilter.tint(tint),
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = isLoading,
        ) {
            if(hasBoarder){
                LottieAnimationView(
                    raw = R.raw.loading,
                    modifier = Modifier.size(25.dp)
                )
            }else {
                LottieAnimationView(
                    raw = R.raw.whiteloading,
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }
}