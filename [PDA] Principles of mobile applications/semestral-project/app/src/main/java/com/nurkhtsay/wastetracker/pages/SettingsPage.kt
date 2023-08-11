package com.nurkhtsay.wastetracker.pages

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.nurkhtsay.wastetracker.MainActivity
import com.nurkhtsay.wastetracker.R
import com.nurkhtsay.wastetracker.components.TopBar
import kotlin.math.roundToInt

@Composable
fun SettingsPage(
    navController: NavController,
    context: Context
) {
    var sliderPosition by remember { mutableStateOf(1f) }
    val word = if (sliderPosition.roundToInt() > 1) " days" else " day"

    val contextLoc = LocalContext.current
    var hasNotificationPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    contextLoc,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else mutableStateOf(true)
    }
    var checked by remember { mutableStateOf(hasNotificationPermission) }

    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val activityIntent = Intent(context, MainActivity::class.java)
    val activityPendingIntent = PendingIntent.getActivity(
        context,
        1,
        activityIntent,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
    )

    val notification = NotificationCompat.Builder(context, "notify_days_before")
        .setSmallIcon(R.drawable.ic_logo)
        .setContentTitle("Days before")
        .setContentText("Test notification")
        .setContentIntent(activityPendingIntent)
        .setAutoCancel(true)
        .build()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasNotificationPermission = isGranted

        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar(
            name = "Settings",
            navController = navController,
            icon = Icons.Default.ArrowBack
        )
        Column(
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Notification",
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                )
                Switch(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                        if (checked) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            }
                        } else hasNotificationPermission = false
                    },
                    modifier = Modifier
                        .semantics { contentDescription = "Notification switcher" }
                        .scale(0.8f, 0.8f),
                )
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = "Notify me before:",
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )

                Text(text = sliderPosition.roundToInt().toString() + word)
                Slider(
                    modifier = Modifier
                        .semantics { contentDescription = "Days before" },
                    value = sliderPosition,
                    onValueChange = { sliderPosition = it },
                    valueRange = 1f..30f,
                    steps = 30
                )
            }
            Button(
                modifier = Modifier
                    .padding(16.dp),
                onClick = {
                    if (hasNotificationPermission) {
                        notificationManager.notify(1, notification)
                    }
                }
            ) {
                Text(text = "Test notifications")
            }
        }
    }
}