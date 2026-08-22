package com.vaddshah2626.vetted.features.wishlist.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.features.wishlist.data.ItemStatus
import com.vaddshah2626.vetted.features.wishlist.data.WishlistWithDetails
import com.vaddshah2626.vetted.features.wishlist.data.toFormattedDate
import com.vaddshah2626.vetted.shared.components.CategoryBadge
import com.vaddshah2626.vetted.shared.components.StatusBadge
import com.vaddshah2626.vetted.shared.components.VariationNote

@Composable
fun WishlistCard(
    wishlist: WishlistWithDetails
) {
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
                    Text("${wishlist.item.desireRating}/5", style = MaterialTheme.typography.bodySmall)
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
                    VariationNote(wishlist.item.variationsNote , null)
                }
            }
        }
    }
    Spacer(Modifier.height(16.dp))
}