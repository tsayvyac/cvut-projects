package com.nurkhtsay.wastetracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StatisticsDao {
    @Insert
    suspend fun insert(statistics: Statistics)

    @Query("UPDATE statistics SET eaten = eaten + 1 WHERE id = 1")
    suspend fun increaseEatenCount()

    @Query("UPDATE statistics SET thrown = thrown + 1 WHERE id = 1")
    suspend fun increaseThrownCount()

    @Query("UPDATE statistics SET eaten = 0, thrown = 0 WHERE id = 1")
    suspend fun resetCounts()

    @Query("SELECT thrown FROM statistics WHERE id = 1")
    suspend fun getThrownCount(): Int

    @Query("SELECT eaten FROM statistics WHERE id = 1")
    suspend fun getEatenCount(): Int
}
