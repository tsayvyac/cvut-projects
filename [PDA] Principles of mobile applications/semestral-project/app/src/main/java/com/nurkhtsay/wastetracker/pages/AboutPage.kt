package com.nurkhtsay.wastetracker.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nurkhtsay.wastetracker.R
import com.nurkhtsay.wastetracker.components.TopBar
import com.nurkhtsay.wastetracker.components.md.montserrat
import com.nurkhtsay.wastetracker.ui.theme.Primary

@Composable
fun AboutPage(
    navController: NavController
) {
    val uriHandler = LocalUriHandler.current
    TopBar(
        name = "About",
        navController = navController,
        icon = Icons.Default.ArrowBack
    )
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Icon(
                painterResource(id = R.drawable.ic_logo),
                contentDescription = null,
                modifier = Modifier
                    .size(160.dp)
                    .align(Alignment.CenterHorizontally),
                tint = Primary
            )
            Text(
                text = "OBSERVER",
                fontFamily = montserrat,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .align(Alignment.CenterHorizontally),
                color = Primary
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 48.dp,
                        bottom = 4.dp
                    ),
                colors = CardDefaults.cardColors(containerColor = Primary)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = "OBSERVER team: ",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                    Text(
                        text = "Arlan Nurkhozhin",
                        fontSize = 16.sp,
                        color = Color.White,
                    )
                    Text(
                        text = "Vyacheslav Tsay",
                        fontSize = 16.sp,
                        color = Color.White,
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 4.dp,
                        bottom = 4.dp
                    ),
                colors = CardDefaults.cardColors(containerColor = Primary)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Sources: ",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 4.dp
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Primary),
                    modifier = Modifier
                        .clickable {

                        }
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_github),
                        contentDescription = "Github logo",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(22.dp)
                            .clickable {
                                uriHandler.openUri("https://github.com/")
                            }
                    )
                }
                Card(
                    colors = CardDefaults.cardColors(containerColor = Primary)
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_logo),
                        contentDescription = "Github logo",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(22.dp)
                            .size(64.dp)
                            .clickable {
                                uriHandler.openUri("https://static.wikia.nocookie.net/creepypasta/images/3/3b/Creepy_eyes.png/revision/latest?cb=20220314204127")
                            }
                    )
                }
                Card(
                    colors = CardDefaults.cardColors(containerColor = Primary)
                ) {
                    Text(
                        text = "IRIS",
                        modifier = Modifier
                            .padding(
                                start = 22.dp,
                                end = 22.dp,
                                top = 32.dp,
                                bottom = 32.dp
                            )
                            .clickable {
                                uriHandler.openUri("https://en.wikipedia.org/wiki/Iris_(anatomy)")
                            },
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}