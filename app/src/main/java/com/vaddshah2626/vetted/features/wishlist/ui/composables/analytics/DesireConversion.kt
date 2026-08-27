package com.vaddshah2626.vetted.features.wishlist.ui.composables.analytics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.features.wishlist.model.DesireConversionDto
import com.vaddshah2626.vetted.ui.theme.TextTheme

@Composable
fun DesireConversion(
    desireConversions: List<DesireConversionDto>
) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        ) {
            Column(
                Modifier.fillMaxWidth().padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    "Desire ratings (1–10) that result in purchases",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextTheme.colors.textSecondary
                )
                Spacer(Modifier.height(2.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Rating" , style = MaterialTheme.typography.bodySmall , color = TextTheme.colors.textSecondary)
                    Text("Total", style = MaterialTheme.typography.bodySmall , color = TextTheme.colors.textSecondary)
                    Text("Purchased", style = MaterialTheme.typography.bodySmall , color = TextTheme.colors.textSecondary)
                    Text("Percentage", style = MaterialTheme.typography.bodySmall , color = TextTheme.colors.textSecondary)
                }
                for (conversion in desireConversions) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("${conversion.desireRating}", style = MaterialTheme.typography.bodyMedium , color = TextTheme.colors.textPrimary)
                        Text("${conversion.totalCount}", style = MaterialTheme.typography.bodyMedium , color = TextTheme.colors.textPrimary)
                        Text("${conversion.purchasedCount}", style = MaterialTheme.typography.bodyMedium , color = TextTheme.colors.textPrimary)
                        Text("${conversion.conversionPercentage}%", style = MaterialTheme.typography.bodyMedium , color = TextTheme.colors.textPrimary)
                    }
                }
            }
        }
}