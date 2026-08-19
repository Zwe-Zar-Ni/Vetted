package com.vaddshah2626.vetted.features.wishlist.data

import kotlinx.coroutines.flow.Flow

class WishlistRepository(private val wishlistDao: WishlistDao) {
    val allWishlists: Flow<List<WishlistWithDetails>> = wishlistDao.getAllWishlist()

    suspend fun addWishlist(wishlist: Wishlist) {
        wishlistDao.insertWishlist(wishlist)
    }
}