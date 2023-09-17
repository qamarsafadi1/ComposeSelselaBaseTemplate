package com.qamar.composetemplate.navigation

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.qamar.composetemplate.util.hasIntentWithData
import com.qamar.composetemplate.util.rememberBottomSheetNavigator


@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterialApi::class)
@Composable
fun NavigationHost(
    activity: Activity,
    modifier: Modifier = Modifier,
    bottomSheetNavigator: BottomSheetNavigator = rememberBottomSheetNavigator(),
    navController: NavHostController = rememberNavController(bottomSheetNavigator),
    @SuppressLint("RestrictedApi") startDestination: String = remember {
        if (activity.hasIntentWithData()) {
            AppDestinations.Categories.name
        } else {
            if (navController.currentBackStack.value.isEmpty())
                AppDestinations.Splash.name
            else AppDestinations.Categories.name
        }
    }
) {
    val scaffoldState = rememberScaffoldState()
    var currentDestinations by remember {
        mutableStateOf(AppDestinations.Splash)
    }
    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        currentDestinations = AppDestinations.valueOf(
            destination.route?.substringBefore("/") ?: AppDestinations.Splash.name
        )
    }

    ModalBottomSheetLayout(
        bottomSheetNavigator,
        sheetShape = RoundedCornerShape(22.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                scaffoldState = scaffoldState,
                backgroundColor = Color.Transparent,
                bottomBar = {
                  // Bottom navigation
                }
            ) { _ ->
                NavHostGraph(navController, startDestination, modifier)
            }
        }
    }
}

