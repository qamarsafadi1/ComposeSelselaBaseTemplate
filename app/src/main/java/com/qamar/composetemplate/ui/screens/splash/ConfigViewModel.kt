package com.qamar.composetemplate.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qamar.composetemplate.data.remote.config.repository.ConfigurationsRepository
import com.qamar.composetemplate.ui.screens.splash.state.GeneralUiState
import com.qamar.composetemplate.util.networking.model.ErrorsData
import com.qamar.composetemplate.util.networking.model.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val repository: ConfigurationsRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<GeneralUiState> = MutableStateFlow(GeneralUiState.Success())
    val uiState: StateFlow<GeneralUiState> = _uiState.asStateFlow()

    init {
        getConfig()
    }
    private fun getConfigurations() {
        viewModelScope.launch {
            _uiState.update {
                GeneralUiState.Loading
            }
            repository.getConfigurations()
                .collect { result ->
                    val authUiState = when (result.status) {
                        Status.SUCCESS -> {
                            GeneralUiState.Success(
                                onSuccess = triggered,
                                configurations = result.data
                            )
                        }

                        Status.LOADING ->
                            GeneralUiState.Loading

                        Status.ERROR -> GeneralUiState.Failed(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                    result.statusCode
                                )
                            ),
                        )
                    }
                    _uiState.update {
                        authUiState
                    }
                }
        }

    }

    private fun getConfig() {
        viewModelScope.launch {
            getConfigurations()
            repository.getPaymentsType()
        }
    }

    fun onReset() {
        _uiState.update {
            it.onReset()
        }
    }
}