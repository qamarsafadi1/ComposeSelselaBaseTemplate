package com.qamar.composetemplate.util

import android.content.Context
import com.qamar.composetemplate.util.FieldsValidateConstraintsClass.isEmailValidPattern
import com.qamar.composetemplate.util.FieldsValidateConstraintsClass.isPasswordValidLength
import com.qamar.composetemplate.util.FieldsValidateConstraintsClass.isPasswordsMatches
import com.qamar.composetemplate.util.FieldsValidateConstraintsClass.isPhoneNumberOnly
import com.qamar.composetemplate.util.FieldsValidateConstraintsClass.isPhoneNumberValidLength
import com.selsela.cpapp.R

fun String.validateEmail(mContext: Context,fieldName: String): String {
    var message = ""
    if (this == "") {
        message = mContext.getString(R.string.valid_required2) + " " + fieldName
    } else if (!isEmailValidPattern(this)) {
        message = mContext.getString(R.string.warning_email_pattern)
    }
    return message
}

fun String.validateRequired(mContext: Context,fieldName: String): String {
    var message = ""
    if (this == "") {
        message = mContext.getString(R.string.valid_required2) + " " + fieldName
    }
    return message
}


fun String.validatePhone(mContext: Context,fieldName: String): String {
    var message = ""
    if (this == "") {
        message = mContext.getString(R.string.valid_required2) + " " + fieldName
    } else if (!isPhoneNumberOnly(this)) {
        message = mContext.getString(R.string.warning_phone_pattern)
    } else if (!isPhoneNumberValidLength(this)) {
        message = mContext.getString(R.string.warning_phone_length_pattern)
    } else if (this.startsWith("0", ignoreCase = true)) {
        message = mContext.getString(R.string.not_start_with_zero)
    }
    return message
}

fun String?.validatePhone(mContext: Context): String {
    var message = ""
    if (this == "" || this == null) {
        message = mContext.getString(R.string.valid_required)
    } else if (!isPhoneNumberOnly(this)) {
        message = mContext.getString(R.string.warning_phone_pattern)
    } else if (!isPhoneNumberValidLength(this)) {
        message = mContext.getString(R.string.warning_phone_length_pattern)
    }
    return message
}
fun String?.validatePhone(): Int {
    var message =  R.string.empty_lbl
    if (this == "" || this == null) {
        message = R.string.valid_required
    } else if (!isPhoneNumberOnly(this)) {
        message = R.string.warning_phone_pattern
    } else if (!isPhoneNumberValidLength(this)) {
        message = R.string.warning_phone_length_pattern
    }
    return message
}

fun String.validateEmail(): Int {
    var message =  R.string.empty_lbl
    if (this == "") {
        message = R.string.valid_required
    } else if (!isEmailValidPattern(this)) {
        message = R.string.warning_email_pattern
    }
    return message
}

fun String.validateRequired(): Int {
    var message = R.string.empty_lbl
    if (this == "") {
        message = R.string.valid_required
    }
    return message
}
fun String?.validatePassword(mContext: Context): String {
    var message = ""
    if (this == "" || this == null) {
        message = mContext.getString(R.string.valid_required)
    } else if (!isPasswordValidLength(this)) {
        message = mContext.getString(R.string.warning_password_length)
    }
    return message
}

fun String?.validateRePassword(mContext: Context, password: String): String {
    var message = ""
    if (this == "" || this == null) {
        message = mContext.getString(R.string.valid_required)
    } else if (!isPasswordsMatches(this, password)) {
        message = mContext.getString(R.string.warning_password_not_match)
    }
    return message
}