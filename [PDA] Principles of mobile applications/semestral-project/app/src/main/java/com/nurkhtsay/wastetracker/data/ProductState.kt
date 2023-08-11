package com.nurkhtsay.wastetracker.data

data class ProductState(
    val products: List<Product> = emptyList(),
    val photo: String = "",
    val expirationDate: String = "",
    val quantity: String = "",
    val category: String = "",
    val categoryName: String = "",
    val measuring: String = "",
    val findByName: String = "",
    val daysUntilDiscard: Int = 1,
    val addedDate: Long = 0,
    val sortType: SortType =  SortType.EXPIRY,
    val name: String = "",
    val productId: Long = -1,
    val existingInDb: Boolean = false,
    val thrownCount: Int = 0,
    val eatenCount: Int = 0
    )