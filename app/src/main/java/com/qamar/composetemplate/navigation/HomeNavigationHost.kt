package com.qamar.composetemplate.navigation

import androidx.camera.core.ExperimentalGetImage
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.qamar.composetemplate.navigation.HomeARGS.ID_ARGS
import com.qamar.composetemplate.navigation.HomeARGS.TITLE_ARGS
import com.qamar.composetemplate.ui.core.views.TopAppBarContainer
import com.qamar.composetemplate.ui.screens.category.CategoriesScreen
import com.qamar.composetemplate.ui.screens.category.CategoryViewModel
import com.qamar.composetemplate.ui.screens.category.ProductsScreen
import com.qamar.composetemplate.ui.screens.category.event.CategoriesEvents
import com.qamar.composetemplate.ui.theme.BgColor
import com.selsela.cpapp.R

object HomeARGS {
    const val ID_ARGS = "id"
    const val TITLE_ARGS = "title"

}

val CATEGORY_SCREEN = AppDestinations.Categories.name
val CATEGORY_PRODUCTS_SCREEN = AppDestinations.CategoryProducts.name
val CATEGORY_PRODUCTS_ROUTE = "${CATEGORY_PRODUCTS_SCREEN}/{$TITLE_ARGS}/{${ID_ARGS}}"

@androidx.annotation.OptIn(ExperimentalGetImage::class)
fun NavGraphBuilder.homeNavigationHost(navController: NavHostController) {

    /**
     * Screens
     */

    composable(CATEGORY_SCREEN) {
        Column(
            Modifier
                .fillMaxSize()
                .background(BgColor)
        ) {
            TopAppBarContainer(title = AppDestinations.Categories.title,
                navigationIcon = null,
                onNavigationIconClick = {},
                actions = {
                    // Menu items
                }
            ) {
                CategoriesScreen {
                    when (it) {
                        is CategoriesEvents.OnCategoryClick -> {
                            navController.navigateToCategoryProducts(it.title, it.categoryId)
                        }
                    }
                }
            }
        }
    }

    composable(
        CATEGORY_PRODUCTS_ROUTE,
        arguments = listOf(
            navArgument(TITLE_ARGS) {
                type = NavType.StringType
            },
            navArgument(ID_ARGS) {
                type = NavType.IntType
            },
        )
    ) {
        val title = it.arguments?.getString(TITLE_ARGS)
        val id = it.arguments?.getInt(ID_ARGS)
        val viewModel = hiltViewModel<CategoryViewModel>()
        LaunchedEffect(Unit) {
            viewModel.categoryTitle.value = title ?: ""
            viewModel.categoryId.intValue = id ?: 0
            viewModel.mainCategoryId.intValue = id ?: 0
        }
        LaunchedEffect(viewModel.isLoaded.value) {
            if (viewModel.isLoaded.value.not())
                viewModel.getCategoryProducts()
        }
        TopAppBarContainer(title = title ?: "",
            navigationIcon = R.drawable.back,
            onNavigationIconClick = navController::navigateUp,
            actions = {
            }
        ) {
            Column(Modifier.padding(top = 88.dp)) {
                ProductsScreen(
                    viewModel,
                )
            }
        }
    }
}


/**
 * Navigation Actions
 */

fun NavHostController.navigateToCategoryProducts(title: String, id: Int) {
    this.navigate("$CATEGORY_PRODUCTS_SCREEN/$title/$id")
}

