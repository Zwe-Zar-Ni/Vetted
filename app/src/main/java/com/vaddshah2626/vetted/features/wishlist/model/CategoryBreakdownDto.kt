package com.vaddshah2626.vetted.features.wishlist.model

data class CategoryBreakdownDto(
    val categoryId: Long,
    val categoryName: String,
    val totalSpent: Double,
    val itemCount: Int
)