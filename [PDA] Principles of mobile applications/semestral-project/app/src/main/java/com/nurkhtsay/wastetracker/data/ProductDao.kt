package com.nurkhtsay.wastetracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert
    suspend fun insertProduct(product: Product): Long

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("UPDATE products SET quantity = :quantity WHERE id = :productId")
    suspend fun updateQuantity(productId: Long, quantity: String)

    @Query("DELETE FROM products WHERE id = :productId")
    suspend fun deleteProductById(productId: Long)

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Long): Product?

    @Query("SELECT * FROM products WHERE expirationDate <= :expirationDate ORDER BY expirationDate ASC")
     fun getExpiredProducts(expirationDate: Long): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE expirationDate > :expirationDate ORDER BY expirationDate ASC")
     fun getNonExpiredProducts(expirationDate: Long): Flow<List<Product>>

    @Query("SELECT * FROM products ORDER BY daysUntilDiscard ASC")
    fun getAllProducts(): Flow<List<Product>>
    @Query("SELECT * FROM products WHERE name LIKE '%' || :query || '%' ORDER BY daysUntilDiscard ASC")
    fun getAllProductsByName(query: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE expirationDate <= :expirationDate ORDER BY expirationDate ASC")
     fun getLeastFreshProducts(expirationDate: Long): Flow<List<Product>>

    @Query("SELECT * FROM products ORDER BY addedDate DESC")
     fun getLastAddedProducts(): Flow<List<Product>>

     @Query("SELECT * FROM products WHERE name LIKE '%' || :query || '%' ORDER BY addedDate DESC")
     fun getLastAddedProductsAndByName(query: String): Flow<List<Product>>

    @Query("SELECT * FROM products ORDER BY name COLLATE NOCASE ASC")
    fun getAllProductsOrderedByName(): Flow<List<Product>>
    @Query("SELECT * FROM products WHERE name LIKE '%' || :query || '%' ORDER BY name COLLATE NOCASE ASC")
    fun getAllProductsOrderedByNameAndByName(query: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE name LIKE '%' || :query || '%'")
    fun searchProductsByName(query: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE category = :category ORDER BY addedDate ASC")
    fun getLastAddedProductsByCategory(category: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE category = :category AND name LIKE '%' || :query || '%' ORDER BY addedDate DESC")
    fun getLastAddedProductsByCategoryAndName(category: String, query: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE category = :category ORDER BY name COLLATE NOCASE ASC")
    fun getAllProductsOrderedByNameAndByCategory(category: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE category = :category AND name LIKE '%' || :query || '%' ORDER BY name COLLATE NOCASE ASC")
    fun getAllProductsOrderedByNameAndByCategoryAndByName(category: String, query: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE category = :category AND name LIKE '%' || :query || '%' ORDER BY daysUntilDiscard ASC")
    fun getProductsByCategoryAndName(category: String, query: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE category = :category ORDER BY daysUntilDiscard ASC")
    fun getProductsByCategory(category: String): Flow<List<Product>>

    @Query("UPDATE products SET daysUntilDiscard = daysUntilDiscard - 1")
    suspend fun decreaseDaysUntilDiscard()
}
