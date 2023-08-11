package com.nurkhtsay.wastetracker.pages

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.nurkhtsay.wastetracker.data.Product
import com.nurkhtsay.wastetracker.data.ProductEvent
import com.nurkhtsay.wastetracker.data.ViewStates
import com.nurkhtsay.wastetracker.navigator.Page
import java.text.SimpleDateFormat

@Composable
fun FridgePage(
    it: PaddingValues,
    viewStates: ViewStates,
    onEvent: (ProductEvent) -> Boolean,
    navController: NavController
) {
    var state by remember { mutableStateOf(0) }
    val categories = listOf("All", "Dairy products", "Meat and poultry", "Fruits", "Vegetables")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(it),
    ) {
        item {
            ScrollableTabRow(selectedTabIndex = state) {
                categories.forEachIndexed { index, category ->
                    Tab(
                        selected = state == index,
                        onClick = {
                            state = index
                            onEvent(ProductEvent.SortProductsByCategory(category))
                        },
                        text = { Text(text = category) }
                    )
                }
            }
        }
        items(viewStates.productState.value.products) { product ->
            ProductCard(product = product, navController = navController, onEvent = onEvent)
        }
    }
}

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(
    product: Product,
    navController: NavController,
    onEvent: (ProductEvent) -> Boolean
) {
    val formatter = SimpleDateFormat("dd.MM.yyyy")
    Card(
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        onClick = {
            onEvent(ProductEvent.SetProductId(product.id))
            onEvent(ProductEvent.SetName(product.name))
            onEvent(ProductEvent.SetCategory(product.category))
            onEvent(ProductEvent.SetMeasuring(product.measuring))
            onEvent(ProductEvent.SetPhoto(product.photo))
            onEvent(ProductEvent.SetQuantity(product.quantity))
            onEvent(ProductEvent.SetExpirationDate(formatter.format(product.expirationDate * 1000)))
            onEvent(ProductEvent.SetAddedDate(product.addedDate))
            onEvent(ProductEvent.SetProductId(product.id))
            onEvent(ProductEvent.SetExistingInDb(true))
            navController.navigate(route = Page.AddItem.route)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Image(
                painter = rememberAsyncImagePainter(Uri.parse(product.photo)),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .height(75.dp)
                    .weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    buildAnnotatedString {
                        append("${product.quantity} ${product.measuring}")
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "")

                    Text(
                        buildAnnotatedString {
                            if (product.daysUntilDiscard < 0) {
                                withStyle(style = SpanStyle(color = Color.Red)) {
                                    append("${product.daysUntilDiscard * (-1)} overdue")
                                }
                            } else {
                                append("${product.daysUntilDiscard} days")
                            }
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
        Box {
            Divider(color = Color.Gray, thickness = 1.dp)
        }
    }
}