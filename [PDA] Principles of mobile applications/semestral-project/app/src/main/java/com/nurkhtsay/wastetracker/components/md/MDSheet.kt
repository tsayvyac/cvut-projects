package com.nurkhtsay.wastetracker.components.md

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nurkhtsay.wastetracker.R
import com.nurkhtsay.wastetracker.navigator.Page
import com.nurkhtsay.wastetracker.ui.theme.Primary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class DrawerItems(val name: String, val icon: Int, val route: String)

object Items {
    var Settings = DrawerItems("Settings", R.drawable.ic_settings, Page.Settings.route)
    var Trash = DrawerItems("Trash", R.drawable.ic_trash, Page.Trash.route)
    var About = DrawerItems("About", R.drawable.ic_about, Page.About.route)

    val items = listOf(Settings, Trash, About)
}

val montserrat = FontFamily(
    Font(R.font.montserrat_black, FontWeight.Black)
)

@Composable
fun MDSheet(
    drawerState: DrawerState,
    scope: CoroutineScope,
    navController: NavController
) {
    ModalDrawerSheet(
        drawerContainerColor = Primary,
    ) {
        Spacer(Modifier.height(8.dp))
        IconButton(
            modifier = Modifier
                .padding(horizontal = 13.dp),
            onClick = { scope.launch { drawerState.close() } }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Arrow back icon"
            )
        }
        Box(
            modifier = Modifier
                .padding(vertical = 32.dp)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Icon(
                    painterResource(id = R.drawable.ic_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(160.dp),
                    tint = Color.Black
                )
                Text(
                    text = "OBSERVER",
                    fontFamily = montserrat,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .align(Alignment.CenterHorizontally),
                    color = Color.Black
                )
            }
        }
        Items.items.forEach { item ->
            NavItems(
                drawerState = drawerState,
                navController = navController,
                scope = scope,
                item = item
            )
        }
    }
}