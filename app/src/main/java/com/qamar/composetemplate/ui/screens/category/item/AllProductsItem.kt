package com.qamar.composetemplate.ui.screens.category.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qamar.composetemplate.data.remote.categories.model.category.Category
import com.qamar.composetemplate.ui.screens.category.event.CategoriesEvents
import com.qamar.composetemplate.ui.theme.ColorAccent
import com.qamar.composetemplate.ui.theme.ColorPrimaryDark
import com.qamar.composetemplate.ui.theme.boldStyle
import com.qamar.composetemplate.util.noRippleClickable
import com.selsela.cpapp.R
import kotlinx.collections.immutable.ImmutableList

@Composable
 fun AllProductsItem(
    categories: ImmutableList<Category>?,
    selectedCategoryId: Int?,
    onClick: (CategoriesEvents) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(35.dp)
            .background(ColorAccent, RoundedCornerShape(5.dp))
            .noRippleClickable {
                val cat = categories?.find { it.id == selectedCategoryId }
                if (cat != null)
                    onClick(CategoriesEvents.OnCategoryClick(cat.id, cat.name))
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.all_products),
            style = boldStyle,
            fontSize = 13.sp,
            color = ColorPrimaryDark
        )
    }
}

@Composable
@Preview
fun AllProductsItemPreview(){
    AllProductsItem(categories = null, selectedCategoryId = null, onClick = {})
}