package com.nurkhtsay.wastetracker.components.md

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.nurkhtsay.wastetracker.ui.theme.Primary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavItems(
    drawerState: DrawerState,
    navController: NavController,
    scope: CoroutineScope,
    item: DrawerItems
) {
    NavigationDrawerItem(
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Primary
        ),
        icon = { Icon(painterResource(id = item.icon), contentDescription = null) },
        label = { Text(text = item.name) },
        selected = false,
        onClick = {
            navController.navigate(route = item.route)
            scope.launch { drawerState.close() }
        },
        modifier = Modifier
            .padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}