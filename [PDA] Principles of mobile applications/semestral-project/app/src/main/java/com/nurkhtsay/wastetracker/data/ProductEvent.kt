package com.nurkhtsay.wastetracker.data

sealed interface ProductEvent {
    object SaveProduct: ProductEvent
    object UpdateProduct: ProductEvent
    data class UpdateQuantity(val productId: Long, val quantity: Int): ProductEvent
    data class SetName(val name: String): ProductEvent
    data class SetPhoto(val photo: String): ProductEvent
    data class SetSortType(val sortType: SortType): ProductEvent
    data class SetDaysUntilDiscard(val daysUntilDiscard: Int): ProductEvent
    data class SetAddedDate(val addedDate: Long): ProductEvent
    data class SetExpirationDate(val expirationDate: String): ProductEvent
    data class SetQuantity(val quantity: String): ProductEvent
    data class SetMeasuring(val measuring: String): ProductEvent
    data class SetCategory(val category: String): ProductEvent
    data class SetProductId(val id: Long): ProductEvent
    data class SetExistingInDb(val existingInDb: Boolean): ProductEvent
    data class ResetProductState(val buff: Unit): ProductEvent
    data class DeleteProductById(val productId: Long): ProductEvent
    data class SortProducts(val sortType: SortType): ProductEvent
    data class SortProductsByCategory(val sortCategoryType: String): ProductEvent
    data class FindProductsByName(val name: String): ProductEvent
    object IncrementThrown: ProductEvent
    object IncrementEaten: ProductEvent
    object UpdateStatistics: ProductEvent
}
