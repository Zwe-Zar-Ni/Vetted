package com.vaddshah2626.vetted.features.wishlist.data

import com.vaddshah2626.vetted.features.categories.data.Category
import kotlinx.coroutines.flow.Flow

class WishlistRepository(private val wishlistDao: WishlistDao) {
    val allWishlists: Flow<List<WishlistWithDetails>> = wishlistDao.getPendingWishlist()

    val history: Flow<List<WishlistWithDetails>> = wishlistDao.getPurchasedWishlist()

    val categories: Flow<List<Category>> = wishlistDao.getAllCategories()

    suspend fun addWishlist(wishlist: Wishlist) : Long {
        val id = wishlistDao.insertWishlist(wishlist)
        return id
    }

    suspend fun updateWishlist(wishlist: Wishlist) {
        wishlistDao.updateWishlist(wishlist)
    }

    fun getWishlistDetails(id : Int) : Flow<WishlistWithDetails> {
        val wishlist = wishlistDao.getWishlistDetails(id)
        return wishlist
    }

    suspend fun checkWishlistsStatuses() {
        wishlistDao.checkWishlistsStatuses()
    }
}