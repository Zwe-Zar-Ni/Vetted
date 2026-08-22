package com.vaddshah2626.vetted.features.wishlist.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.vaddshah2626.vetted.core.db.toFormattedDate
import com.vaddshah2626.vetted.core.db.toKString
import com.vaddshah2626.vetted.features.wishlist.data.ItemStatus
import com.vaddshah2626.vetted.features.wishlist.data.WishlistWithDetails
import java.io.File

@Composable
fun WishlistCard(
    wishlist: WishlistWithDetails
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (wishlist.photos.isNotEmpty()) {
                val photo = wishlist.photos[0]
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            shape = MaterialTheme.shapes.small
                        )
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(File(photo.fileUri))
                            .crossfade(true)
                            .build(),
                        contentDescription = "Photo ${photo.id}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .align(Alignment.Center)
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(wishlist.item.name, style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(2.dp))
                    Text(
                        "\uD83D\uDD25 ${wishlist.item.desireRating} / 10",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = CircleShape
                            )
                            .padding(8.dp, 4.dp)
                    )
                    Row {
                        Text("$ ", style = MaterialTheme.typography.bodySmall)
                        Text(
                            wishlist.item.minTargetPrice.toKString(),
                            style = MaterialTheme.typography.bodySmall
                        )
                        if (wishlist.item.maxTargetPrice != null) {
                            Text(" - ", style = MaterialTheme.typography.bodySmall)
                            Text(
                                wishlist.item.maxTargetPrice.toKString(),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    CategoryBadge(wishlist.category, null)
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = when (wishlist.item.status) {
                            ItemStatus.WISHLISTED -> "READY IN"
                            ItemStatus.READY -> "READY TO BUY"
                            else -> ""
                        },
                        color = when (wishlist.item.status) {
                            ItemStatus.WISHLISTED -> MaterialTheme.colorScheme.error
                            ItemStatus.READY -> Color.Green
                            else -> MaterialTheme.colorScheme.onSurface
                        },
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    if (wishlist.item.status == ItemStatus.WISHLISTED) {
                        Text(
                            wishlist.item.coolOffUntil.toFormattedDate(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}