package com.qamar.composetemplate.di

import android.content.Context
import com.qamar.composetemplate.data.remote.auth.repository.AuthRepository
import com.qamar.composetemplate.data.remote.auth.service.AuthService
import com.qamar.composetemplate.data.remote.categories.repository.CategoryRepository
import com.qamar.composetemplate.data.remote.categories.service.CategoryService
import com.qamar.composetemplate.data.remote.config.repository.ConfigurationsRepository
import com.qamar.composetemplate.data.remote.config.service.ConfigServiceApi
import com.qamar.composetemplate.util.internet.NetworkConnectivityService
import com.qamar.composetemplate.util.internet.NetworkConnectivityServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providerConfigurationsRepository(
        apiService: ConfigServiceApi,
    ): ConfigurationsRepository {
        return ConfigurationsRepository(apiService)
    }

    @Provides
    @Singleton
    fun providerAuthRepository(
        apiService: AuthService,
    ): AuthRepository {
        return AuthRepository(apiService)
    }

    @Provides
    @Singleton
    fun providerCategoryRepository(
        apiService: CategoryService,
    ): CategoryRepository {
        return CategoryRepository(apiService)
    }

    @Provides
    @Singleton
    fun providerNetworkService(
        @ApplicationContext appContext: Context
    ): NetworkConnectivityService {
        return NetworkConnectivityServiceImpl(appContext)
    }


}