package com.vaddshah2626.vetted.features.analytics.data

import com.vaddshah2626.vetted.features.analytics.model.CategoryBreakdownDto
import com.vaddshah2626.vetted.features.analytics.model.DesireConversionDto
import com.vaddshah2626.vetted.features.analytics.model.MonthlySpendingDto
import com.vaddshah2626.vetted.features.analytics.model.WishlistStatusCountsDto
import kotlinx.coroutines.flow.Flow

class AnalyticsRepository(private val dao: AnalyticsDao) {

    fun getWishlistAndReadyCounts() : Flow<WishlistStatusCountsDto> = dao.getWishlistAndReadyCounts()

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