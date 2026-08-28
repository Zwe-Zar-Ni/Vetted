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
import com.vaddshah2626.vetted.core.db.toKString
import com.vaddshah2626.vetted.features.wishlist.model.MonthlySpendingDto
import com.vaddshah2626.vetted.ui.theme.TextTheme

@Composable
fun MonthlySpendingTrend(
    trend: List<MonthlySpendingDto>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                "Total amount spent on items month-over-month.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextTheme.colors.textSecondary
            )
            Spacer(Modifier.height(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Month",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTheme.colors.textSecondary
                )
                Text(
                    "Item Count",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTheme.colors.textSecondary
                )
                Text(
                    "Total Spent",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTheme.colors.textSecondary
                )
            }
            for (tr in trend) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        tr.yearMonth,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextTheme.colors.textPrimary
                    )
                    Text(
                        "${tr.itemCount}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextTheme.colors.textPrimary
                    )
                    Text(
                        tr.totalSpent.toKString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextTheme.colors.textPrimary
                    )
                }
            }
        }
    }
}