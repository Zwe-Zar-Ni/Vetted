package com.vaddshah2626.vetted.features.history.data

import com.vaddshah2626.vetted.features.wishlist.data.Wishlist
import com.vaddshah2626.vetted.core.models.WishlistWithDetails
import kotlinx.coroutines.flow.Flow

class HistoryRepository(private val dao: HistoryDao) {

    val history: Flow<List<WishlistWithDetails>> = dao.getPurchasedWishlist()

    suspend fun updateWishlist(wishlist: Wishlist) {
        dao.updateWishlist(wishlist)
    }

    fun getItemDetails(id: Int): Flow<WishlistWithDetails> {
        val wishlist = dao.getItemDetails(id)
        return wishlist
    }

}