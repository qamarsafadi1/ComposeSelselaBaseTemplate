package com.qamar.composetemplate.ui.screens.category

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.qamar.composetemplate.data.remote.categories.model.category.Category
import com.qamar.composetemplate.data.remote.categories.model.category.Children
import com.qamar.composetemplate.ui.core.views.LoadingView
import com.qamar.composetemplate.ui.screens.category.components.MainCategoriesList
import com.qamar.composetemplate.ui.screens.category.components.SubCategoriesList
import com.qamar.composetemplate.ui.screens.category.event.CategoriesEvents
import com.qamar.composetemplate.ui.screens.category.state.CategoryUiState
import com.qamar.composetemplate.util.Common
import com.qamar.composetemplate.util.getActivity
import de.palm.composestateevents.EventEffect
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun CategoriesScreen(
    viewModel: CategoryViewModel = hiltViewModel(),
    onClick: (CategoriesEvents) -> Unit
) {
    val viewState: CategoryUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    /**
     * Get categories request
     */

    LaunchedEffect(Unit) {
        if (!viewModel.isLoaded.value)
            viewModel.getCategories()
    }


    /**
     * Handle Ui state from flow
     */

    when (val state = viewState) {
        is CategoryUiState.Loading, CategoryUiState.IDLE -> LoadingView()
        is CategoryUiState.Success -> {
            CategoriesList(
                state.categories,
                onClick
            )
        }

        is CategoryUiState.Failed -> {
            EventEffect(
                event = state.onFailure,
                onConsumed = viewModel::onFailure
            ) { error ->
                Common.handleErrors(
                    error?.responseMessage,
                    error?.errors,
                    context.getActivity()
                )
            }
        }
    }

}

@Composable
private fun CategoriesList(
    categories: ImmutableList<Category>?,
    onClick: (CategoriesEvents) -> Unit
) {
    var subCategories by remember {
        mutableStateOf(categories?.firstOrNull()?.childs)
    }
    var brands by remember {
        mutableStateOf(categories?.firstOrNull()?.brands)
    }
    var selectedCategoryId by remember {
        mutableStateOf(categories?.firstOrNull()?.id)
    }
    Row(
        Modifier
            .fillMaxSize()
            .padding(top = 126.dp)
    ) {
        selectedCategoryId?.let {
            MainCategoriesList(
                it,
                categories = categories?.toImmutableList() ?: persistentListOf(),
            ) { catId ->
                selectedCategoryId = catId
                subCategories = categories?.find { it.id == catId }?.childs
                brands = categories?.find { it.id == catId }?.brands
            }
        }

        SubCategoriesList(subCategories, categories, selectedCategoryId, onClick)
    }
}


@Preview
@Composable
private fun CategoriesListPreview() {
    CategoriesList(
        categories = persistentListOf(
            Category(
                id = 2552,
                imageUrl = "http://www.bing.com/search?q=feugait",
                name = "Adolph Stark",
                productsCount = 3833,
                childs = listOf(
                    Children(
                        id = 7819,
                        name = "Morris Justice",
                        childs = listOf(),
                        brands = listOf(),
                        imageUrl = "https://duckduckgo.com/?q=principes"
                    )
                ),
                brands = listOf()
            )
        )
    ) {}
}