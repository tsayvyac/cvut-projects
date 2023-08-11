package com.nurkhtsay.wastetracker.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.nurkhtsay.wastetracker.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    name: String,
    navController: NavController,
    icon: ImageVector
) {
    TopAppBar(
        title = {
            Text(text = name)
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = icon,
                    contentDescription = icon.name
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Primary)
    )
}