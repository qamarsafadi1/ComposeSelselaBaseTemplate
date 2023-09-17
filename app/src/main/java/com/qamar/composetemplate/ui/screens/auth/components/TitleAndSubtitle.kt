package com.qamar.composetemplate.ui.screens.auth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qamar.composetemplate.data.remote.auth.model.user.User
import com.qamar.composetemplate.ui.theme.CodeOfStatePhoneNumber
import com.qamar.composetemplate.ui.theme.SubTextOnLanguageScreen
import com.qamar.composetemplate.ui.theme.TextOnSplashScreen
import com.qamar.composetemplate.ui.theme.TextSubtitleColorInAuthScreen
import com.selsela.cpapp.R

@Composable
fun TitleAndSubtitle() {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(51.dp))
        Text(
            text = stringResource(R.string.codeVerfiay),
            color = CodeOfStatePhoneNumber,
            style = SubTextOnLanguageScreen,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text =
            if (User.currentUser?.mobile.isNullOrEmpty())
                stringResource(R.string.pleaseSendCodeEmail)
            else stringResource(R.string.pleaseSendCode),
            color = TextSubtitleColorInAuthScreen,
            style = TextOnSplashScreen,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "${
                if (User.currentUser?.mobile.isNullOrEmpty()) User.currentUser?.email else
                   "${User.currentUser?.country?.prefix} ${User.currentUser?.mobile}"
            }",
            color = TextSubtitleColorInAuthScreen,
            style = TextOnSplashScreen,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(19.dp))
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun ShowTitleAndSubtitle() {
    TitleAndSubtitle()
}