package com.vaddshah2626.vetted.features.wishlist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vaddshah2626.vetted.features.categories.data.Category
import kotlin.time.Clock


@Entity(
    tableName = "wishlists",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.RESTRICT // Prevent deleting a category if items exist inside it
        )
    ],
    indices = [Index(value = ["category_id"])]
)
data class Wishlist(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "category_id")
    val categoryId: Int,

    val name: String,

    val status: ItemStatus = ItemStatus.WISHLISTED,

    // --- Pre-Purchase / Evaluation Fields ---
    @ColumnInfo(name = "desire_rating")
    val desireRating: Int, // Range 1 to 10

    @ColumnInfo(name = "min_target_price")
    val minTargetPrice: Double,

    @ColumnInfo(name = "max_target_price")
    val maxTargetPrice: Double? = null,

    @ColumnInfo(name = "variations_note")
    val variationsNote: String? = null,

    @ColumnInfo(name = "pre_purchase_note")
    val prePurchaseNote: String? = null,

    @ColumnInfo(name = "cool_off_until")
    val coolOffUntil : Long = System.currentTimeMillis() + 172800000L, // 2 days to cool off

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    // --- Post-Purchase / Ownership Fields ---
    @ColumnInfo(name = "purchased_at")
    val purchasedAt: Long? = null,

    @ColumnInfo(name = "actual_price_paid")
    val actualPricePaid: Double? = null,

    @ColumnInfo(name = "warranty_expires_at")
    val warrantyExpiresAt: Long? = null,

    @ColumnInfo(name = "purchase_note")
    val purchaseNote: String? = null,

    // --- End-of-Life / Retirement Fields ---
    @ColumnInfo(name = "retired_at")
    val retiredAt: Long? = null,

    @ColumnInfo(name = "post_mortem_note")
    val postMortemNote: String? = null
)