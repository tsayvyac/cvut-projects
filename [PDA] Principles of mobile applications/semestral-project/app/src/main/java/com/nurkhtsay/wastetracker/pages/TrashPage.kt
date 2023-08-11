package com.nurkhtsay.wastetracker.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.nurkhtsay.wastetracker.components.TopBar

@Composable
fun TrashPage(
    navController: NavController
) {
    TopBar(
        name = "Trash",
        navController = navController,
        icon = Icons.Default.ArrowBack
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    )
}