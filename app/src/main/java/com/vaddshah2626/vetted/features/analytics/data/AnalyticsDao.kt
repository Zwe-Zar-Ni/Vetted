package com.vaddshah2626.vetted.features.analytics.data

import androidx.room.Dao
import androidx.room.Query
import com.vaddshah2626.vetted.features.wishlist.model.CategoryBreakdownDto
import com.vaddshah2626.vetted.features.wishlist.model.DesireConversionDto
import com.vaddshah2626.vetted.features.wishlist.model.MonthlySpendingDto
import kotlinx.coroutines.flow.Flow

@Dao
interface AnalyticsDao {

    @Query(
        """
    SELECT AVG((purchased_at - cool_off_until) / 86400000.0)
    FROM wishlists
    WHERE status = 'PURCHASED'
      AND purchased_at IS NOT NULL
      AND cool_off_until IS NOT NULL
      AND purchased_at >= cool_off_until
    """
    )
    fun getAverageDaysInReady(): Flow<Double?>

    @Query(
        """
    SELECT COALESCE(SUM(COALESCE(max_target_price, min_target_price, 0.0)), 0.0)
    FROM wishlists
    WHERE status = 'READY'
    """
    )
    fun getWishlistPipelineValue(): Flow<Double>

    @Query(
        """
    SELECT 
        CASE 
            WHEN COUNT(CASE WHEN status IN ('PURCHASED', 'USED_UP', 'BROKEN', 'DAMAGED', 'LOST', 'RETIRED', 'CANCELED') THEN 1 END) = 0 
            THEN 0.0
            ELSE (
                CAST(COUNT(CASE WHEN status IN ('PURCHASED', 'USED_UP', 'BROKEN', 'DAMAGED', 'LOST', 'RETIRED') THEN 1 END) AS REAL) * 100.0
                / COUNT(CASE WHEN status IN ('PURCHASED', 'USED_UP', 'BROKEN', 'DAMAGED', 'LOST', 'RETIRED', 'CANCELED') THEN 1 END)
            )
        END
    FROM wishlists
    """
    )
    fun getWishlistConversionRate(): Flow<Double>

    @Query(
        """
    SELECT 
        desire_rating AS desireRating,
        COUNT(CASE WHEN status IN ('PURCHASED', 'USED_UP', 'BROKEN', 'DAMAGED', 'LOST', 'RETIRED') THEN 1 END) AS purchasedCount,
        COUNT(*) AS totalCount
    FROM wishlists
    GROUP BY desire_rating
    ORDER BY desire_rating ASC
    """
    )
    fun getDesireRatingConversion(): Flow<List<DesireConversionDto>>

    @Query(
        """
    SELECT 
        c.id AS categoryId,
        c.name AS categoryName,
        COALESCE(SUM(COALESCE(i.max_target_price, i.min_target_price, 0.0)), 0.0) AS totalSpent,
        COUNT(i.id) AS itemCount
    FROM categories c
    INNER JOIN wishlists i ON c.id = i.category_id
    WHERE i.status IN ('PURCHASED', 'USED_UP', 'BROKEN', 'DAMAGED', 'LOST', 'RETIRED')
    GROUP BY c.id, c.name
    ORDER BY totalSpent DESC
    """
    )
    fun getCategoryBreakdown(): Flow<List<CategoryBreakdownDto>>

    @Query(
        """
    SELECT 
        strftime('%Y-%m', purchased_at / 1000, 'unixepoch') AS yearMonth,
        COALESCE(SUM(COALESCE(actual_price_paid, max_target_price, min_target_price, 0.0)), 0.0) AS totalSpent,
        COUNT(id) AS itemCount
    FROM wishlists
    WHERE status IN ('PURCHASED', 'USED_UP', 'BROKEN', 'DAMAGED', 'LOST', 'RETIRED')
      AND purchased_at IS NOT NULL
    GROUP BY yearMonth
    ORDER BY yearMonth ASC
    """
    )
    fun getMonthlySpendingTrend(): Flow<List<MonthlySpendingDto>>
}