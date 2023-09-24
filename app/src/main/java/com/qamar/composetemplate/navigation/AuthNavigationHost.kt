package com.qamar.composetemplate.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.qamar.composetemplate.ui.screens.auth.AuthViewModel
import com.qamar.composetemplate.ui.screens.auth.ForgetPasswordDialog
import com.qamar.composetemplate.ui.screens.auth.LoginScreen
import com.qamar.composetemplate.ui.screens.auth.RegisterScreen
import com.qamar.composetemplate.ui.screens.auth.ResetPasswordDialog
import com.qamar.composetemplate.ui.screens.auth.VerifySheet
import com.qamar.composetemplate.ui.screens.auth.events.LoginEvent

/**
 * Destinations
 */

sealed class AuthHostDestination(val route: String) {
    object AuthArgs {
        const val goToRootArgs = "goToRootArgs"
    }

    data object Login :
        AuthHostDestination("LOGIN_SCREEN/{${AuthArgs.goToRootArgs}}") {
        fun createRoute(goToRoot: Boolean? = true): String {
            return "LOGIN_SCREEN/$goToRoot"
        }
    }

    data object Register : AuthHostDestination("REGISTER_SCREEN")
    data object VerifyCode : AuthHostDestination("VERIFY_SCREEN")
}

/**
 * Nav Graph Builder
 */

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.authNavigationHost(navController: NavHostController) {

    navigation(
        startDestination = AuthHostDestination.Login.createRoute(),
        route = NavigationGraphs.AuthNavGraph
    ) {

        /**
         * Screens
         */
        composable(
            route = AuthHostDestination.Login.route,
            arguments = listOf(navArgument(AuthHostDestination.AuthArgs.goToRootArgs) {
                type = NavType.BoolType
            })
        ) { it ->
            val viewModel = hiltViewModel<AuthViewModel>()
            val goToRoot =
                it.arguments?.getBoolean(AuthHostDestination.AuthArgs.goToRootArgs, true) ?: true

            Box(modifier = Modifier.fillMaxSize()) {
                LoginScreen(goToRoot) {
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
            route = AuthHostDestination.Register.route,
        ) { _ ->
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

        bottomSheet(
            route = AuthHostDestination.VerifyCode.route,
            ) {
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
}


/**
 * Navigation Actions
 */


fun NavHostController.openVerifySheet() {
    navigate(AuthHostDestination.VerifyCode.route) {
        popUpTo(AuthHostDestination.Login.route) {
            inclusive = true
        }
    }
}


fun NavHostController.navigateToRegister() {
    navigate(AuthHostDestination.Register.route)
}

fun NavHostController.navigateToLogin() {
    navigate(NavigationGraphs.AuthNavGraph)
}

fun NavHostController.goToHome() {
    this.popBackStack(
        this.previousBackStackEntry?.destination?.route ?: "Parent", false
    )
}




