package com.nurkhtsay.wastetracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CategoryDao {

    @Insert
    suspend fun insertCategory(category: Category): Long

    @Update
    suspend fun updateCategory(category: Category)

    @Query("DELETE FROM categories WHERE id = :id AND id != 0")
    suspend fun deleteCategory(id: Long)

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: Long): Category?

    @Query("SELECT * FROM categories ORDER BY name ASC")
    suspend fun getAllCategories(): List<Category>

    @Query("SELECT EXISTS (SELECT 1 FROM categories LIMIT 1)")
    fun hasAnyCategories(): Boolean

    @Query("SELECT * FROM categories WHERE name = :name LIMIT 1")
    suspend fun getCategoryByName(name: String): Category

    suspend fun prePopulate() {

        if (!hasAnyCategories()) {
            var category = Category(0, "All")
            insertCategory(category)

            category = Category(1, "Dairy products")
            insertCategory(category)

            category = Category(2, "Meat and poultry")
            insertCategory(category)

            category = Category(3, "Fruits")
            insertCategory(category)

            category = Category(4, "Vegetables")
            insertCategory(category)
        }
    }
}
