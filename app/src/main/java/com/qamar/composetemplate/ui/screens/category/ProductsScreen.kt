package com.qamar.composetemplate.ui.screens.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.qamar.composetemplate.data.remote.categories.model.category.Children
import com.qamar.composetemplate.data.remote.categories.model.products.Product
import com.qamar.composetemplate.ui.core.views.EmptyView
import com.qamar.composetemplate.ui.core.views.LoadingView
import com.qamar.composetemplate.ui.screens.category.components.CategoriesTabs
import com.qamar.composetemplate.ui.screens.category.item.ProductItem
import com.qamar.composetemplate.ui.screens.category.state.ProductUiState
import com.qamar.composetemplate.util.Common
import com.qamar.composetemplate.util.fromJson
import com.qamar.composetemplate.util.getActivity
import com.qamar.composetemplate.util.log
import com.qamar.composetemplate.util.toJson
import de.palm.composestateevents.EventEffect
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun ProductsScreen(
    viewModel: CategoryViewModel,
) {

    val viewState: ProductUiState by viewModel.productUiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lazyColumnListState = rememberLazyGridState()

    // check if can paginate
    val isScrollToEnd by remember {
        derivedStateOf {
            lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == lazyColumnListState.layoutInfo.totalItemsCount - 1
                    && viewModel.canPaginate
        }
    }

    // listen if pagination allowed
    LaunchedEffect(key1 = isScrollToEnd) {
        if (isScrollToEnd)
            viewModel.getCategoryProducts()
    }

    /**
     * Handle Ui state from flow
     */

    when (val state = viewState) {

        is ProductUiState.Empty, ProductUiState.IDLE -> EmptyView()

        is ProductUiState.Failed -> {
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

        else -> {
            ProductContent(
                isLoading = viewState is ProductUiState.Loading,
                lazyColumnListState = lazyColumnListState,
                list = viewModel.productsList.value.toImmutableList(),
                categories = viewModel.categoriesList.value.toImmutableList(),
                onCategoryClick = viewModel::onCategoryClick,
            )

        }
    }
}

@Composable
private fun ProductContent(
    isLoading: Boolean,
    lazyColumnListState: LazyGridState,
    list: ImmutableList<Product> = persistentListOf(),
    categories: ImmutableList<Children>,
    onCategoryClick: (Int) -> Unit,
) {

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
    ) {
        if (categories.isEmpty().not()) {
            CategoriesTabs(categories) {
                it.id.log("child_id")
                onCategoryClick(it.id)
            }
        }
        if (isLoading) {
            LoadingView()
        } else {
            LazyVerticalGrid(
                contentPadding = PaddingValues(vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                state = lazyColumnListState,
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(9.dp),
            ) {
                itemsIndexed(list,
                    key = { index, item ->
                        "${index}:${item.id}"
                    }) { index, it ->
                    ProductItem(
                        it.toJson().fromJson(),
                        index,
                    )
                }
            }
        }
    }

}


@Preview
@Composable
private fun SearchScreenPreview() {
    ProductContent(
        false,
        lazyColumnListState = rememberLazyGridState(),
        list = persistentListOf(),
        categories = persistentListOf(),
        onCategoryClick = {},
    )
}