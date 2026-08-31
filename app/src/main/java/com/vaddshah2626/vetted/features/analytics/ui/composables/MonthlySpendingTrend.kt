package com.vaddshah2626.vetted.features.analytics.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.R
import com.vaddshah2626.vetted.core.db.toKString
import com.vaddshah2626.vetted.features.analytics.model.MonthlySpendingDto
import com.vaddshah2626.vetted.ui.theme.TextTheme

@Composable
fun MonthlySpendingTrend(
    trend: List<MonthlySpendingDto>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Box(Modifier.fillMaxWidth().padding(12.dp)) {
            Image(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = "Clock Icon",
                modifier = Modifier.size(120.dp).align(Alignment.BottomStart).graphicsLayer(alpha = 0.5f)
            )

            Column(
                Modifier.fillMaxWidth().height(250.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    "Total amount spent on items month-over-month.",
                    style = MaterialTheme.typography.bodyLarge,
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
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextTheme.colors.textSecondary
                    )
                    Text(
                        "Item Count",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextTheme.colors.textSecondary
                    )
                    Text(
                        "Total Spent",
                        style = MaterialTheme.typography.bodyMedium,
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
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextTheme.colors.textPrimary
                        )
                        Text(
                            "${tr.itemCount}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextTheme.colors.textPrimary
                        )
                        Text(
                            tr.totalSpent.toKString(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextTheme.colors.textPrimary
                        )
                    }
                }
            }
        }
    }
}