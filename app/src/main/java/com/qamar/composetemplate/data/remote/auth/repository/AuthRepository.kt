package com.qamar.composetemplate.data.remote.auth.repository

import androidx.annotation.Keep
import com.google.gson.Gson
import com.qamar.composetemplate.data.remote.auth.model.user.AuthResponse
import com.qamar.composetemplate.data.remote.auth.model.user.User
import com.qamar.composetemplate.data.remote.auth.service.AuthService
import com.qamar.composetemplate.util.Common
import com.qamar.composetemplate.util.Constants.NOT_VERIFIED
import com.qamar.composetemplate.util.handleExceptions
import com.qamar.composetemplate.util.handleSuccess
import com.qamar.composetemplate.util.log
import com.qamar.composetemplate.util.networking.model.ErrorBase
import com.qamar.composetemplate.util.networking.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

@Keep
class AuthRepository @Inject constructor(
    private val api: AuthService
) {
    suspend fun auth(
        mobile: String,
        email: String? = "",
        name: String,
    ): Flow<Resource<User>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<User>> = try {
            val body = HashMap<String, Any>()
            if (mobile.isNotEmpty())
                body["mobile"] = mobile
            else body["email"] = email ?: ""
            body["name"] = name
            body["country_id"] = "1"
            val response = api.auth(body)
            if (response.isSuccessful) {
                User.currentUser = response.body()?.user
                if (response.body()?.user?.status != NOT_VERIFIED)
                    User.accessToken = response.body()?.user?.accessToken
                handleSuccess(
                    response.body()?.user,
                    response.body()?.responseMessage ?: response.message()
                )
            } else {
                if (response.code() == 451) {
                    val responseUser: AuthResponse? = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        AuthResponse::class.java
                    )
                    User.currentUser = responseUser?.user
                    if (response.body()?.user?.status != NOT_VERIFIED)
                        User.accessToken = response.body()?.user?.accessToken
                    responseUser?.log("responseUser")
                    handleSuccess(
                        responseUser?.user,
                        responseUser?.responseMessage ?: response.message()
                    )
                } else {
                    val gson = Gson()
                    val errorBase =
                        gson.fromJson(response.errorBody()?.string(), ErrorBase::class.java)
                    handleExceptions(errorBase)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            handleExceptions(e)
        }
        data
    }

    suspend fun updateProfile(
        avatar: File?,
        name: String,
        mobile: String,
        email: String,
    ): Flow<Resource<User>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<User>> = try {
            val body = HashMap<String, String?>()
            body["name"] = name
            body["mobile"] = mobile
            body["country_id"] = "1"
            if (email.isNotBlank()) {
                body["email"] = email
            }
            val map: HashMap<String, RequestBody> = hashMapOf()
            body.forEach { entry ->
                " ${entry.key} = ${entry.value}"
                run { map[entry.key] = Common.createPartFromString(entry.value) }
            }
            val userImage: MultipartBody.Part? = if (avatar != null)
                Common.createImageFile(avatar, "avatar") else null
            val response = api.updateProfile(userImage, map)
            if (response.isSuccessful) {
                User.currentUser = response.body()?.user
                User.accessToken = response.body()?.user?.accessToken ?: ""
                handleSuccess(
                    response.body()?.user,
                    response.body()?.responseMessage ?: response.message()
                )
            } else {
                if (response.code() != 451) {
                    val gson = Gson()
                    val errorBase =
                        gson.fromJson(response.errorBody()?.string(), ErrorBase::class.java)
                    handleExceptions(errorBase)
                } else {
                    val responseUser: AuthResponse? = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        AuthResponse::class.java
                    )
                    User.currentUser = responseUser?.user
                    handleSuccess(
                        responseUser?.user, responseUser?.responseMessage ?: response.message()
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            handleExceptions(e)
        }
        data
    }

    suspend fun verifyCode(
        mobile: String,
        code: String,
    ): Flow<Resource<User>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<User>> = try {
            val body = HashMap<String, Any>()
            body["mobile"] = mobile
            body["code"] = code
            body["country_id"] = "1"
            val response = api.verifyCode(body)
            if (response.isSuccessful) {
                User.currentUser = response.body()?.user
                User.accessToken = response.body()?.user?.accessToken
                handleSuccess(
                    response.body()?.user,
                    response.body()?.responseMessage ?: response.message()
                )
            } else {
                if (response.code() == 451) {
                    val responseUser: AuthResponse? = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        AuthResponse::class.java
                    )
                    User.currentUser = responseUser?.user
                    handleSuccess(
                        response.body()?.user,
                        response.body()?.responseMessage ?: response.message()
                    )
                } else {
                    val gson = Gson()
                    val errorBase =
                        gson.fromJson(response.errorBody()?.string(), ErrorBase::class.java)
                    handleExceptions(errorBase)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            handleExceptions(e)
        }
        data
    }

    suspend fun resendCode(
        membershipNum: String,
    ): Flow<Resource<User>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<User>> = try {
            val body = HashMap<String, Any>()
            body["mobile"] = membershipNum
            val response = api.resendCode(body)
            if (response.isSuccessful) {
                User.currentUser = response.body()?.user
                User.accessToken = response.body()?.user?.accessToken
                handleSuccess(
                    response.body()?.user,
                    response.body()?.responseMessage ?: response.message()
                )
            } else {
                if (response.code() == 451) {
                    val responseUser: AuthResponse? = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        AuthResponse::class.java
                    )
                    User.currentUser = response.body()?.user

                    handleSuccess(
                        responseUser?.user,
                        responseUser?.responseMessage ?: response.message()
                    )
                } else {
                    val gson = Gson()
                    val errorBase =
                        gson.fromJson(response.errorBody()?.string(), ErrorBase::class.java)
                    handleExceptions(errorBase)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            handleExceptions(e)
        }
        data
    }

    suspend fun forgetPassword(
        mobile: String
    ): Flow<Resource<User>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<User>> = try {
            val body = HashMap<String, Any>()
            body["mobile"] = mobile
            body["country_id"] = "1"
            val response = api.forgetPassword(body)
            if (response.isSuccessful) {
                val user = User(
                    activationCode = response.body()?.returned.toString(),
                    mobile = mobile
                )
                User.currentUser = user
                handleSuccess(
                    user,
                    response.body()?.responseMessage ?: response.message()
                )
            } else {
                if (response.code() == 451) {
                    val responseUser: AuthResponse? = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        AuthResponse::class.java
                    )
                    User.currentUser = responseUser?.user
                    if (response.body()?.user?.status != NOT_VERIFIED)
                        User.accessToken = response.body()?.user?.accessToken
                    responseUser?.log("responseUser")
                    handleSuccess(
                        responseUser?.user,
                        responseUser?.responseMessage ?: response.message()
                    )
                } else {
                    val gson = Gson()
                    val errorBase =
                        gson.fromJson(response.errorBody()?.string(), ErrorBase::class.java)
                    handleExceptions(errorBase)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            handleExceptions(e)
        }
        data
    }

    suspend fun resetPassword(
        mobile: String,
        code: String,
        password: String,
    ): Flow<Resource<User>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<User>> = try {
            val body = HashMap<String, Any>()
            body["mobile"] = mobile
            body["code"] = code
            body["password"] = password
            body["country_id"] = "1"
            val response = api.forgetPassword(body)
            if (response.isSuccessful) {
                User.currentUser = response.body()?.user
                if (response.body()?.user?.status != NOT_VERIFIED)
                    User.accessToken = response.body()?.user?.accessToken
                handleSuccess(
                    response.body()?.user,
                    response.body()?.responseMessage ?: response.message()
                )
            } else {
                if (response.code() == 451) {
                    val responseUser: AuthResponse? = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        AuthResponse::class.java
                    )
                    User.currentUser = responseUser?.user
                    if (response.body()?.user?.status != NOT_VERIFIED)
                        User.accessToken = response.body()?.user?.accessToken
                    responseUser?.log("responseUser")
                    handleSuccess(
                        responseUser?.user,
                        responseUser?.responseMessage ?: response.message()
                    )
                } else {
                    val gson = Gson()
                    val errorBase =
                        gson.fromJson(response.errorBody()?.string(), ErrorBase::class.java)
                    handleExceptions(errorBase)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            handleExceptions(e)
        }
        data
    }

}