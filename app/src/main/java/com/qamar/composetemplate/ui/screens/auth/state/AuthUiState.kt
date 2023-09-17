package com.qamar.composetemplate.ui.screens.auth.state

import android.annotation.SuppressLint
import com.qamar.composetemplate.data.remote.auth.model.user.User
import com.qamar.composetemplate.util.networking.model.ErrorsData
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

sealed interface AuthUiState {
    data object Loading : AuthUiState
    data object IDLE : AuthUiState

    data class Success(
        val user: User? = User.currentUser,
        var onVerify: StateEventWithContent<User?> = consumed(),
        var onLogin: StateEventWithContent<User?> = consumed(),
        val onResend: StateEventWithContent<String> = consumed(),
        ) : AuthUiState

    data class Failed(
        val onFailure: StateEventWithContent<ErrorsData?> = consumed(),
    ) : AuthUiState

    @SuppressLint("SuspiciousIndentation")
    fun onReset(): AuthUiState {
      val uiState = Success(
          onVerify = consumed(),
          onLogin = consumed(),
          onResend = consumed(),
      )
        Failed(
            onFailure = consumed()
        )
      return uiState
    }

}
