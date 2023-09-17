package com.qamar.composetemplate.ui.screens.category.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qamar.composetemplate.data.remote.categories.model.category.Category
import com.qamar.composetemplate.ui.core.component.AsyncImage
import com.qamar.composetemplate.ui.core.component.AutoResizeText
import com.qamar.composetemplate.ui.core.component.FontSizeRange
import com.qamar.composetemplate.ui.theme.ColorPrimary
import com.qamar.composetemplate.ui.theme.boldStyle
import com.qamar.composetemplate.ui.theme.regularStyle
import com.qamar.composetemplate.util.getResource
import com.qamar.composetemplate.util.noRippleClickable
import com.selsela.cpapp.R
import kotlinx.collections.immutable.ImmutableList

@Composable
fun RowScope.MainCategoriesList(
    selectedCategoryId: Int,
    categories: ImmutableList<Category>,
    onSelectCategory: (Int) -> Unit
) {
    LazyColumn(
        Modifier
            .weight(1f)
            .offset(x = 8.dp),
        verticalArrangement = Arrangement.spacedBy(11.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(top = 10.dp, bottom = 190.dp)
    ) {
        items(categories) {
            val isSelected = it.id == selectedCategoryId
            MainCategoryItem(
                isSelected,
                it,
                onSelectCategory
            )
        }
    }
}

@Composable
fun MainCategoryItem(
    isSelected: Boolean, category: Category,
    onSelectCategory: (Int) -> Unit
) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .noRippleClickable {
                onSelectCategory(category.id)
            }
            .padding(vertical = 11.dp)
    ) {
        if (isSelected) {
            Image(
                painter = R.drawable.selectedcat.getResource(),
                contentDescription = "",
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(Color.White.copy(0.26f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    imageUrl = category.imageUrl,
                    modifier = Modifier.size(28.dp)
                )
            }
            AutoResizeText(
                text = category.name,
                style = if (isSelected) boldStyle else  regularStyle,
                fontSizeRange = FontSizeRange(min = 10.sp, max = 12.sp),
                maxLines = 2,
                color = ColorPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(62.dp)
            )
        }
    }
}
