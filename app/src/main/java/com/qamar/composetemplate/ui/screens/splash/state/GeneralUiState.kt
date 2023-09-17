package com.qamar.composetemplate.ui.screens.splash.state

import com.qamar.composetemplate.data.remote.config.model.config.Configurations
import com.qamar.composetemplate.util.networking.model.ErrorsData
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

sealed interface GeneralUiState {
    data object Loading : GeneralUiState
    data object IDLE : GeneralUiState

    data class Success(
        val configurations: Configurations? = Configurations.Config,
        val onSuccess: StateEvent = consumed,
    ) : GeneralUiState

    data class Failed(
        val onFailure: StateEventWithContent<ErrorsData?> = consumed(),
    ) : GeneralUiState

    fun onReset(): GeneralUiState {
        val uiState = Success(
            onSuccess = consumed
        )
        Failed(
            onFailure = consumed()
        )
        return uiState
    }
}