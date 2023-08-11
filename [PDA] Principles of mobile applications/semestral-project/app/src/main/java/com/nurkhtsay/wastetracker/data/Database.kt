package com.nurkhtsay.wastetracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Product::class, Category::class, Statistics::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class WasteTrackerDatabase : RoomDatabase() {
    abstract val categoryDao: CategoryDao
    abstract val productDao: ProductDao
    abstract val statisticsDao: StatisticsDao

    companion object {
        private var instance: WasteTrackerDatabase? = null

        fun getInstance(context: Context): WasteTrackerDatabase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    WasteTrackerDatabase::class.java,
                    "waste_tracker.db"
                )
                    .createFromAsset("database/categories.db")
                    .build()
                instance = newInstance
                newInstance
            }
        }
    }
}
