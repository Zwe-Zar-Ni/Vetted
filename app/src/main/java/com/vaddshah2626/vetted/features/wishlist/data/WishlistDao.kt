package com.vaddshah2626.vetted.features.wishlist.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.vaddshah2626.vetted.features.categories.data.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {

    @Transaction
    @Query("SELECT * FROM wishlists WHERE status IN (:status1, :status2) ORDER BY created_at DESC")
    fun getAllWishlist(
        status1: ItemStatus = ItemStatus.WISHLISTED,
        status2: ItemStatus = ItemStatus.READY
    ): Flow<List<WishlistWithDetails>>

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWishlist(wishlist: Wishlist)
}