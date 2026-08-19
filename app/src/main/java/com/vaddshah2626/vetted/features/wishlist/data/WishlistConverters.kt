package com.vaddshah2626.vetted.features.wishlist.data

import androidx.room.TypeConverter
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Long.toFormattedDate(pattern: String = "dd MMM yyyy HH:mm"): String {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern(pattern, Locale.getDefault()))
}


class WishlistConverters {

    @TypeConverter
    fun fromItemStatus(status: ItemStatus): String = status.name

    @TypeConverter
    fun toItemStatus(value: String): ItemStatus {
        return runCatching { ItemStatus.valueOf(value) }.getOrDefault(ItemStatus.WISHLISTED)
    }
}