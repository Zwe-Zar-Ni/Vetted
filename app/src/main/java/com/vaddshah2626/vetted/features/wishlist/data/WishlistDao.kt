package com.vaddshah2626.vetted.features.wishlist.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.vaddshah2626.vetted.features.categories.data.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {

    @Transaction
    @Query("SELECT * FROM wishlists WHERE status IN (:status1, :status2) ORDER BY created_at DESC")
    fun getPendingWishlist(
        status1: ItemStatus = ItemStatus.WISHLISTED,
        status2: ItemStatus = ItemStatus.READY
    ): Flow<List<WishlistWithDetails>>

    @Transaction
    @Query("SELECT * FROM wishlists WHERE status NOT In (:status1, :status2) ORDER BY purchased_at DESC")
    fun getPurchasedWishlist(
        status1: ItemStatus = ItemStatus.WISHLISTED,
        status2: ItemStatus = ItemStatus.READY
    ): Flow<List<WishlistWithDetails>>

    @Transaction
    @Query("SELECT * FROM wishlists WHERE ID=:id")
    fun getWishlistDetails(
        id: Int
    ): Flow<WishlistWithDetails>

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWishlist(wishlist: Wishlist): Long

    @Update
    suspend fun updateWishlist(wishlist: Wishlist)

    @Query("UPDATE wishlists SET status=:targetStatus WHERE status=:currentStatus AND created_at < :currentTime")
    suspend fun checkWishlistsStatuses(
        targetStatus: ItemStatus = ItemStatus.READY, currentStatus: ItemStatus =
            ItemStatus.WISHLISTED, currentTime: Long = System.currentTimeMillis()
    )
}