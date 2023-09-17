package com.qamar.composetemplate.data.remote.auth.service

import com.qamar.composetemplate.data.remote.auth.model.user.AuthResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface AuthService {

    @POST("user/login_register")
    @JvmSuppressWildcards
    @FormUrlEncoded
    suspend fun auth(
        @FieldMap
        body: Map<String, Any>
    ): Response<AuthResponse>

    @POST("user/update_profile")
    @JvmSuppressWildcards
    @Multipart
    suspend fun updateProfile(
        @Part
        avatar: MultipartBody.Part?,
        @PartMap
        body: HashMap<String, RequestBody>
    ): Response<AuthResponse>

    @POST("user/verify_code")
    @JvmSuppressWildcards
    @FormUrlEncoded
    suspend fun verifyCode(
        @FieldMap
        body: Map<String, Any>
    ): Response<AuthResponse>

    @POST("user/resend_verify_code")
    @JvmSuppressWildcards
    @FormUrlEncoded
    suspend fun resendCode(
        @FieldMap
        body: Map<String, Any>
    ): Response<AuthResponse>

    @POST("user/forget_password")
    @JvmSuppressWildcards
    @FormUrlEncoded
    suspend fun forgetPassword(
        @FieldMap
        body: Map<String, Any>
    ): Response<AuthResponse>

}