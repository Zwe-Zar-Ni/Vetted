package com.vaddshah2626.vetted.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vaddshah2626.vetted.features.wishlist.data.Wishlist
import com.vaddshah2626.vetted.features.wishlist.data.WishlistDao

@Database(entities = [Wishlist::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wishlistDao(): WishlistDao
}