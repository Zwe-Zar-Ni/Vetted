package com.vaddshah2626.vetted.features.wishlist.data

import com.vaddshah2626.vetted.features.wishlist.model.CategoryBreakdownDto
import com.vaddshah2626.vetted.features.wishlist.model.DesireConversionDto
import com.vaddshah2626.vetted.features.wishlist.model.MonthlySpendingDto
import kotlinx.coroutines.flow.Flow

class AnalyticsRepository(private val wishlistDao: WishlistDao) {

    fun getAverageDaysInReady(): Flow<Double?> = wishlistDao.getAverageDaysInReady()
    fun getWishlistPipelineValue(): Flow<Double> = wishlistDao.getWishlistPipelineValue()

    fun getWishlistConversionRate(): Flow<Double> = wishlistDao.getWishlistConversionRate()

    fun getDesireRatingConversion(): Flow<List<DesireConversionDto>> =
        wishlistDao.getDesireRatingConversion()

    fun getCategoryBreakdown(): Flow<List<CategoryBreakdownDto>> =
        wishlistDao.getCategoryBreakdown()

    fun getMonthlySpendingTrend(): Flow<List<MonthlySpendingDto>> =
        wishlistDao.getMonthlySpendingTrend()
}