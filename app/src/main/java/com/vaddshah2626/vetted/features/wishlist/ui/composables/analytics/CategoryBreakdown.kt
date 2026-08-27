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
import com.vaddshah2626.vetted.features.wishlist.model.CategoryBreakdownDto
import com.vaddshah2626.vetted.features.wishlist.model.DesireConversionDto
import com.vaddshah2626.vetted.ui.theme.TextTheme

@Composable
fun CategoryBreakdown(
    categoryBreakdown: List<CategoryBreakdownDto>
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
                "Total money spent on each category",
                style = MaterialTheme.typography.bodyMedium,
                color = TextTheme.colors.textSecondary
            )
            Spacer(Modifier.height(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Category" , style = MaterialTheme.typography.bodySmall , color = TextTheme.colors.textSecondary)
                Text("Item Count", style = MaterialTheme.typography.bodySmall , color = TextTheme.colors.textSecondary)
                Text("Total Spent", style = MaterialTheme.typography.bodySmall , color = TextTheme.colors.textSecondary)
            }
            for (category in categoryBreakdown) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(category.categoryName, style = MaterialTheme.typography.bodyMedium , color = TextTheme.colors.textPrimary)
                    Text("${category.itemCount}", style = MaterialTheme.typography.bodyMedium , color = TextTheme.colors.textPrimary)
                    Text(category.totalSpent.toKString(), style = MaterialTheme.typography.bodyMedium , color = TextTheme.colors.textPrimary)
                }
            }
        }
    }
}