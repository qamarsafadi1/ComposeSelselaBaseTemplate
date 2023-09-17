package com.qamar.composetemplate.ui.core.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qamar.composetemplate.ui.theme.GrayBgColor
import com.qamar.composetemplate.ui.theme.GrayColor
import com.qamar.composetemplate.ui.theme.PinkColor
import com.qamar.composetemplate.util.getResource
import com.qamar.composetemplate.util.noRippleClickable
import com.selsela.cpapp.R

@Composable
fun FavButton(
    isFav: MutableState<Boolean>,
    onFavClick: (Boolean) -> Unit
) {
    var isInFav by remember {
        mutableStateOf(isFav.value)
    }
    val color by animateColorAsState(
        targetValue =
        if (isInFav)
            Color.White
        else
            GrayColor, label = ""
    )
    val bg by animateColorAsState(
        targetValue =
        if (isInFav)
            PinkColor
        else
            GrayBgColor, label = ""
    )
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(
                bg, CircleShape
            )
            .clip(RoundedCornerShape(33.dp))
            .noRippleClickable {
                isInFav = !isInFav
                onFavClick(isInFav)
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = R.drawable.heart.getResource(),
            contentDescription = "",
            colorFilter = ColorFilter.tint(
                color
            )
        )
    }
}

@Composable
fun FavButton(
    isFav: Boolean,
    onFavClick: (Boolean) -> Unit
) {

    Image(
        painter =
        if (isFav.not())
            R.drawable.heart.getResource()
        else R.drawable.fillheart.getResource(),
        contentDescription = "",
        modifier = Modifier.noRippleClickable {
            onFavClick(isFav.not())
        }
    )
}


@Preview
@Composable
private fun FavButtonPreview() {
    //FavButton(false) {}
}