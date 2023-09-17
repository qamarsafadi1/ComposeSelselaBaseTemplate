package com.qamar.composetemplate.util

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.LocaleManager
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Handler
import android.os.LocaleList
import android.os.Looper
import android.text.Html
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.qamar.composetemplate.BaseApp.Companion.LocalData
import com.qamar.composetemplate.data.local.PreferenceHelper.fcmToken
import com.qamar.composetemplate.data.remote.config.model.config.Configurations
import com.qamar.composetemplate.util.networking.model.ErrorBase
import com.qamar.composetemplate.util.networking.model.Resource
import com.selsela.cpapp.BuildConfig
import com.selsela.cpapp.R
import com.tapadoo.alerter.Alerter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Locale


var absoulutePath: String? = null

fun Activity.showSuccessTop(message: String) {
    Alerter.create(this)
        .setTitle("")
        .setText(message)
        .setTitleAppearance(R.style.AlertTextAppearance_Title_1)
        .setTextAppearance(R.style.AlertTextAppearance_Text_1)
        .setBackgroundColorRes(R.color.green) // or setBackgroundColorInt(Color.CYAN)
        .setIcon(R.drawable.component_6___2)
        .setIconColorFilter(0) //
        .show()
}

fun Activity.showErrorTop(message: String) {
    Alerter.create(this)
        .setTitle("")
        .setText(message)
        .setTitleAppearance(R.style.AlertTextAppearance_Title_1)
        .setTextAppearance(R.style.AlertTextAppearance_Text_1)
        .setBackgroundColorRes(R.color.red) // or setBackgroundColorInt(Color.CYAN)
        .setIcon(R.drawable.svgexport_10__10_)
        .setIconColorFilter(0) //
        .show()
}


fun Context.getActivity(): AppCompatActivity? = when (this) {
    is AppCompatActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

fun <T> (() -> T).withDelay(delay: Long = 250L) {
    Handler(Looper.getMainLooper()).postDelayed({ this.invoke() }, delay)
}

fun Any.log(tag: String = "") {
    if (BuildConfig.DEBUG) {
        if (tag == "") {
            Log.d("", this.toString())
        } else {
            Log.d("$tag", this.toString())

        }
    }
}

fun <E> handleSuccess(data: E?, message: String? = ""): MutableStateFlow<Resource<E>> {
    return MutableStateFlow(
        Resource.success(
            data,
            message = message
        )
    )
}

fun <E> handleExceptions(errorBase: Exception): MutableStateFlow<Resource<E>> {
    return MutableStateFlow(
        Resource.error(
            null,
            errorBase.message,
            null
        )
    )
}

fun <E> handleExceptions(errorBase: ErrorBase): MutableStateFlow<Resource<E>> {
    return MutableStateFlow(
        Resource.error(
            null,
            errorBase.responseMessage,
            errorBase.errors
        )
    )
}

@Composable
fun <T> rememberFlow(
    flow: Flow<T>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
): Flow<T> {
    return remember(key1 = flow, key2 = lifecycleOwner) {
        flow.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }
}

fun Float.dpToPx(context: Context): Float {
    return this * context.resources.displayMetrics.density

}

fun Context.showAlertDialogNotDismissed(
    title: String, content: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onPositiveClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(content)
        .setPositiveButton(positiveButtonText) { dialog, _ ->
            onPositiveClick()
        }.setNegativeButton(negativeButtonText) { dialog, _ ->
        }
        .setCancelable(false).show()
}

fun bindHtml(html: String): String {
    val str =
        Html.fromHtml(
            html,
            Html.FROM_HTML_MODE_LEGACY
        )
    return str.toString()
}


fun receiveToken() {
    FirebaseMessaging.getInstance().token
        .addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Token =>", "getInstanceId failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            LocalData?.fcmToken = token
            LocalData?.fcmToken?.log("Token")
        })
}


@Composable
inline fun <reified T : ViewModel> NavController.getSharedViewModel(
    backStackEntry: NavBackStackEntry?,
    route: String = "Parent"
): T {
    val mainEntity = remember(backStackEntry) {
        this.getBackStackEntry(route)
    }
    return hiltViewModel(mainEntity)
}


@Composable
fun Int.getResource(): Painter {
    return painterResource(id = this)
}


fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

fun Activity.applyAppLanguage() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSystemService(
            LocaleManager::class.java
        ).applicationLocales =
            LocaleList(Locale.forLanguageTag(Configurations.appLocal.value))
    }
}

fun FragmentActivity.requestNotificationPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.permissionsBuilder(
            Manifest.permission.POST_NOTIFICATIONS
        ).build().send()
    }
}

fun forceUpdateApp(context: Context) {
    context.showAlertDialogNotDismissed(
        context.getString(R.string.app_update),
        context.getString(R.string.app_update_lbl),
        context.getString(R.string.update),
        "", {
            context.launchAppInStore()
        }) {}
}

fun disableApp(context: Context) {
    context.showAlertDialogNotDismissed(
        context.getString(R.string.app_disabled),
        context.getString(R.string.app_disabled_lbl),
        "",
        "", {}) {}
}

fun Activity.hasIntentWithData(): Boolean {
    return intent != null && intent.extras != null && intent.extras!!.containsKey("action")
}
