package com.qamar.composetemplate.ui.core.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qamar.composetemplate.ui.theme.ColorAccent
import com.qamar.composetemplate.ui.theme.ColorPrimaryDark
import com.qamar.composetemplate.ui.theme.HintColor
import com.qamar.composetemplate.ui.theme.regularStyle
import com.selsela.cpapp.R


@Composable
fun <T> Spinner(
    selectedItem: Int = -1,
    placeHolder: String,
    modifier: Modifier = Modifier,
    raduis: Dp = 12.dp,
    height: Dp = 56.dp,
    items: List<T> = listOf(),
    onError: () -> Unit,
    onSelectedItem: (T) -> Unit,
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(selectedItem) }
    Box(
        modifier = Modifier
            .then(modifier)
            .requiredHeight(height)
            .background(ColorAccent, RoundedCornerShape(raduis))
            .clip(RoundedCornerShape(raduis))
            .clickable(onClick = {
                expanded = true
                if (items.isEmpty())
                     onError()
//                if (items.isEmpty().not() && items.first() is City) {
//                    onSelectedItem(City(id = -1) as T)
//                }

            })
            .padding(horizontal = 16.dp)

    ) {
        Text(
            if (items.isEmpty().not()) {
                if (selectedIndex != -1)
                    items[selectedIndex].toString()
                else placeHolder
            } else placeHolder,
            style = regularStyle,
            color = if (selectedIndex != -1)
                ColorPrimaryDark
            else HintColor,
            modifier = Modifier
                .align(Alignment.CenterStart),
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis

        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .wrapContentSize(Alignment.BottomStart)
                .border(1.dp, color = Color.Transparent, RoundedCornerShape(8.dp))
                .background(
                    Color.White
                )
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                    onSelectedItem(s)
                }) {
                    Text(
                        text = s.toString(),
                        style = regularStyle,
                        color = ColorPrimaryDark,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.spinnerarrow),
            contentDescription = "",
            modifier = Modifier.align(Alignment.CenterEnd)

        )
    }
}

