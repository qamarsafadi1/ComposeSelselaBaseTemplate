package com.qamar.composetemplate.ui.core.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.selsela.cpapp.R
import com.qamar.composetemplate.ui.theme.ColorPrimary
import net.engawapg.lib.zoomable.ZoomState

@Composable
fun AsyncImage(
    imageUrl: Any,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val imageLoader = rememberAsyncImagePainter(
        model = imageUrl,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = state is AsyncImagePainter.State.Error
        },
    )
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading) {
            // Display a progress bar while loading
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    Modifier.size(10.dp),
                    color = ColorPrimary,
                    strokeWidth = 0.5.dp
                )
            }
        }

        Image(
            modifier = modifier,
            contentScale = contentScale,
            painter = if (isError.not()) imageLoader else painterResource(R.drawable.logoinsplcahscreen),
            contentDescription = null
        )
    }
}

@Composable

fun AsyncImage(
    imageUrl: Any,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    zoomState: ZoomState
) {
    SubcomposeAsyncImage(
        modifier = modifier,
        model = imageUrl,
        error = {
            Image(
                painter = painterResource(R.drawable.logoinsplcahscreen), contentDescription = "",
            )
        },
        loading = {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    Modifier.size(10.dp),
                    color = ColorPrimary,
                    strokeWidth = 0.5.dp
                )
            }
        },
        contentDescription = "",
        contentScale = contentScale,
        onSuccess = {
            zoomState.setContentSize(it.painter.intrinsicSize)

        }
    )
}
