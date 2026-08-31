package com.vaddshah2626.vetted.features.analytics.model

data class DesireConversionDto(
    val desireRating: Int,
    val purchasedCount: Int,
    val totalCount: Int,
) {
    val conversionPercentage: Double
        get() = if (totalCount > 0) (purchasedCount.toDouble() / totalCount) * 100.0 else 0.0
}