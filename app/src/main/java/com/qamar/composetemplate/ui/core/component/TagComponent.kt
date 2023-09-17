package com.qamar.composetemplate.ui.core.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qamar.composetemplate.data.remote.config.model.config.Configurations
import com.qamar.composetemplate.ui.theme.regularStyle
import com.qamar.composetemplate.util.getResource
import com.selsela.cpapp.R


@Composable
fun TagLabel(
modifier: Modifier,
    tagName: String) {
    Box(modifier) {
        Surface(
            shape =
            if (Configurations.appLocal.value == "ar") {
                AbsoluteCutCornerShape(
                    topLeftPercent = 50,
                    bottomLeftPercent = 50
                )
            }else{
                AbsoluteCutCornerShape(
                    topRightPercent = 50,
                    bottomRightPercent = 50
                )
                 },
            modifier = Modifier
                .padding(start = 10.dp)
                .padding(8.dp)

        ) {
            Box(
                modifier = Modifier
                    .background(Red.copy(0.35f))
                    .defaultMinSize(minWidth = 100.dp)
                    .height(30.dp)
                    .padding(
                        start = MaterialTheme.typography.h6.fontSize.value.dp * 1.1f,
                        end = MaterialTheme.typography.h6.fontSize.value.dp / 2,
                    )
            ) {
                Text(
                    text = tagName,
                    color = Red,
                    style = regularStyle,
                    fontWeight = FontWeight.W300,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
        Image(
            painter = R.drawable.offersvg.getResource(),
            contentDescription = "offer tag",
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}

@Composable
fun TagLabelSmall(
modifier: Modifier,
    tagName: String) {
    Box(modifier) {
        Surface(
            shape =
            if (Configurations.appLocal.value == "ar") {
                AbsoluteCutCornerShape(
                    topLeftPercent = 50,
                    bottomLeftPercent = 50
                )
            }else{
                AbsoluteCutCornerShape(
                    topRightPercent = 50,
                    bottomRightPercent = 50
                )
                 },
            modifier = Modifier
                .padding(start = 10.dp)
                .padding(8.dp)

        ) {
            Box(
                modifier = Modifier
                    .background(Red.copy(0.35f))
                    .defaultMinSize(minWidth = 50.dp)
                    .height(20.dp)
                    .padding(
                        start = MaterialTheme.typography.h6.fontSize.value.dp * 1.1f,
                        end = MaterialTheme.typography.h6.fontSize.value.dp / 2,
                    )
            ) {
                Text(
                    text = tagName,
                    color = Red,
                    style = regularStyle,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
        Image(
            painter = R.drawable.offersvg.getResource(),
            contentDescription = "offer tag",
            modifier = Modifier.align(Alignment.CenterStart)
                .size(30.dp)
        )
    }
}

@Preview(locale = "ar")
@Composable
fun TagContnent() {
    TagLabelSmall(Modifier,tagName = "10 ريال")

}