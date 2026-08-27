package com.vaddshah2626.vetted.features.wishlist.data

import kotlinx.coroutines.flow.Flow

class AnalyticsRepository(private val wishlistDao: WishlistDao) {
    val averageDaysInReady :  Flow<Double?> = wishlistDao.getAverageDaysInReady()

    val wishlistPipelineValue :  Flow<Double> = wishlistDao.getWishlistPipelineValue()

    val wishlistConversionRate :  Flow<Double> = wishlistDao.getWishlistConversionRate()
}