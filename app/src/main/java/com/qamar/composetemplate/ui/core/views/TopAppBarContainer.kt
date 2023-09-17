package com.qamar.composetemplate.ui.core.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.qamar.composetemplate.data.remote.auth.model.user.User
import com.qamar.composetemplate.ui.core.component.TopBar
import com.selsela.cpapp.R

@Composable
fun TopAppBarContainer(
    title: Any,
    navigationIcon: Int?,
    onNavigationIconClick: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable () -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        content()
        TopBar(
            title = title, navigationIcon =
            if (navigationIcon == R.drawable.notification) {
                if (User.isLoggedIn.value)
                    navigationIcon
                else null
            } else
                navigationIcon,
            onNavigationIconClick = onNavigationIconClick, actions = actions
        )
    }
}