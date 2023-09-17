package com.qamar.composetemplate.ui.screens.category.state

import com.qamar.composetemplate.data.remote.categories.model.category.Category
import com.qamar.composetemplate.util.networking.model.ErrorsData
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed interface CategoryUiState {
    object Loading : CategoryUiState
    object IDLE : CategoryUiState

    data class Success(
        val categories: ImmutableList<Category>? = persistentListOf(),
    ) : CategoryUiState

    data class Failed(
        val onFailure: StateEventWithContent<ErrorsData?> = consumed(),
    ) : CategoryUiState
    fun onReset(): CategoryUiState {
        val uiState = Success()
        Failed(
            onFailure = consumed()
        )
        return uiState
    }
}