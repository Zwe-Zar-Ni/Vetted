package com.vaddshah2626.vetted.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
fun CategoryBadge(category: Category) {
    val vectorIcon = getIconByName(category.icon)

    val rowShape = RoundedCornerShape(12.dp)

    Row(
        modifier = Modifier
            .border(width = 1.dp, color = MaterialTheme.colorScheme.onTertiary, shape = rowShape)
            .clip(rowShape)
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = rowShape
            )
            .padding(vertical = 2.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = vectorIcon,
            contentDescription = category.name,
            tint = MaterialTheme.colorScheme.onTertiary
        )
        Text(text = category.name, color = MaterialTheme.colorScheme.onTertiary)
    }
}