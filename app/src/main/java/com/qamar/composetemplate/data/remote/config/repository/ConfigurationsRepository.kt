package com.qamar.composetemplate.data.remote.config.repository

import com.google.gson.Gson
import com.qamar.composetemplate.data.remote.config.model.config.Configurations
import com.qamar.composetemplate.data.remote.config.model.payment.Payment
import com.qamar.composetemplate.data.remote.config.service.ConfigServiceApi
import com.qamar.composetemplate.util.handleExceptions
import com.qamar.composetemplate.util.handleSuccess
import com.qamar.composetemplate.util.networking.model.ErrorBase
import com.qamar.composetemplate.util.networking.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConfigurationsRepository @Inject constructor(
    private val api: ConfigServiceApi
) {
    suspend fun getConfigurations(): Flow<Resource<Configurations>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<Configurations>> = try {
            val response = api.getConfigurations()
            if (response.isSuccessful) {
                Configurations.Config = response.body()?.configurations
                handleSuccess(
                    response.body()?.configurations,
                    response.body()?.responseMessage ?: response.message()
                )
            } else {
                val gson = Gson()
                val errorBase = gson.fromJson(response.errorBody()?.string(), ErrorBase::class.java)
                handleExceptions(errorBase)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            handleExceptions(e)
        }
        data
    }


    suspend fun getPaymentsType(): Flow<Resource<List<Payment>>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<List<Payment>>> = try {
            val response = api.getPaymentsType()
            if (response.isSuccessful) {
                Configurations.payments = response.body()?.payments ?: listOf()
                handleSuccess(
                    response.body()?.payments,
                    response.body()?.responseMessage ?: response.message()
                )
            } else {
                val gson = Gson()
                val errorBase = gson.fromJson(response.errorBody()?.string(), ErrorBase::class.java)
                handleExceptions(errorBase)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            handleExceptions(e)
        }
        data
    }

}