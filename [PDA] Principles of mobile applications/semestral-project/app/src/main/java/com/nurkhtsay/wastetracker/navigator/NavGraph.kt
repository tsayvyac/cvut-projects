package com.nurkhtsay.wastetracker.navigator

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nurkhtsay.wastetracker.App
import com.nurkhtsay.wastetracker.data.ProductEvent
import com.nurkhtsay.wastetracker.data.ViewStates
import com.nurkhtsay.wastetracker.pages.AboutPage
import com.nurkhtsay.wastetracker.pages.AddItemPage
import com.nurkhtsay.wastetracker.pages.SettingsPage
import com.nurkhtsay.wastetracker.pages.TrashPage

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    viewStates: ViewStates,
    onEvent: (ProductEvent) -> Boolean,
    context: Context
) {

    NavHost(
        navController = navController,
        startDestination = Page.AppPage.route
    ) {
        composable(route = Page.AppPage.route) {
            App(navController, viewStates, onEvent)
        }
        composable(route = Page.Settings.route) {
            SettingsPage(navController, context)
        }
        composable(route = Page.About.route) {
            AboutPage(navController)
        }
        composable(route = Page.Trash.route) {
            TrashPage(navController)
        }
        composable(route = Page.AddItem.route) {
            AddItemPage(viewStates.productState.value, onEvent, navController)
        }
    }
}