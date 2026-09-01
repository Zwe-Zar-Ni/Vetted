package com.vaddshah2626.vetted.features.photos.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vaddshah2626.vetted.core.enums.PhotoType
import com.vaddshah2626.vetted.features.wishlist.data.Wishlist

@Entity(
    tableName = "photos",
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
data class Photo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "item_id")
    val itemId: Int,

    @ColumnInfo(name = "file_uri")
    val fileUri: String,

    @ColumnInfo(name = "photo_type")
    val photoType: PhotoType = PhotoType.PRODUCT,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)