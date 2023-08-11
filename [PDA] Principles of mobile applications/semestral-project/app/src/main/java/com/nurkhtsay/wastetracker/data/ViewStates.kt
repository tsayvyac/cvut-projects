package com.nurkhtsay.wastetracker.data

import androidx.compose.runtime.State

data class ViewStates(
    val productState: State<ProductState>,
    val categoryState: State<CategoryState>
)
