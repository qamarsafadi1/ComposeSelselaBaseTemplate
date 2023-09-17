package com.qamar.composetemplate.ui.screens.category.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qamar.composetemplate.data.remote.categories.model.category.Children
import com.qamar.composetemplate.ui.theme.ColorPrimary
import com.qamar.composetemplate.ui.theme.ColorPrimaryDark
import com.qamar.composetemplate.ui.theme.ColorSecondary
import com.qamar.composetemplate.ui.theme.DividerColor
import com.qamar.composetemplate.ui.theme.TabsColor
import com.qamar.composetemplate.ui.theme.boldStyle
import com.qamar.composetemplate.ui.theme.regularStyle
import com.qamar.composetemplate.util.log
import kotlinx.collections.immutable.ImmutableList

@Composable
fun CategoriesTabs(
    categories: ImmutableList<Children>,
    onSelectTab: (Children) -> Unit
) {
    var tabIndex by remember { mutableStateOf(0) }
    ScrollableTabRow(
        selectedTabIndex = tabIndex,
        modifier = Modifier
            .fillMaxWidth(), edgePadding = 9.dp,
        containerColor = TabsColor,
        indicator = {
            it.size.log("tabpositios")
            categories.size.log("tabpositios")
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(it[tabIndex]),
                color = ColorPrimary,
                height = 1.dp
            )
        },
        divider = {
            Divider(
                thickness = 0.5.dp,
                color = DividerColor.copy(0.20f)
            )
        }
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                text = {
                    Text(
                        category.name,
                        style = if (tabIndex == index)
                            boldStyle else regularStyle,
                        color = if (tabIndex == index) ColorPrimaryDark else ColorSecondary,
                        fontSize = 13.sp
                    )
                },
                selected = tabIndex == index,
                onClick = {
                    tabIndex = index
                    onSelectTab(category)
                },
            )
        }
    }
}