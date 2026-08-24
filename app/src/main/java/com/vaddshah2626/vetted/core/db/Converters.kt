package com.vaddshah2626.vetted.core.db

import androidx.room.TypeConverter
import com.vaddshah2626.vetted.features.photos.data.PhotoType
import com.vaddshah2626.vetted.features.wishlist.data.ItemStatus
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Long.toFormattedDate(pattern: String = "dd MMM yyyy HH:mm"): String {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern(pattern, Locale.getDefault()))
}

fun Double.toKString(): String {
    return if (this >= 1_000) "$ ${this / 1_000.0.toInt()}K" else "$ $this"
}


class Converters {

    // --- ItemStatus Converters ---
    @TypeConverter
    fun fromItemStatus(status: ItemStatus): String = status.name

    @TypeConverter
    fun toItemStatus(value: String): ItemStatus {
        return runCatching { ItemStatus.valueOf(value) }.getOrDefault(ItemStatus.WISHLISTED)
    }

    // --- PhotoType Converters ---
    @TypeConverter
    fun fromPhotoType(photoType: PhotoType): String = photoType.name

    @TypeConverter
    fun toPhotoType(value: String): PhotoType {
        return runCatching { PhotoType.valueOf(value) }.getOrDefault(PhotoType.PRODUCT)
    }
}