package com.qamar.composetemplate.di

import android.app.Application
import android.content.SharedPreferences
import com.qamar.composetemplate.data.local.PreferenceHelper
import com.qamar.composetemplate.data.remote.auth.service.AuthService
import com.qamar.composetemplate.data.remote.categories.service.CategoryService
import com.qamar.composetemplate.data.remote.config.service.ConfigServiceApi
import com.qamar.composetemplate.util.networking.HeaderInterceptor
import com.qamar.composetemplate.util.networking.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        retrofitBuilder: RetrofitBuilder,
        headerInterceptor: HeaderInterceptor
    ): Retrofit =
        retrofitBuilder
            .addInterceptors(headerInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideConfigApi(retrofit: Retrofit): ConfigServiceApi =
        retrofit.create(ConfigServiceApi::class.java)

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthService = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideCategoriesApi(retrofit: Retrofit): CategoryService = retrofit.create(CategoryService::class.java)

    @Provides
    @Singleton
    fun provideLocalPreference(application: Application): SharedPreferences =
        PreferenceHelper.customPreference(application.applicationContext, "APP_PREFERENCE")

}