package com.qamar.composetemplate.data.remote.config.service


import com.qamar.composetemplate.data.remote.config.model.config.ConfigResponse
import com.qamar.composetemplate.data.remote.config.model.payment.PaymentResponse
import retrofit2.Response
import retrofit2.http.GET

interface ConfigServiceApi {

    @GET("app/get_configuration")
    suspend fun getConfigurations(): Response<ConfigResponse>

    @GET("app/get_payments")
    suspend fun getPaymentsType(): Response<PaymentResponse>

}