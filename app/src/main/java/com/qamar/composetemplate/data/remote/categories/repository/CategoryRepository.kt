package com.qamar.composetemplate.data.remote.categories.repository

import com.google.gson.Gson
import com.qamar.composetemplate.data.remote.categories.model.category.Category
import com.qamar.composetemplate.data.remote.categories.model.products.ProductsResponse
import com.qamar.composetemplate.data.remote.categories.service.CategoryService
import com.qamar.composetemplate.util.handleExceptions
import com.qamar.composetemplate.util.handleSuccess
import com.qamar.composetemplate.util.networking.model.ErrorBase
import com.qamar.composetemplate.util.networking.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val api: CategoryService,
) {

    suspend fun getCategories(): Flow<Resource<List<Category>>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<List<Category>>> = try {
            val response = api.getCategories()
            if (response.isSuccessful) {
                handleSuccess(
                    response.body()?.categories,
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

    suspend fun getCategoryProducts(
        page: Int,
        categoryID: Int? = 0,
    ): Flow<Resource<ProductsResponse>> = withContext(Dispatchers.IO) {
        val data: Flow<Resource<ProductsResponse>> = try {
            val response =
                api.getCategoryProducts(
                    page,
                    categoryID
                )
            if (response.isSuccessful) {
                handleSuccess(
                    response.body(),
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