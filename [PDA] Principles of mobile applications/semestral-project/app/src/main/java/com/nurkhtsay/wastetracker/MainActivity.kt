package com.nurkhtsay.wastetracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.nurkhtsay.wastetracker.data.FridgeViewModel
import com.nurkhtsay.wastetracker.data.ViewStates
import com.nurkhtsay.wastetracker.data.WasteTrackerDatabase
import com.nurkhtsay.wastetracker.navigator.NavGraph
import com.nurkhtsay.wastetracker.ui.theme.WasteTrackerTheme
import com.nurkhtsay.wastetracker.workers.ExpirationWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    lateinit var navHostController: NavHostController

    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            WasteTrackerDatabase::class.java,
            "waste_tracker.db"
        )
            .createFromAsset("database/categories.db")
            .createFromAsset("database/statistics.db")
            .build()
    }

    private val viewModel by viewModels<FridgeViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return FridgeViewModel(db.productDao, db.categoryDao, db.statisticsDao) as T
                }
            }
        }
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WasteTrackerTheme {
                navHostController = rememberNavController()

                val viewStates = ViewStates(
                    productState = viewModel.state.collectAsState(),
                    categoryState = viewModel.categoryState.collectAsState(),
                )

                NavGraph(
                    navController = navHostController,
                    viewStates = viewStates,
                    onEvent = viewModel::onEvent,
                    applicationContext
                )
            }
        }
        scheduleExpirationWorker()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun scheduleExpirationWorker() {
        val workRequest = PeriodicWorkRequestBuilder<ExpirationWorker>(24, TimeUnit.HOURS)
            .setInitialDelay(60, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "ExpirationWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}