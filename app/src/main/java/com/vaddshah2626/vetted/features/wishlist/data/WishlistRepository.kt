package com.vaddshah2626.vetted.features.wishlist.data

import com.vaddshah2626.vetted.features.categories.data.Category
import kotlinx.coroutines.flow.Flow

class WishlistRepository(private val wishlistDao: WishlistDao) {
    val allWishlists: Flow<List<WishlistWithDetails>> = wishlistDao.getAllWishlist()

    val categories: Flow<List<Category>> = wishlistDao.getAllCategories()

    suspend fun addWishlist(wishlist: Wishlist) : Long {
        val id = wishlistDao.insertWishlist(wishlist)
        return id
    }
}