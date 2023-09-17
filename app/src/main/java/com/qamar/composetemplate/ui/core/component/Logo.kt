package com.qamar.composetemplate.ui.core.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qamar.composetemplate.ui.theme.ColorPrimary
import com.qamar.composetemplate.ui.theme.TextOnSplashScreenInLogo
import com.selsela.cpapp.R


// Loge and text and subtext
@Composable
fun LogeAndText() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.logoinsplcahscreen),
            contentDescription = "Logo",
            contentScale = ContentScale.Crop
        )
        Text(
            text = stringResource(R.string.cosmatic),
            style = TextOnSplashScreenInLogo,
            color = ColorPrimary
        )
    }
}

@Composable
@Preview(showSystemUi = true , showBackground = true)
fun ShowLogeAndText(){
    LogeAndText()
}