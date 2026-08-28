package com.vaddshah2626.vetted.features.history.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.vaddshah2626.vetted.core.enums.ItemStatus
import com.vaddshah2626.vetted.features.wishlist.data.Wishlist
import com.vaddshah2626.vetted.core.models.WishlistWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Transaction
    @Query("SELECT * FROM wishlists WHERE status NOT In (:status1, :status2, :status3) ORDER BY purchased_at DESC")
    fun getPurchasedWishlist(
        status1: ItemStatus = ItemStatus.WISHLISTED,
        status2: ItemStatus = ItemStatus.READY,
        status3: ItemStatus = ItemStatus.CANCELED
    ): Flow<List<WishlistWithDetails>>

    @Transaction
    @Query("SELECT * FROM wishlists WHERE ID=:id")
    fun getItemDetails(
        id: Int
    ): Flow<WishlistWithDetails>

    @Update
    suspend fun updateWishlist(wishlist: Wishlist)

}