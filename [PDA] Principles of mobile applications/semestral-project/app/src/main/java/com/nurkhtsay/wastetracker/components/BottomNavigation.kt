package com.nurkhtsay.wastetracker.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.nurkhtsay.wastetracker.R
import com.nurkhtsay.wastetracker.ui.theme.Primary

data class NavPage(var name: String, var icon: Int, var route: String)

object Routes {
    var StatisticsPage = NavPage("Statistics", R.drawable.statistics_icon, "statistics")
    var ShoppingLists = NavPage("ShoppingLists", R.drawable.shopping_list, "shopping_lists")
    var FridgePage = NavPage("Fridge", R.drawable.fridge_vector, "fridge")
    var Categories = NavPage("Categories", R.drawable.categories_icons, "categories")

    val pages = listOf(StatisticsPage, ShoppingLists, FridgePage, Categories)
}

@Composable
fun NavBar(selectedRoute: String = Routes.FridgePage.route, onClick: (String) -> Unit = {}) {
    androidx.compose.material3.NavigationBar(
        containerColor = Primary,
    ) {
        Routes.pages.forEach { page ->
            val selected = selectedRoute == page.route

            NavigationBarItem(
                selected = selected,
                onClick = { onClick(page.route) },
                label = {
                    Text(
                        text = page.name,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(page.icon),
                        contentDescription = page.name,
                    )
                },
                colors = if (selected) {
                    NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme
                            .colorScheme
                            .inversePrimary
                    )
                } else {
                    NavigationBarItemDefaults.colors()
                }
            )
        }
    }
}
