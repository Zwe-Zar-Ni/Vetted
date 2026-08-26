package com.vaddshah2626.vetted.features.sources.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vaddshah2626.vetted.features.wishlist.data.Wishlist

@Entity(
    tableName = "sources",
    foreignKeys = [
        ForeignKey(
            entity = Wishlist::class,
            parentColumns = ["id"],
            childColumns = ["item_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["item_id"])]
)
data class Source(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "item_id")
    val itemId: Int,

    val title: String,

    val url: String? = null,

    val price: Double? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)