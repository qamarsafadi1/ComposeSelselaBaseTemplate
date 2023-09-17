package com.qamar.composetemplate.ui.core.views

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.qamar.composetemplate.data.remote.config.model.config.Configurations
import com.qamar.composetemplate.ui.theme.RedColor
import com.qamar.composetemplate.util.dpToPx
import com.selsela.cpapp.R
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SwipeableContainer(
    currentSelected: T,
    item: T,
    onDelete: (T) -> Unit,
    onSelectedItem: () -> Unit,
    content: @Composable (Modifier) -> Unit
) {
    val swappableState = rememberSwipeableState(0)
    if (currentSelected != item) {
        LaunchedEffect(key1 = Unit) {
            swappableState.animateTo(0,
                tween(
                    durationMillis = 100,
                    delayMillis = 0
                ))
        }
    }
    LaunchedEffect(swappableState) {
        snapshotFlow { swappableState.currentValue }
            .distinctUntilChanged().collect { offset ->
                if (offset != 0) {
                    onSelectedItem()
                }
            }
    }


    val context = LocalContext.current
    val anchors = mapOf(
        0f to 0,
        if (Configurations.appLocal.value == "ar")
            (-75f).dpToPx(context) to 1
        else (75f).dpToPx(context) to 2
    ) // Maps anchor points (in px) to states
    val alignment = Alignment.CenterStart
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 83.dp)
            .swipeable(
                state = swappableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal,
            )
            .background(RedColor.copy(0.30f),RoundedCornerShape(12.dp))
    ) {
        Box(
            Modifier
                .align(Alignment.CenterStart)
                .fillMaxSize()
                .wrapContentHeight()
                .clickable {
                    coroutineScope.launch {
                        swappableState.animateTo(0)
                    }
                    onDelete(item)
                }
                .background(Color.Transparent)
                .padding(horizontal = 20.dp),
            contentAlignment = alignment
        ) {
            Image(
                painter = painterResource(id = R.drawable.delete),
                contentDescription = "Delete",
                modifier = Modifier.align(Alignment.CenterStart),
                colorFilter = ColorFilter.tint(color = RedColor)
            )
        }
        content(Modifier.offset {
            if (Configurations.appLocal.value == "ar")
                IntOffset(-(swappableState.offset.value.roundToInt()), 0)
            else IntOffset(swappableState.offset.value.roundToInt(), 0)
        })
    }
}
