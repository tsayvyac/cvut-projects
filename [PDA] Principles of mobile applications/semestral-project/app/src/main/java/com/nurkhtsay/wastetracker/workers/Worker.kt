package com.nurkhtsay.wastetracker.workers

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nurkhtsay.wastetracker.MainActivity
import com.nurkhtsay.wastetracker.R
import com.nurkhtsay.wastetracker.data.WasteTrackerDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExpirationWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    private val notificationManager =
        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val activityIntent = Intent(applicationContext, MainActivity::class.java)
    private val activityPendingIntent: PendingIntent = PendingIntent.getActivity(
        applicationContext,
        1,
        activityIntent,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
    )

    private val notification = NotificationCompat.Builder(applicationContext, "notify_days_before")
        .setSmallIcon(R.drawable.ic_logo)
        .setContentTitle("Decrease days")
        .setContentText("Days has been decreased")
        .setContentIntent(activityPendingIntent)
        .setAutoCancel(true)
        .build()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            Log.d("doWork", "work")
            val myDao = WasteTrackerDatabase.getInstance(applicationContext).productDao
            myDao.decreaseDaysUntilDiscard()
            notificationManager.notify(2, notification)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}