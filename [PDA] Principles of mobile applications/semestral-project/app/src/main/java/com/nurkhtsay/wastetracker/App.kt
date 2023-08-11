package com.nurkhtsay.wastetracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nurkhtsay.wastetracker.components.NavBar
import com.nurkhtsay.wastetracker.components.Routes
import com.nurkhtsay.wastetracker.components.md.MDSheet
import com.nurkhtsay.wastetracker.data.ProductEvent
import com.nurkhtsay.wastetracker.data.SortType
import com.nurkhtsay.wastetracker.data.ViewStates
import com.nurkhtsay.wastetracker.navigator.Page
import com.nurkhtsay.wastetracker.pages.CategoriesPage
import com.nurkhtsay.wastetracker.pages.FridgePage
import com.nurkhtsay.wastetracker.pages.ShoppingListsPage
import com.nurkhtsay.wastetracker.pages.StatisticsPage
import com.nurkhtsay.wastetracker.ui.theme.Primary
import com.nurkhtsay.wastetracker.ui.theme.Purple80
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun App(
    navController: NavController,
    viewStates: ViewStates,
    onEvent: (ProductEvent) -> Boolean,
) {
    val selectedRoute = remember {
        mutableStateOf("fridge")
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val skipPartiallyExpanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )

    var isOpen by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }  // Remember the FocusRequester

    val transition = updateTransition(targetState = isOpen, label = "transition")

    val widthFraction by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 300) },
        label = "width"
    ) { if (it) 1f else 0f }

    val cornerRadius by transition.animateDp(
        transitionSpec = { tween(durationMillis = 300) },
        label = "cornerRadius"
    ) { if (it) 0.dp else 24.dp }
    val keyboardController = LocalSoftwareKeyboardController.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            MDSheet(drawerState, scope, navController)
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = "Localized description"
                                )
                            }
                        },
                        actions = {
                            if (selectedRoute.value == Routes.FridgePage.route) {
                                BoxWithConstraints(
                                    modifier = Modifier
                                        .height(52.dp)
                                        .clip(RoundedCornerShape(cornerRadius))
                                        .clickable { isOpen = false }
                                        .focusRequester(focusRequester)
                                ) {
                                    val maxWidth = maxWidth
                                    val iconWidth = 144.dp
                                    val calculatedWidth =
                                        maxWidth * widthFraction.coerceAtLeast(iconWidth / maxWidth)

                                    Box(
                                        modifier = Modifier.width(calculatedWidth)
                                    ) {
                                        if (isOpen) {
                                            TextField(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(horizontal = 8.dp)
                                                    .background(Purple80),
                                                value = text,
                                                placeholder = { Text(text = "Search") },
                                                onValueChange = { text = it },
                                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                                keyboardActions = KeyboardActions(
                                                    onSearch = {
                                                        keyboardController?.hide()
                                                        onEvent(ProductEvent.FindProductsByName(text))
//
                                                    }
                                                ),
                                                leadingIcon = {
                                                    IconButton(onClick = {
                                                        isOpen = false
                                                        onEvent(ProductEvent.FindProductsByName(""))
                                                        text = ""
                                                    }) {
                                                        Icon(
                                                            imageVector = Icons.Filled.Close,
                                                            contentDescription = "Close"
                                                        )
                                                    }
                                                },
                                                singleLine = true
                                            )
                                            LaunchedEffect(Unit) {
                                                focusRequester.requestFocus()
                                            }
                                        } else {

                                            Row {
                                                IconButton(onClick = { isOpen = true }) {
                                                    Icon(
                                                        imageVector = Icons.Filled.Search,
                                                        contentDescription = "Search Icon"
                                                    )
                                                }
                                                IconButton(onClick = { }) {
                                                    Icon(
                                                        imageVector = Icons.Filled.Notifications,
                                                        contentDescription = "Notifications button"
                                                    )
                                                }
                                                IconButton(onClick = {
                                                    showDialog = true
                                                }) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.sort_icon),
                                                        contentDescription = "sort"
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Primary)
                    )
                },
                content = {
                    when (selectedRoute.value) {
                        Routes.FridgePage.route -> FridgePage(
                            it,
                            viewStates,
                            onEvent,
                            navController
                        )

                        Routes.Categories.route -> CategoriesPage()
                        Routes.ShoppingLists.route -> ShoppingListsPage()
                        Routes.StatisticsPage.route -> {
                            onEvent(ProductEvent.UpdateStatistics)
                            StatisticsPage(viewStates)
                        }
                    }

                    if (showDialog) {
                        ModalBottomSheet(
                            onDismissRequest = { showDialog = false },
                            sheetState = bottomSheetState,
                            containerColor = Primary
                        ) {

                            Column()
                            {
                                Text(
                                    text = "Sort products by",
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier
                                        .padding(
                                            start = 16.dp,
                                            bottom = 16.dp
                                        )
                                )

                                Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)

                                Row(modifier = Modifier
                                    .padding(
                                        top = 16.dp
                                    )
                                    .fillMaxWidth()
                                    .clickable {
                                        scope
                                            .launch { bottomSheetState.hide() }
                                            .invokeOnCompletion {
                                                if (!bottomSheetState.isVisible) {
                                                    onEvent(ProductEvent.SortProducts(SortType.NAME))
                                                    showDialog = false
                                                }
                                            }
                                    }) {
                                    Text(
                                        text = "Sort by Name",
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }

                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        scope
                                            .launch { bottomSheetState.hide() }
                                            .invokeOnCompletion {
                                                if (!bottomSheetState.isVisible) {
                                                    onEvent(ProductEvent.SortProducts(SortType.LAST_ADDED))
                                                    showDialog = false
                                                }
                                            }
                                    }) {
                                    Text(
                                        text = "Sort by Last Added",
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier
                                            .padding(16.dp)
                                    )
                                }

                                Row(modifier = Modifier
                                    .padding(
                                        bottom = 16.dp
                                    )
                                    .fillMaxWidth()
                                    .clickable {
                                        scope
                                            .launch { bottomSheetState.hide() }
                                            .invokeOnCompletion {
                                                if (!bottomSheetState.isVisible) {
                                                    onEvent(ProductEvent.SortProducts(SortType.EXPIRY))
                                                    showDialog = false
                                                }
                                            }
                                    }) {
                                    Text(
                                        text = "Sort by Expiry",
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier
                                            .padding(16.dp)
                                    )
                                }
                            }
                        }
                    }
                },

                bottomBar = {
                    NavBar(selectedRoute = selectedRoute.value,
                        onClick = { route ->
                            selectedRoute.value = route
                        })
                },

                floatingActionButton = {
                    if (selectedRoute.value == Routes.FridgePage.route) {
                        FloatingActionButton(
                            modifier = Modifier.padding(all = 16.dp),
                            onClick = {
                                navController.navigate(route = Page.AddItem.route)
                            },
                            containerColor = Primary,
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Add,
                                contentDescription = "Add FAB",
                                tint = Color.Black,
                            )
                        }
                    }
                }
            )
        }
    )
}