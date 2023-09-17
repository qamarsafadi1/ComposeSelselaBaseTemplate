package com.qamar.composetemplate.util

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import kotlinx.coroutines.InternalCoroutinesApi

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialNavigationApi::class, InternalCoroutinesApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun rememberBottomSheetNavigator(
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
    skipHalfExpanded: Boolean = true,
    confirmStateChange: (ModalBottomSheetValue) -> Boolean = { true }

): BottomSheetNavigator {
    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        animationSpec,
        confirmValueChange = confirmStateChange,
        skipHalfExpanded
    )


    return remember(sheetState) {
        BottomSheetNavigator(sheetState = sheetState)
    }
}