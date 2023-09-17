package com.qamar.composetemplate.ui.screens.category.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.qamar.composetemplate.data.remote.categories.model.category.Category
import com.qamar.composetemplate.data.remote.categories.model.category.Children
import com.qamar.composetemplate.ui.core.views.EmptyView
import com.qamar.composetemplate.ui.screens.category.event.CategoriesEvents
import com.qamar.composetemplate.ui.screens.category.item.AllProductsItem
import com.qamar.composetemplate.ui.screens.category.item.SubCategoryItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun RowScope.SubCategoriesList(
    subCategories: List<Children>?,
    categories: ImmutableList<Category>?,
    selectedCategoryId: Int?,
    onClick: (CategoriesEvents) -> Unit
) {
    Box(
        modifier = Modifier.Companion
            .weight(3f)
            .fillMaxHeight()
            .background(Color.White, RoundedCornerShape(8.dp))
    ) {
        if (subCategories.isNullOrEmpty().not()) {
            LazyColumn(
                contentPadding = PaddingValues(
                    top = 10.dp, end = 20.dp, start = 20.dp,
                    bottom = 120.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    AllProductsItem(categories, selectedCategoryId, onClick)
                }
                items(
                    subCategories ?: persistentListOf(),
                    key = {
                        it.id
                    }) { childern ->
                    SubCategoryItem(childern, onClick)
                }
            }
        } else EmptyView()
    }
}

