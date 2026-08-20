package com.vaddshah2626.vetted.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SportsGymnastics
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.features.wishlist.data.ItemStatus
import java.util.Locale.getDefault

fun getIconByStatus(status: ItemStatus): ImageVector {
    return when (status) {
        ItemStatus.WISHLISTED -> Icons.Default.Person
        ItemStatus.READY -> Icons.Default.Home
        ItemStatus.PURCHASED -> Icons.Default.Work
        ItemStatus.LOST -> Icons.Default.School
        ItemStatus.BROKEN -> Icons.Default.Devices
        ItemStatus.DAMAGED -> Icons.Default.SportsGymnastics
        ItemStatus.RETIRED -> Icons.Default.DirectionsCar
        ItemStatus.USED_UP -> Icons.Default.CardGiftcard
    }
}

@Composable
fun StatusBadge(
    status: ItemStatus,
    modifier: Modifier?,
    iconTint: Color = MaterialTheme.colorScheme.onTertiary,
    textColor: Color = MaterialTheme.colorScheme.onTertiary
) {
    val vectorIcon = getIconByStatus(status)

    val rowShape = RoundedCornerShape(16.dp)

    Row(
        modifier = modifier
            ?: Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onTertiary,
                    shape = rowShape
                )
                .clip(rowShape)
                .background(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = rowShape
                )
                .padding(vertical = 4.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = vectorIcon,
            contentDescription = status.name,
            tint = iconTint,
            modifier = Modifier.size(14.dp)
        )
        Text(
            text = status.name.lowercase(getDefault()),
            color = textColor,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}