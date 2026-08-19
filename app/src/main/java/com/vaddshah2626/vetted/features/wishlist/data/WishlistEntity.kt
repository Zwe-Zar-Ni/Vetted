package com.vaddshah2626.vetted.features.wishlist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlists")
data class Wishlist(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)