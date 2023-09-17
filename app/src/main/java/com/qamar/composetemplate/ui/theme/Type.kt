package com.qamar.composetemplate.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.selsela.cpapp.R


val Fonts = FontFamily(
    Font(R.font.bold, FontWeight.Bold),
    Font(R.font.light, FontWeight.Light),
    Font(R.font.regular, FontWeight.Normal),
    Font(R.font.regular, FontWeight.Medium),
    Font(R.font.extrabold, FontWeight.ExtraBold),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Fonts,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

val TextOnSplashScreen = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 0.sp,
    letterSpacing = 0.5.sp,
    color = ColorPrimaryDark
)

val TextOnSplashScreenInLogo = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Normal,
    fontSize = 25.sp,
    lineHeight = 0.sp,
    letterSpacing = 0.5.sp,
)
val TextOnSplashScreenInLogoSubtext = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 0.sp,
    letterSpacing = 3.sp,
)

val TextOnLanguageScreen = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Normal,
    fontSize = 45.sp,
    lineHeight = 57.sp,
    letterSpacing = 0.5.sp,
)
val SubTextOnLanguageScreen = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Normal,
    fontSize = 20.sp,
    lineHeight = 30.sp,
    letterSpacing = 0.5.sp,
)
val TextSignInAuthScreen = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Normal,
    fontSize = 29.sp,
    lineHeight = 0.sp,
    letterSpacing = 0.5.sp,
)
val SubTextOnInAuthScreen = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 30.sp,
    letterSpacing = 0.5.sp,
)

val TextOnHomeScreen = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 0.sp,
    letterSpacing = 0.5.sp,
)

val TextRatingOnHomeScreen = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Normal,
    fontSize = 10.sp,
    lineHeight = 0.sp,
    letterSpacing = 0.5.sp,
)

@OptIn(ExperimentalTextApi::class)
val TextCategoryOnHomeScreen = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Light,
    fontSize = 14.sp,
    lineHeight = 0.sp,
    letterSpacing = 0.5.sp,
    drawStyle = Stroke(
        miter = 10f,
        width = 1f,
        join = StrokeJoin.Round
    )

)

val buttonText = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
    color = Color.White,
)
val regularStyle = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    color = ColorPrimaryDark,
    lineHeight = 14.sp,
    letterSpacing = 0.5.sp,
)
val regularStylewithLines = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    color = ColorPrimaryDark,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp,
)
val regularStyle12 = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    color = ColorPrimaryDark,
)
val boldStyle = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
    color = Color.White,
)
val mediumStyle = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    color = ColorPrimaryDark,
)
val lightStyle = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Light,
    fontSize = 14.sp,
    color = ColorPrimaryDark,
)
val regularStyleWithoutLine = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Normal,
    fontSize = 21.sp,
    lineHeight = 55.sp,
    letterSpacing = 0.sp,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    ),

    )


val TextOnProductScreen = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 0.sp,
    letterSpacing = 0.5.sp,
)


val TextPriceOnProductDetails = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Medium,
    fontSize = 15.sp,
    lineHeight = 0.sp,
    letterSpacing = 0.5.sp,
)
val TextSubPriceOnProductDetails = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    lineHeight = 0.sp,
    letterSpacing = 0.5.sp,
)
val TextDetailsOnProductDetails = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 30.sp,
    letterSpacing = 0.5.sp,
)


val inputTextStyle = TextStyle(
    fontFamily = Fonts,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    color = ColorPrimaryDark,
)