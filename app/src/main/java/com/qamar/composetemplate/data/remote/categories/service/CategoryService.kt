package com.qamar.composetemplate.data.remote.categories.service

import com.qamar.composetemplate.data.remote.categories.model.category.CategoryResponse
import com.qamar.composetemplate.data.remote.categories.model.products.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryService {

    @GET("app/categories")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("app/category/products")
    suspend fun getCategoryProducts(
        @Query("page") page: Int,
        @Query("category_id") categoryId: Int? = null
    ): Response<ProductsResponse>
}