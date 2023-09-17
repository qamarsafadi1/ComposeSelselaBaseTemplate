package com.qamar.composetemplate.util

import androidx.appcompat.app.AppCompatActivity
import com.qamar.composetemplate.util.networking.model.Errors
import com.selsela.cpapp.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

open class Common {
    companion object {

        fun handleErrors(
            message: String?, errors: List<Errors>?, context: AppCompatActivity?
        ) {
            when {
                errors != null -> {
                    if (errors.isNotEmpty())
                        context?.showErrorTop(errors[0].error)
                    else context?.showErrorTop(message ?: context.getString(R.string._msg_failed))
                }

                message != null -> context?.showErrorTop(message)
                else -> context?.showErrorTop(context.getString(R.string._msg_failed))
            }
        }

        fun createPartFromString(descriptionString: String?): RequestBody {
            return descriptionString?.toRequestBody(MultipartBody.FORM) ?: "".toRequestBody(
                MultipartBody.FORM
            )
        }

        fun createImageFile(image: File, key: String): MultipartBody.Part {
            val requestBody = image.asRequestBody("image/*".toMediaTypeOrNull())
            return MultipartBody.Part.createFormData(key, image.name ?: "123", requestBody)
        }

    }
}