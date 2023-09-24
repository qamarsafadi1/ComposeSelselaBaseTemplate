package com.qamar.composetemplate.ui.screens.category.event

sealed interface CategoriesEvents {
    data class OnCategoryClick(val categoryId: Int, val title: String) : CategoriesEvents
}