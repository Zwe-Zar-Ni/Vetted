package com.vaddshah2626.vetted.features.wishlist.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.vaddshah2626.vetted.core.db.toFormattedDate
import com.vaddshah2626.vetted.features.wishlist.data.ItemStatus
import com.vaddshah2626.vetted.features.wishlist.data.WishlistWithDetails
import com.vaddshah2626.vetted.shared.components.CategoryBadge
import com.vaddshah2626.vetted.shared.components.StatusBadge
import com.vaddshah2626.vetted.shared.components.VariationNote
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CategoryBadge(wishlist.category, null, false)
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(wishlist.item.name, style = MaterialTheme.typography.titleMedium)
                    Text(
                        "${wishlist.item.desireRating}/10",
                        style = MaterialTheme.typography.bodySmall
                    )
                    if (wishlist.item.prePurchaseNote?.isNotEmpty() ?: false) {
                        Text(
                            wishlist.item.prePurchaseNote,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    StatusBadge(wishlist.item.status, null)
                    if (wishlist.item.status == ItemStatus.WISHLISTED) {
                        Text(
                            "Ready at ${wishlist.item.coolOffUntil.toFormattedDate()}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    VariationNote(wishlist.item.variationsNote, null)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (photo in wishlist.photos) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(File(photo.fileUri))
                            .crossfade(true)
                            .build(),
                        contentDescription = "Photo ${photo.id}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                for (src in wishlist.sources) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(src.title)
                        Text(if (src.price != null) src.price.toString() else "")
                    }
                }
            }
        }
    }
    Spacer(Modifier.height(16.dp))
}