package com.qamar.composetemplate.ui.screens.splash.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.qamar.composetemplate.ui.core.component.LogeAndText

// column of Loge and text and photo in bottom
@Composable
fun LogeAndPhotoInBottom() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogeAndText()
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ShowLogeAndPhotoInBottom() {
    LogeAndPhotoInBottom()
}