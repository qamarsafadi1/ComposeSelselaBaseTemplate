package com.qamar.composetemplate.ui.screens.auth

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.qamar.composetemplate.data.remote.auth.model.user.User
import com.qamar.composetemplate.data.remote.auth.repository.AuthRepository
import com.qamar.composetemplate.ui.screens.auth.events.AuthForm
import com.qamar.composetemplate.ui.screens.auth.events.AuthFormEvents
import com.qamar.composetemplate.ui.screens.auth.state.AuthUiState
import com.qamar.composetemplate.util.Constants.NOT_VERIFIED
import com.qamar.composetemplate.util.GeocodingUtils
import com.qamar.composetemplate.util.getActivity
import com.qamar.composetemplate.util.log
import com.qamar.composetemplate.util.networking.model.ErrorsData
import com.qamar.composetemplate.util.networking.model.Status
import com.qamar.composetemplate.util.showErrorTop
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    application: Application
) : ViewModel() {

    var form: AuthForm by mutableStateOf(AuthForm(application))
        private set

    var openForgetPasswordDialog = mutableStateOf(false)
    var openResetPasswordDialog = mutableStateOf(false)


    fun onAuthEvent(event: AuthFormEvents) {
        form = when (event) {
            is AuthFormEvents.OnEmailChanged -> {
                event.value.let {
                    form.validateEmail(it)
                }
            }

            is AuthFormEvents.OnMobileChanged -> {
                event.value.let {
                    form.validateMobile(it)
                }
            }

            is AuthFormEvents.OnPasswordChanged -> {
                event.value.let {
                    form.validatePassword(it)
                }
            }

            is AuthFormEvents.OnNewPasswordChanged -> {
                event.value.let {
                    form.validateNewPassword(it)
                }
            }

            is AuthFormEvents.OnConfirmPasswordChanged -> {
                event.value.let {
                    form.validateRePassword(it)
                }
            }

            is AuthFormEvents.OnOTPChanged -> event.value.let {
                form.validateCode(it, event.isOtpFilled)
            }

            is AuthFormEvents.OnNameChanged -> event.value.let {
                form.validateName(it)
            }

            is AuthFormEvents.OnImageChanged -> event.value.let {
                form.onAvatarChange(it)
            }
        }
    }


    /**
     * State Flows
     */
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.IDLE)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()


    /**
     * API Requests
     */

    fun login() {
        viewModelScope.launch {
            _uiState.update {
                AuthUiState.Loading
            }
            repository.auth(form.mobile.inputValue, form.email.inputValue, form.name.inputValue)
                .collect { result ->
                    val authUiState = when (result.status) {
                        Status.SUCCESS -> {
                            if (result.data?.status == NOT_VERIFIED) {
                                AuthUiState.Success(
                                    user = result.data,
                                    onVerify = triggered(result.data)
                                )
                            } else {
                                result.data.let {
                                    AuthUiState.Success(
                                        user = it,
                                        onLogin = triggered(result.data)
                                    )
                                }
                            }
                        }

                        Status.LOADING ->
                            AuthUiState.Loading

                        Status.ERROR -> AuthUiState.Failed(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                    result.statusCode
                                )
                            )
                        )
                    }
                    _uiState.update {
                        authUiState
                    }
                }
        }
    }

    fun verifyCode() {
        viewModelScope.launch {
            _uiState.update {
                AuthUiState.Loading
            }
            repository.verifyCode(
                form.mobile.inputValue,
                form.code.inputValue
            )
                .collect { result ->
                    val authUiState = when (result.status) {
                        Status.SUCCESS -> {
                            result.data.let {
                                AuthUiState.Success(
                                    user = it,
                                    onLogin = triggered(result.data)
                                )
                            }
                        }

                        Status.LOADING ->
                            AuthUiState.Loading

                        Status.ERROR -> AuthUiState.Failed(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                    result.statusCode
                                )
                            )
                        )
                    }
                    _uiState.update {
                        authUiState
                    }
                }
        }
    }

    fun resendCode() {
        viewModelScope.launch {
            _uiState.update {
                AuthUiState.Loading
            }
            repository.resendCode(User.currentUser?.mobile ?: "")
                .collect { result ->
                    val authUiState = when (result.status) {
                        Status.SUCCESS -> {
                            result.data.let {
                                AuthUiState.Success(
                                    user = it,
                                    onResend = triggered(result.message ?: "")
                                )
                            }
                        }

                        Status.LOADING ->
                            AuthUiState.Loading

                        Status.ERROR -> AuthUiState.Failed(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                    result.statusCode
                                )
                            )
                        )
                    }
                    _uiState.update {
                        authUiState
                    }
                }
        }
    }

    fun forgetPassword() {
        viewModelScope.launch {
            _uiState.update {
                AuthUiState.Loading
            }
            repository.forgetPassword(
                form.mobile.inputValue,
            )
                .collect { result ->
                    val authUiState = when (result.status) {
                        Status.SUCCESS -> {
                            result.data.let {
                                AuthUiState.Success(
                                    user = it,
                                    onVerify = triggered(result.data)
                                )
                            }
                        }

                        Status.LOADING ->
                            AuthUiState.Loading

                        Status.ERROR -> AuthUiState.Failed(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                    result.statusCode
                                )
                            )
                        )
                    }
                    _uiState.update {
                        authUiState
                    }
                }
        }
    }

    fun resetPassword() {
        viewModelScope.launch {
            _uiState.update {
                AuthUiState.Loading
            }
            repository.resetPassword(
                form.mobile.inputValue,
                form.code.inputValue,
                form.password.inputValue,
            )
                .collect { result ->
                    val authUiState = when (result.status) {
                        Status.SUCCESS -> {
                            result.data.let {
                                AuthUiState.Success(
                                    user = it,
                                    onLogin = triggered(result.data)
                                )
                            }
                        }

                        Status.LOADING ->
                            AuthUiState.Loading

                        Status.ERROR -> AuthUiState.Failed(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                    result.statusCode
                                )
                            )
                        )
                    }
                    _uiState.update {
                        authUiState
                    }
                }
        }
    }

    fun updateSelectedAddress(latLng: LatLng) {
        val currentLocation = latLng
        viewModelScope.launch {
            val geocodingUtils = GeocodingUtils(currentLocation)
            geocodingUtils.reverseGeocode()
            val countryName = geocodingUtils.getCountryName()
            if (countryName != null) {
                //  ifFromNationalQuery.value = false
                //  country.value = countryName
            } else {
                // country.value = ""
                println("Failed to get country name.")
            }

            val cityName = geocodingUtils.getCityName()
            if (cityName != null) {
                //   city.value = cityName
                //     selectedCityId.value = getCities().find { city.value.contains(it.name) }?.id ?: 0
                //   selectedCityIndex.intValue = getCities().indexOfLast { city.value.contains(it.name) }
                //   onLocationInfoFormEvent(LocationInfoFormEvents.OnCitySelected(selectedCityId.value))
            } else {
                println("Failed to get city name.")
            }

            val neighborhood = geocodingUtils.getNeighborhood()
            if (neighborhood != null) {
                //    area.value = neighborhood
                //    onLocationInfoFormEvent(LocationInfoFormEvents.OnAreaEntered(area.value))
            } else {
                println("Failed to get neighborhood.")
            }

            val street = geocodingUtils.getStreet()
            if (street != null) {
                //   onLocationInfoFormEvent(LocationInfoFormEvents.OnStreetEntered(street))
            } else {
                println("Failed to get street.")
            }
        }
    }

    fun queryForNationalNumber(nationalNumber: String, context: Context) {
        viewModelScope.launch {
            val currentLocation = LatLng(0.0, 0.0)
            val geocodingUtils = GeocodingUtils(currentLocation)
            geocodingUtils.nationalNumberQuery(nationalNumber)
            if (geocodingUtils.response.results.isEmpty()) {
                context.getActivity()?.showErrorTop(
                    "no address found"
                )
            } else {
                val countryName = geocodingUtils.getCountryName()
                if (countryName != null) {
                    //   country.value = countryName
                } else {
                    //    country.value = ""
                    println("Failed to get country name.")
                }
                val latLng = geocodingUtils.getLatLng()
                if (latLng != null) {
                    //   ifFromNationalQuery.value = true
                    //  currentLocation.value = latLng
                    //   checkIfLocationWithinBoundaries()
                    latLng.log("latLnglatLng")
                } else {
                    //currentLocation.value = currentLocation.value
                    println("Failed to get latLng.")
                }

                val cityName = geocodingUtils.getCityName()
                if (cityName != null) {
                    //   city.value = cityName
                    //   selectedCityId.value =
                    //     getCities().find { city.value.contains(it.name) }?.id ?: 0
                    //  selectedCityIndex.intValue =
                    //       getCities().indexOfLast { city.value.contains(it.name) }
                    //  onLocationInfoFormEvent(LocationInfoFormEvents.OnCitySelected(selectedCityId.value))
                } else {
                    println("Failed to get city name.")
                }

                val neighborhood = geocodingUtils.getNeighborhood()
                if (neighborhood != null) {
                    //   area.value = neighborhood
                    //    onLocationInfoFormEvent(LocationInfoFormEvents.OnAreaEntered(area.value))
                } else {
                    println("Failed to get neighborhood.")
                }

                val street = geocodingUtils.getStreet()
                if (street != null) {
                    //  onLocationInfoFormEvent(LocationInfoFormEvents.OnStreetEntered(street))
                } else {
                    println("Failed to get street.")
                }
            }
        }
    }


    /**
     * Reset handlers when single event
     */

    fun onSuccess() {
        _uiState.update {
            it.onReset()
        }
    }

    fun onVerify() {
        _uiState.update {
            it.onReset()
        }

    }

}