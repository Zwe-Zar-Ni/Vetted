package com.vaddshah2626.vetted.features.wishlist.data

import com.vaddshah2626.vetted.core.models.WishlistWithDetails
import com.vaddshah2626.vetted.features.categories.data.Category
import kotlinx.coroutines.flow.Flow

class WishlistRepository(private val dao: WishlistDao) {
    val allWishlists: Flow<List<WishlistWithDetails>> = dao.getPendingWishlist()

    val categories: Flow<List<Category>> = dao.getAllCategories()

    val currentMonthSpending : Flow<Double> = dao.getCurrentMonthSpending()

    suspend fun addWishlist(wishlist: Wishlist) : Long {
        val id = dao.insertWishlist(wishlist)
        return id
    }

    suspend fun updateWishlist(wishlist: Wishlist) {
        dao.updateWishlist(wishlist)
    }

    fun getWishlistDetails(id : Int) : Flow<WishlistWithDetails> {
        val wishlist = dao.getWishlistDetails(id)
        return wishlist
    }

    suspend fun checkWishlistsStatuses() {
        dao.checkWishlistsStatuses()
    }

}