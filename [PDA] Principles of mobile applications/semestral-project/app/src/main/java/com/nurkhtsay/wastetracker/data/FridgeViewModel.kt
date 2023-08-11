package com.nurkhtsay.wastetracker.data

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.nurkhtsay.wastetracker.data.SortType.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class FridgeViewModel(
    private val productDao: ProductDao,
    private val categoryDao: CategoryDao,
    private val statisticsDao: StatisticsDao
) : ViewModel() {

    private val _sortType = MutableStateFlow(EXPIRY)
    private val _sortTypeByCategory = MutableStateFlow("All")
    private val _findByName = MutableStateFlow("")

    private val _categories: StateFlow<List<Category>> = flow {
        emit(categoryDao.getAllCategories())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _categoryState = MutableStateFlow(CategoryState())
    val categoryState = combine(_categoryState, _categories) { categoryState, categories ->
        categoryState.copy(
            categories = categories
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CategoryState())

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _products =
        combine(
            _sortType,
            _sortTypeByCategory,
            _findByName
        ) { sortType, sortTypeByCategory, findByName ->
            when (sortType) {
                EXPIRY -> if (sortTypeByCategory == "All") {
                    if (findByName != "") {
                        productDao.getAllProductsByName(findByName)
                    } else {
                        productDao.getAllProducts()
                    }
                } else {
                    if (findByName != "") {
                        productDao.getProductsByCategoryAndName(
                            sortTypeByCategory, findByName
                        )
                    } else {
                        productDao.getProductsByCategory(
                            sortTypeByCategory
                        )
                    }
                }

                LAST_ADDED -> if (sortTypeByCategory == "All") {
                    if (findByName != "") {
                        productDao.getLastAddedProductsAndByName(findByName)
                    } else {
                        productDao.getLastAddedProducts()
                    }
                } else {
                    if (findByName != "") {
                        productDao.getLastAddedProductsByCategoryAndName(
                            sortTypeByCategory, findByName
                        )
                    } else {
                        productDao.getLastAddedProductsByCategory(
                            sortTypeByCategory
                        )
                    }
                }

                NAME -> if (sortTypeByCategory == "All") {
                    if (findByName != "") {
                        productDao.getAllProductsOrderedByNameAndByName(findByName)
                    } else {
                        productDao.getAllProductsOrderedByName()
                    }
                } else {
                    if (findByName != "") {
                        productDao.getAllProductsOrderedByNameAndByCategoryAndByName(
                            sortTypeByCategory, findByName
                        )
                    } else {
                        productDao.getAllProductsOrderedByNameAndByCategory(
                            sortTypeByCategory
                        )
                    }
                }
            }

        }
            .flatMapLatest { it }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    private val _state = MutableStateFlow(ProductState())
    val state = combine(
        _state,
        _sortType,
        _products,
        _sortTypeByCategory,
        _findByName
    ) { state, sortType, products, categoryName, findByName ->
        state.copy(
            products = products,
            sortType = sortType,
            categoryName = categoryName,
            findByName = findByName
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductState())

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: ProductEvent): Boolean {

        when (event) {
            is ProductEvent.SaveProduct -> {
                val name = state.value.name
                val quantity = state.value.quantity
                val measuring = state.value.measuring
                val photo = state.value.photo
                val category = state.value.category
                val expirationDate = state.value.expirationDate
                val addedDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond()
                val daysUntilDiscard = ((expirationDate.toLong() - addedDate) / 86400) + 1

                if (name.isBlank() || quantity.isBlank() || measuring.isBlank() ||
                    photo.isBlank() || category.isBlank() || expirationDate.isBlank()
                ) {
                    return false
                }

                val product = Product(
                    name = name,
                    photo = photo,
                    expirationDate = expirationDate.toLong(),
                    quantity = quantity,
                    measuring = measuring,
                    category = category,
                    addedDate = addedDate,
                    daysUntilDiscard = daysUntilDiscard
                )

                viewModelScope.launch {
                    productDao.insertProduct(product)
                }
                _state.update {
                    it.copy(
                        name = "",
                        photo = "",
                        expirationDate = "",
                        quantity = "",
                        measuring = "",
                        category = "",
                        productId = -1,
                        existingInDb = false
                    )
                }
            }

            is ProductEvent.UpdateProduct -> {
                val id = state.value.productId
                val name = state.value.name
                val quantity = state.value.quantity
                val measuring = state.value.measuring
                val photo = state.value.photo
                val category = state.value.category
                val expirationDate = if (state.value.expirationDate.contains(".")) {
                    (SimpleDateFormat("dd.MM.yyyy").parse(state.value.expirationDate).time / 1000).toString()
                } else {
                    state.value.expirationDate
                }
                val addedDate = state.value.addedDate
                val daysUntilDiscard = ((expirationDate.toLong() - addedDate) / 86400) + 1
                if (name.isBlank() || quantity.isBlank() || measuring.isBlank() ||
                    photo.isBlank() || category.isBlank() || expirationDate.isBlank()
                ) {
                    return false
                }
                val product = Product(
                    id = id,
                    name = name,
                    photo = photo,
                    expirationDate = expirationDate.toLong(),
                    quantity = quantity,
                    measuring = measuring,
                    category = category,
                    addedDate = addedDate,
                    daysUntilDiscard = daysUntilDiscard
                )
                viewModelScope.launch {
                    productDao.updateProduct(product)
                }
            }

            is ProductEvent.UpdateQuantity -> {
                viewModelScope.launch {
                    productDao.updateQuantity(event.productId, event.quantity.toString())
                }
            }

            is ProductEvent.SetName -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }

            is ProductEvent.SetPhoto -> {
                _state.update {
                    it.copy(
                        photo = event.photo
                    )
                }
            }

            is ProductEvent.SetSortType -> {
                _state.update {
                    it.copy(
                        sortType = event.sortType
                    )
                }
            }

            is ProductEvent.SetDaysUntilDiscard -> {
                _state.update {
                    it.copy(
                        daysUntilDiscard = event.daysUntilDiscard
                    )
                }
            }

            is ProductEvent.SetAddedDate -> {
                _state.update {
                    it.copy(
                        addedDate = event.addedDate
                    )
                }
            }

            is ProductEvent.DeleteProductById -> {
                viewModelScope.launch {
                    productDao.deleteProductById(event.productId)
                }
                _state.update {
                    it.copy(
                        productId = -1
                    )
                }
            }

            is ProductEvent.SetExpirationDate -> {
                _state.update {
                    it.copy(
                        expirationDate = event.expirationDate
                    )
                }
            }

            is ProductEvent.SetCategory -> {
                _state.update {
                    it.copy(
                        category = event.category
                    )
                }
            }

            is ProductEvent.SetMeasuring -> {
                _state.update {
                    it.copy(
                        measuring = event.measuring
                    )
                }
            }

            is ProductEvent.SetQuantity -> {
                _state.update {
                    it.copy(
                        quantity = event.quantity
                    )
                }
            }

            is ProductEvent.SortProducts -> {
                _sortType.value = event.sortType
            }

            is ProductEvent.SortProductsByCategory -> {
                _sortTypeByCategory.value = event.sortCategoryType
            }

            is ProductEvent.FindProductsByName -> {
                _findByName.value = event.name
            }

            is ProductEvent.ResetProductState -> {
                _state.update {
                    it.copy(
                        name = "",
                        photo = "",
                        expirationDate = "",
                        quantity = "",
                        measuring = "",
                        category = "",
                        productId = -1
                    )
                }
            }

            is ProductEvent.SetProductId -> {
                _state.update {
                    it.copy(
                        productId = event.id
                    )
                }
            }

            is ProductEvent.SetExistingInDb -> {
                _state.update {
                    it.copy(
                        existingInDb = event.existingInDb
                    )
                }
            }

            is ProductEvent.IncrementThrown -> {
                viewModelScope.launch {
                    statisticsDao.increaseThrownCount()
                }
            }

            is ProductEvent.IncrementEaten -> {
                viewModelScope.launch {
                    statisticsDao.increaseEatenCount()
                }
            }

            is ProductEvent.UpdateStatistics -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            thrownCount = statisticsDao.getThrownCount(),
                            eatenCount = statisticsDao.getEatenCount()
                        )
                    }
                }
            }
        }
        return true
    }
}


