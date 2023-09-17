package com.qamar.composetemplate.util

import com.qamar.composetemplate.ui.theme.GrayValidation

data class InputWrapper(
    var inputValue: String = "",
    var borderColor: String = GrayValidation,
    var validationMessage: String? = null,
)