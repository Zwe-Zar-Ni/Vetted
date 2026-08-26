package com.vaddshah2626.vetted.features.wishlist.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.core.db.toFormattedDate
import com.vaddshah2626.vetted.features.wishlist.data.Wishlist
import com.vaddshah2626.vetted.ui.theme.TextTheme

@Composable
fun TimelineItem(label: String, body: String, icon: ImageVector, isLastItem: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Date",
                modifier = Modifier
                    .size(28.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = CircleShape
                    )
                    .padding(8.dp)
            )
            if (!isLastItem) {
                VerticalDivider(Modifier.height(48.dp), thickness = 2.dp)
            }
        }
        Column() {
            Spacer(Modifier.height(13.dp))
            HorizontalDivider(Modifier.width(24.dp), thickness = 2.dp)
        }
        Card(
            Modifier.weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            shape = MaterialTheme.shapes.small
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    label,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTheme.colors.textTertiary
                )
                Text(
                    body,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextTheme.colors.textPrimary
                )
            }
        }
    }
}

@Composable
fun TimelineHistory(
    item: Wishlist
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Timeline")
        TimelineItem(
            label = "Wishlist At",
            body = item.createdAt.toFormattedDate(),
            icon = Icons.Default.Check
        )
        if (item.purchasedAt != null) {
            TimelineItem(
                label = "Purchased At",
                body = item.purchasedAt.toFormattedDate(),
                icon = Icons.Default.ShoppingBag,
                isLastItem = item.retiredAt == null
            )
        }
        if (item.retiredAt != null) {
            TimelineItem(
                label = "Retired At",
                body = item.retiredAt.toFormattedDate(),
                icon = Icons.Default.BrokenImage,
                isLastItem = true
            )
        }
    }
}