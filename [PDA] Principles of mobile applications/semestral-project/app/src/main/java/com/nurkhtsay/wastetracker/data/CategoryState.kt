package com.nurkhtsay.wastetracker.data

data class CategoryState(
    val categories: List<Category> = emptyList(),
    val name: String = "",
    val id: Int = 0
)