package com.qamar.composetemplate.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.qamar.composetemplate.ui.screens.auth.AuthViewModel
import com.qamar.composetemplate.ui.screens.auth.ForgetPasswordDialog
import com.qamar.composetemplate.ui.screens.auth.LoginScreen
import com.qamar.composetemplate.ui.screens.auth.RegisterScreen
import com.qamar.composetemplate.ui.screens.auth.ResetPasswordDialog
import com.qamar.composetemplate.ui.screens.auth.VerifySheet
import com.qamar.composetemplate.ui.screens.auth.events.LoginEvent


val LOGIN_SCREEN = AppDestinations.Login.name
val REGISTER_SCREEN = AppDestinations.Register.name
val VERIFY_SHEET = AppDestinations.VerifyCodeSheet.name

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.authNavigationHost(navController: NavHostController) {

    /**
     * Screens
     */
    composable(
        route = LOGIN_SCREEN,
    ) { it ->
        val viewModel = hiltViewModel<AuthViewModel>()
        Box(modifier = Modifier.fillMaxSize()) {
            LoginScreen {
                when (it) {
                    is LoginEvent.GoToCategories -> {
                        navController.navigateToCategories()
                    }

                    is LoginEvent.LoggedIn -> navController.navigateUp()

                    is LoginEvent.GoToForgetPassword -> {
                        viewModel.openForgetPasswordDialog.value = true
                    }

                    is LoginEvent.GoToRegister -> {
                        navController.navigateToRegister()
                    }

                    else -> {}
                }
            }

            if (viewModel.openForgetPasswordDialog.value) {
                ForgetPasswordDialog(viewModel, onNavigate = {
                    viewModel.openForgetPasswordDialog.value = false
                }) {
                    viewModel.openForgetPasswordDialog.value = false
                    viewModel.openResetPasswordDialog.value = true
                }
            }
            if (viewModel.openResetPasswordDialog.value) {
                ResetPasswordDialog(viewModel, onNavigate = {
                    viewModel.openResetPasswordDialog.value = false
                }) {
                    viewModel.openResetPasswordDialog.value = false
                    navController.navigateToCategories()
                }
            }
        }
    }

    composable(
        route = REGISTER_SCREEN,
    ) { it ->
        Box(modifier = Modifier.fillMaxSize()) {
            RegisterScreen {
                when (it) {
                    is LoginEvent.GoToCategories -> {
                        navController.navigateUp()
                    }

                    is LoginEvent.GoToVerify -> {
                        navController.openVerifySheet()
                    }

                    is LoginEvent.GoToLogin -> {
                        navController.navigateToLogin()
                    }

                    else -> {}
                }
            }
        }
    }

    /**
     * Sheets
     */

    bottomSheet(VERIFY_SHEET) {
        val viewModel = hiltViewModel<AuthViewModel>()
        VerifySheet(
            viewModel,
            goToHome = {
                if (it) {
                    navController.navigateToCategories()
                } else {
                    navController.goToHome()
                }
            }
        )
    }

}


/**
 * Navigation Actions
 */


fun NavHostController.openVerifySheet() {
    navigate(VERIFY_SHEET) {
        popUpTo(LOGIN_SCREEN) {
            inclusive = true
        }
    }
}

fun NavHostController.navigateToRegister() {
    navigate(REGISTER_SCREEN)
}

fun NavHostController.navigateToLogin() {
    navigate(LOGIN_SCREEN)
}

fun NavHostController.goToHome() {
    this.popBackStack(
        this.previousBackStackEntry?.destination?.route ?: "Parent", false
    )
}




