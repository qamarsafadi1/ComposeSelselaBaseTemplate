package com.qamar.composetemplate.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.qamar.composetemplate.ui.screens.splash.componets.LogeAndPhotoInBottom
import com.qamar.composetemplate.ui.screens.splash.state.GeneralUiState
import com.qamar.composetemplate.util.Common
import com.qamar.composetemplate.util.getActivity
import com.qamar.composetemplate.util.receiveToken
import com.qamar.composetemplate.util.withDelay
import com.selsela.cpapp.R
import de.palm.composestateevents.EventEffect

// Splash Screen
@Composable
fun SplashScreen(
    viewModel: ConfigViewModel = hiltViewModel(),
    onFinish: () -> Unit
) {
    val context = LocalContext.current
    val viewState: GeneralUiState by viewModel.uiState.collectAsStateWithLifecycle()
    SplashContent()

    /**
     * Handle Ui state from flow
     */

    when (val state = viewState) {
        is GeneralUiState.Success -> {
            EventEffect(
                event = state.onSuccess,
                onConsumed = viewModel::onReset
            ) {
                { onFinish() }.withDelay(1000)
            }
        }

        is GeneralUiState.Failed -> {
            EventEffect(
                event = state.onFailure,
                onConsumed = viewModel::onReset
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
    LaunchedEffect(Unit) {
        /**
         * Get fcm token
         */
        receiveToken()
    }
}

@Composable
private fun SplashContent() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(R.drawable.backgroundofsplcahscreen),
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentScale = ContentScale.FillBounds
        )
        LogeAndPhotoInBottom()

    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true, locale = "ar")
fun ShowSplashScreen() {
    SplashContent()
}

@Composable
@Preview(showSystemUi = true, showBackground = true, locale = "en")
fun ShowSplashEnScreen() {
    SplashContent()
}