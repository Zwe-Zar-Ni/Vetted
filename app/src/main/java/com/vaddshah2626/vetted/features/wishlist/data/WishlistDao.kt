package com.vaddshah2626.vetted.features.wishlist.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.vaddshah2626.vetted.features.categories.data.Category
import com.vaddshah2626.vetted.features.wishlist.model.CategoryBreakdownDto
import com.vaddshah2626.vetted.features.wishlist.model.DesireConversionDto
import com.vaddshah2626.vetted.features.wishlist.model.MonthlySpendingDto
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {

    @Transaction
    @Query("SELECT * FROM wishlists WHERE status IN (:status1, :status2) ORDER BY created_at DESC")
    fun getPendingWishlist(
        status1: ItemStatus = ItemStatus.WISHLISTED,
        status2: ItemStatus = ItemStatus.READY
    ): Flow<List<WishlistWithDetails>>

    @Transaction
    @Query("SELECT * FROM wishlists WHERE status NOT In (:status1, :status2) ORDER BY purchased_at DESC")
    fun getPurchasedWishlist(
        status1: ItemStatus = ItemStatus.WISHLISTED,
        status2: ItemStatus = ItemStatus.READY
    ): Flow<List<WishlistWithDetails>>

    @Transaction
    @Query("SELECT * FROM wishlists WHERE ID=:id")
    fun getWishlistDetails(
        id: Int
    ): Flow<WishlistWithDetails>

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWishlist(wishlist: Wishlist): Long

    @Update
    suspend fun updateWishlist(wishlist: Wishlist)

    @Query("UPDATE wishlists SET status=:targetStatus WHERE status=:currentStatus AND cool_off_until < :currentTime")
    suspend fun checkWishlistsStatuses(
        targetStatus: ItemStatus = ItemStatus.READY,
        currentStatus: ItemStatus = ItemStatus.WISHLISTED,
        currentTime: Long = System.currentTimeMillis()
    )

    // ? Analytics queries

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