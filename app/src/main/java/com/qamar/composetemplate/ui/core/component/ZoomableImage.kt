package com.qamar.composetemplate.ui.core.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.qamar.composetemplate.data.remote.categories.model.products.Image
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ZoomableImage(imageUrl: String?, images: List<Image> ) {
    val state = rememberZoomState()
    val pagerState = rememberPagerState(pageCount = {images.size})
    LaunchedEffect(key1 = Unit) {
        pagerState.animateScrollToPage(
            images.indexOfFirst { it.imageUrl == imageUrl }
        )
    }

    HorizontalPager(
        modifier = Modifier
            .fillMaxSize(),
        state = pagerState
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            AsyncImage(
                images[it].imageUrl ?: "",
                modifier = Modifier
                    .padding(top = 74.dp)
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .zoomable(
                        state,
                    ),
                zoomState = state
            )
        }
    }

}