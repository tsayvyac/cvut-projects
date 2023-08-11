package com.nurkhtsay.wastetracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)

@Entity(
    tableName = "products"
)
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    val photo: String,
    val expirationDate: Long,
    val quantity: String,
    val measuring: String,
    val category: String,
    val daysUntilDiscard: Long,
    val addedDate: Long
)

@Entity(tableName = "statistics")
data class Statistics(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "eaten")
    var eaten: Int = 0,
    @ColumnInfo(name = "thrown")
    var thrown: Int = 0
)