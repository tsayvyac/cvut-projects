package com.nurkhtsay.wastetracker.navigator

sealed class Page(val route: String) {
    object AppPage : Page("app_page")
    object Settings : Page("settings_page")
    object Trash : Page("trash_page")
    object About : Page("about_page")
    object AddItem : Page("add_item_page")
}
