package com.qamar.composetemplate.ui.core.component.otp

import android.os.CountDownTimer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qamar.composetemplate.ui.theme.ColorPrimaryDark
import com.qamar.composetemplate.ui.theme.TextSubtitleColorInAuthScreen
import com.qamar.composetemplate.ui.theme.regularStyle
import com.qamar.composetemplate.util.noRippleClickable
import com.selsela.cpapp.R
import java.util.Locale

@Composable
fun Countdown(
    seconds: Long, modifier: Modifier,
    resend: () -> Unit
) {
    val millisInFuture: Long = seconds * 1000

    var timeData by remember {
        mutableLongStateOf(millisInFuture)
    }
    var isFinish by remember {

        mutableStateOf(false)
    }

    val countDownTimer =
        object : CountDownTimer(millisInFuture, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeData = millisUntilFinished
            }

            override fun onFinish() {
                isFinish = true
            }
        }

    DisposableEffect(key1 = "key") {
        countDownTimer.start()
        onDispose {
            countDownTimer.cancel()
        }
    }
    val secMilSec: Long = 1000
    val minMilSec = 60 * secMilSec
    val hourMilSec = 60 * minMilSec
    val dayMilSec = 24 * hourMilSec
    val minutes = (timeData % dayMilSec % hourMilSec / minMilSec).toInt()
    val seconds = (timeData % dayMilSec % hourMilSec % minMilSec / secMilSec).toInt()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = String.format(
                Locale.ENGLISH,
                "%02d:%02d", minutes, seconds
            ),
            color = ColorPrimaryDark,
            fontSize = 20.sp,
            style = regularStyle
        )
        Spacer(modifier = Modifier.height(31.dp))
        if (isFinish) {
            Text(
                text = stringResource(id = R.string.resend),
                color = TextSubtitleColorInAuthScreen,
                modifier = Modifier.noRippleClickable {
                    resend()
                    isFinish = false
                    timeData = millisInFuture
                    countDownTimer.start()
                },
                style = regularStyle,
                fontSize = 18.sp
            )
        }
    }

}
