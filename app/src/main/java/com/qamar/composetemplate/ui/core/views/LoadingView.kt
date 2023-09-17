package com.qamar.composetemplate.ui.core.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.qamar.composetemplate.ui.core.component.LottieAnimationView
import com.qamar.composetemplate.ui.theme.BgColor
import com.selsela.cpapp.R

@Composable
fun LoadingView() {
    Box(
        Modifier.fillMaxSize()
            .background(BgColor),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimationView(raw = R.raw.loading,
        modifier = Modifier.size(20.dp))
    }
}


@Composable
fun HolderView() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

    }
}