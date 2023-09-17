package com.qamar.composetemplate.ui.core.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qamar.composetemplate.ui.theme.ColorAccent
import com.qamar.composetemplate.ui.theme.ColorPrimaryDark
import com.qamar.composetemplate.ui.theme.ColorSecondary
import com.qamar.composetemplate.ui.theme.lightStyle
import com.qamar.composetemplate.ui.theme.regularStyle
import com.qamar.composetemplate.util.getResource
import com.qamar.composetemplate.util.noRippleClickable
import com.selsela.cpapp.R

@Preview
@Composable
fun EmptyView(
    title: String = stringResource(id = R.string.no_products),
    description: String = "",
    backgroundColor: Color = Color.Transparent
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = R.drawable.emptyart.getResource(),
                contentDescription = "")
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = title, style = regularStyle,
                fontSize = 16.sp,
                color = ColorSecondary,
            )
            if (description.isEmpty().not()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = description, style = lightStyle,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationEmptyView(){
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(painter = R.drawable.bignotification.getResource(), contentDescription = "Empty Cart")
        Spacer(modifier = Modifier.height(36.8.dp))
        Text(
            text = stringResource(R.string.no_notifications),
            style = regularStyle,
            fontSize = 18.sp,
            color = ColorPrimaryDark
        )
        Spacer(modifier = Modifier.height(9.8.dp))
        Text(
            text = stringResource(R.string.no_notifications_lbl),
            style = lightStyle,
            fontSize = 16.sp
        )
    }
}

@Composable
fun EmptyFavView(onNavigate: () -> Unit){
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(painter = R.drawable.emptycart.getResource(), contentDescription = "Empty Cart")
        Spacer(modifier = Modifier.height(9.8.dp))
        Text(
            text = stringResource(R.string.fav_emtpy),
            style = regularStyle,
            fontSize = 18.sp,
            color = ColorPrimaryDark
        )
        Spacer(modifier = Modifier.height(39.8.dp))
        Text(
            text = stringResource(R.string.empty_fav),
            style = lightStyle,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(35.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.noRippleClickable {
                onNavigate()
            }) {
            Text(
                text = stringResource(id = R.string.view_order),
                style = regularStyle,
                fontSize = 16.sp,
                color = ColorAccent
            )
            Image(
                painter = R.drawable.browsearrow.getResource(),
                contentDescription = "Arrow"
            )
        }
    }
}