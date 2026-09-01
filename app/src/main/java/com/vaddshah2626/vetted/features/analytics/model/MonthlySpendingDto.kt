package com.vaddshah2626.vetted.features.analytics.model

data class MonthlySpendingDto(
    val yearMonth: String, // Formatted as "YYYY-MM" (e.g., "2026-08")
    val totalSpent: Double,
    val itemCount: Int
)