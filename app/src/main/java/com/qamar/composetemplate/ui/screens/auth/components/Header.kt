package com.qamar.composetemplate.ui.screens.auth.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qamar.composetemplate.ui.theme.ColorAccent
import com.qamar.composetemplate.ui.theme.boldStyle
import com.qamar.composetemplate.ui.theme.regularStylewithLines
import com.qamar.composetemplate.util.getResource
import com.qamar.composetemplate.util.noRippleClickable
import com.selsela.cpapp.R

@Composable
fun ColumnScope.Header() {
    Box(modifier = Modifier.Companion.weight(1f)) {
        Image(
            painter = R.drawable.loginbg.getResource(),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            Modifier
                .statusBarsPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = boldStyle,
                fontSize = 18.sp,
                color = ColorAccent,
                modifier = Modifier.padding(top = 18.dp)
            )
            Spacer(modifier = Modifier.height(56.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(painter = R.drawable.logowhite.getResource(), contentDescription = "Logo")
                Spacer(modifier = Modifier.height(29.dp))
                Text(
                    text = stringResource(id = R.string.login_lbl),
                    style = regularStylewithLines,
                    fontSize = 17.sp,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 46.dp),
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}


@Composable
fun ColumnScope.RegisterHeader() {
    Box(modifier = Modifier.Companion.weight(1f)) {
        Image(
            painter = R.drawable.registerbg.getResource(),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            Modifier
                .statusBarsPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.new_register),
                style = boldStyle,
                fontSize = 18.sp,
                color = ColorAccent,
                modifier = Modifier.padding(top = 18.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
            ) {
                Image(
                    painter = R.drawable.logowhite.getResource(), contentDescription = "Logo",
                    modifier = Modifier.size(61.dp)
                )
                Spacer(modifier = Modifier.width(27.dp))
                Text(
                    text = stringResource(id = R.string.register_lbl),
                    style = regularStylewithLines,
                    fontSize = 17.sp,
                    color = Color.White,
                )
            }
        }
    }
}

@Composable
fun ColumnScope.AboutStoreHeader(navigateUp: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()
        .requiredHeight(241.dp)) {
        Image(
            painter = R.drawable.registerbg.getResource(),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(Modifier.padding(top = 18.dp)) {
                Text(
                    text = stringResource(id = R.string.about_app),
                    style = boldStyle,
                    fontSize = 18.sp,
                    color = ColorAccent,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Icon(
                    painter = R.drawable.back.getResource(), contentDescription = "",
                    tint = ColorAccent,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 20.dp)
                        .noRippleClickable {
                            navigateUp()
                        }
                )

            }

            Spacer(modifier = Modifier.height(30.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
            ) {
                Image(
                    painter = R.drawable.logowhite.getResource(), contentDescription = "Logo",
                    modifier = Modifier.size(61.dp)
                )
                Spacer(modifier = Modifier.width(27.dp))
                Text(
                    text = stringResource(id = R.string.about_app_lbl),
                    style = regularStylewithLines,
                    fontSize = 17.sp,
                    color = Color.White,
                )
            }
        }
    }
}