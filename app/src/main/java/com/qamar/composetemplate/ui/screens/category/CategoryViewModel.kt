package com.qamar.composetemplate.ui.screens.category

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qamar.composetemplate.data.remote.categories.model.category.Children
import com.qamar.composetemplate.data.remote.categories.model.products.Product
import com.qamar.composetemplate.data.remote.categories.repository.CategoryRepository
import com.qamar.composetemplate.data.remote.config.model.config.Configurations
import com.qamar.composetemplate.ui.screens.category.state.CategoryUiState
import com.qamar.composetemplate.ui.screens.category.state.ProductUiState
import com.qamar.composetemplate.util.networking.model.ErrorsData
import com.qamar.composetemplate.util.networking.model.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.triggered
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: CategoryRepository
) : ViewModel() {

    var isLoaded = mutableStateOf(false)
    var categoryTitle = mutableStateOf("")
    var categoryId = mutableIntStateOf(0)
    var mainCategoryId = mutableIntStateOf(0)
    var page by mutableIntStateOf(1)

    @SuppressLint("MutableCollectionMutableState")
    var categoriesList = mutableStateOf(mutableListOf<Children>())
    @SuppressLint("MutableCollectionMutableState")
    var productsList = mutableStateOf(mutableListOf<Product>())
    private var isFistTimeToLoad = mutableStateOf(true)
    var canPaginate by mutableStateOf(false)


    /**
     * State Flows
     */
    private val _uiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()


    private val _productUiState = MutableStateFlow<ProductUiState>(ProductUiState.IDLE)
    val productUiState: StateFlow<ProductUiState> = _productUiState.asStateFlow()


    /**
     * API Requests
     */
    fun getCategories() {
        viewModelScope.launch {
            repository.getCategories()
                .collect { result ->
                    val categoriesUiState = when (result.status) {
                        Status.SUCCESS -> {
                            isLoaded.value = true
                            CategoryUiState.Success(
                                categories = result.data?.toImmutableList()
                            )
                        }

                        Status.LOADING ->
                            CategoryUiState.Loading

                        Status.ERROR -> CategoryUiState.Failed(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                    result.statusCode
                                )
                            )
                        )
                    }
                    _uiState.update {
                        categoriesUiState
                    }
                }
        }
    }

    fun getCategoryProducts() {
        viewModelScope.launch {
            _productUiState.update {
                if (page == 1) ProductUiState.Loading else ProductUiState.Paging
            }
            repository.getCategoryProducts(
                page,
                categoryId.intValue,
            )
                .collect { result ->
                    val offersUiState = when (result.status) {
                        Status.SUCCESS -> {
                            if (isFistTimeToLoad.value) {
                                categoriesList.value =
                                    result.data?.category?.childs?.toMutableList()
                                        ?: mutableListOf()
                                if (categoriesList.value.firstOrNull()?.id != mainCategoryId.intValue) {
                                    categoriesList.value.add(
                                        0,
                                        Children(
                                            id = mainCategoryId.intValue,
                                            name = if (Configurations.appLocal.value == "ar") "جميع المنتجات" else "All"
                                        )
                                    )
                                }
                                isFistTimeToLoad.value = false
                            }
                            canPaginate = result.data?.hasMorePage ?: false
                            if (page == 1) {
                                productsList.value.clear()
                                result.data?.products?.let { productsList.value.addAll(it) }
                            } else {
                                result.data?.products?.let { productsList.value.addAll(it) }
                            }
                            if (canPaginate)
                                page++
                            if (productsList.value.isEmpty())
                                ProductUiState.Empty
                            else {
                                ProductUiState.Success(
                                    products = productsList.value.toImmutableList(),
                                    categories = categoriesList.value.toImmutableList(),
                                    onHomeLoaded = triggered
                                )
                            }
                        }

                        Status.LOADING ->
                            if (page == 1) ProductUiState.Loading else ProductUiState.Paging

                        Status.ERROR -> ProductUiState.Failed(
                            onFailure = triggered(
                                ErrorsData(
                                    result.errors,
                                    result.message,
                                    result.statusCode
                                )
                            )
                        )
                    }
                    _productUiState.update {
                        offersUiState
                    }
                }
        }
    }

    fun onCategoryClick(id: Int) {
        categoryId.intValue = id
        page = 1
        getCategoryProducts()
    }

    fun onFailure() {
        _uiState.update {
            it.onReset()
        }
    }
}