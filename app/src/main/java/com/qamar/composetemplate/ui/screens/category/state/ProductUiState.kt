package com.qamar.composetemplate.ui.screens.category.state

import com.qamar.composetemplate.data.remote.categories.model.category.Children
import com.qamar.composetemplate.data.remote.categories.model.products.Product
import com.qamar.composetemplate.util.networking.model.ErrorsData
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed interface ProductUiState {
    data object Loading : ProductUiState
    data object IDLE : ProductUiState
    data object Empty : ProductUiState
    data object Paging : ProductUiState

    data class Success(
        val products: ImmutableList<Product>? = persistentListOf(),
        val categories : ImmutableList<Children>? = persistentListOf(),
        val subCategories : ImmutableList<Children>? = persistentListOf(),
        var onHomeLoaded: StateEvent = consumed,
        ) : ProductUiState

    data class Failed(
        val onFailure: StateEventWithContent<ErrorsData?> = consumed(),
    ) : ProductUiState
    fun onReset(): ProductUiState {
        val uiState = Success()
        Failed(
            onFailure = consumed()
        )
        return uiState
    }
}