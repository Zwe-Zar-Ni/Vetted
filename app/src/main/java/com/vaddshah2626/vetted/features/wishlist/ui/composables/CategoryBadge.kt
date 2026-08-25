package com.vaddshah2626.vetted.features.wishlist.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Category
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
import com.vaddshah2626.vetted.features.categories.data.Category


fun getIconByName(iconName: String?): ImageVector {
    return when (iconName) {
        "personal" -> Icons.Default.Person
        "home" -> Icons.Default.Home
        "work" -> Icons.Default.Work
        "school" -> Icons.Default.School
        "gadgets" -> Icons.Default.Devices
        "hobby" -> Icons.Default.SportsGymnastics
        "car" -> Icons.Default.DirectionsCar
        "gift" -> Icons.Default.CardGiftcard
        "others" -> Icons.Default.Category
        else -> Icons.Default.Category
    }
}

@Composable
fun CategoryBadge(
    category: Category,
    modifier: Modifier?,
    withText: Boolean = true,
    iconTint: Color = MaterialTheme.colorScheme.onTertiary,
    textColor: Color = MaterialTheme.colorScheme.onTertiary
) {
    val vectorIcon = getIconByName(category.icon)

    val rowShape = RoundedCornerShape(16.dp)

    Row(
        modifier = modifier
            ?: Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSecondary,
                    shape = rowShape
                )
                .clip(rowShape)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = rowShape
                )
                .padding(vertical = 4.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = vectorIcon,
            contentDescription = category.name,
            tint = iconTint,
            modifier = Modifier.size(12.dp)
        )
        if(withText) {
            Text(
                text = category.name,
                color = textColor,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}