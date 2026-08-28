package com.vaddshah2626.vetted.features.analytics.data

import com.vaddshah2626.vetted.features.wishlist.model.CategoryBreakdownDto
import com.vaddshah2626.vetted.features.wishlist.model.DesireConversionDto
import com.vaddshah2626.vetted.features.wishlist.model.MonthlySpendingDto
import kotlinx.coroutines.flow.Flow

class AnalyticsRepository(private val dao: AnalyticsDao) {

    fun getAverageDaysInReady(): Flow<Double?> = dao.getAverageDaysInReady()
    fun getWishlistPipelineValue(): Flow<Double> = dao.getWishlistPipelineValue()

    fun getWishlistConversionRate(): Flow<Double> = dao.getWishlistConversionRate()

    fun getDesireRatingConversion(): Flow<List<DesireConversionDto>> =
        dao.getDesireRatingConversion()

    fun getCategoryBreakdown(): Flow<List<CategoryBreakdownDto>> =
        dao.getCategoryBreakdown()

    fun getMonthlySpendingTrend(): Flow<List<MonthlySpendingDto>> =
        dao.getMonthlySpendingTrend()
}