package com.nurkhtsay.wastetracker.data

sealed interface CategoryEvent {
    object SaveCategory : CategoryEvent
    data class SetCategoryName(val name: String) : CategoryEvent
    data class SetCategories(val categories: List<Category>) : CategoryEvent
    data class DeleteCategory(val category: Category) : CategoryEvent
}