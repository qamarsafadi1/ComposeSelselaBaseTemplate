package com.qamar.composetemplate.ui.core.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qamar.composetemplate.ui.theme.ColorAccent
import com.qamar.composetemplate.ui.theme.ColorPrimary
import com.qamar.composetemplate.ui.theme.boldStyle
import com.qamar.composetemplate.util.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: Any,
    navigationIcon: Int?,
    onNavigationIconClick: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(99.dp),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (title is Int)
                        stringResource(title)
                    else title.toString(),
                    style = boldStyle,
                    color = ColorPrimary,
                    fontSize = 16.sp,
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = ColorAccent
        ),
        navigationIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxHeight()
            ) {
                if (navigationIcon != null)
                    Image(
                        painter = painterResource(navigationIcon),
                        contentDescription = "cart",
                        modifier = Modifier
                            .padding(start = 19.dp)
                            .noRippleClickable {
                                onNavigationIconClick()
                            }
                    )
            }
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(77.dp)
            ) {
                actions()
            }
        }
    )
}