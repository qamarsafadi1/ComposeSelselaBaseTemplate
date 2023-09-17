package com.qamar.composetemplate.ui.screens.category.item


import ExpandableCard
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qamar.composetemplate.data.remote.categories.model.category.Children
import com.qamar.composetemplate.ui.core.component.AsyncImage
import com.qamar.composetemplate.ui.core.component.AutoResizeText
import com.qamar.composetemplate.ui.core.component.FontSizeRange
import com.qamar.composetemplate.ui.core.views.VerticalGrid
import com.qamar.composetemplate.ui.screens.category.event.CategoriesEvents
import com.qamar.composetemplate.ui.theme.BorderGrayColor
import com.qamar.composetemplate.ui.theme.ColorPrimaryDark
import com.qamar.composetemplate.ui.theme.regularStyle
import com.qamar.composetemplate.util.noRippleClickable

@Composable
 fun SubCategoryItem(
    childern: Children,
    onClick: (CategoriesEvents) -> Unit
) {
    ExpandableCard(
        title = childern.name, isOpenByDefault = false,
        padding = 15.dp,
        hasItem = childern.childs.isEmpty().not(),
        onClick = {
            onClick(CategoriesEvents.OnCategoryClick(childern.id, childern.name))
        }
    ) {
        VerticalGrid(
            columns = 3,
        ) {
            childern.subCategories.forEach {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(bottom = 8.dp, end = 8.dp)
                        .noRippleClickable {
                            if (it.id == -1)
                                onClick(
                                    CategoriesEvents.OnCategoryClick(
                                        childern.id,
                                        childern.name
                                    )
                                )
                            else onClick(
                                CategoriesEvents.OnCategoryClick(
                                    it.id,
                                    it.name
                                )
                            )

                        },
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(55.dp)
                            .clip(CircleShape)
                            .background(Color.White, CircleShape)
                            .border(0.5.dp, BorderGrayColor, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            imageUrl = it.imageUrl,
                            modifier = Modifier.size(33.dp)
                        )
                    }
                    AutoResizeText(
                        text = it.name, style = regularStyle,
                        fontSizeRange = FontSizeRange(min = 8.sp, max = 10.sp),
                        color = ColorPrimaryDark,
                        maxLines = 1,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SubCategoryItemPreview(){
    SubCategoryItem(childern = Children(
        id = 5568,
        name = "Quentin Fields",
        childs = listOf(),
        brands = listOf(),
        imageUrl = "https://duckduckgo.com/?q=menandri"
    ), onClick = {}
    )
}