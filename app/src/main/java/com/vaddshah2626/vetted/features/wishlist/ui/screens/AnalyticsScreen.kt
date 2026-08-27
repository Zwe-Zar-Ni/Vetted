package com.vaddshah2626.vetted.features.wishlist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vaddshah2626.vetted.core.db.toKString
import com.vaddshah2626.vetted.features.wishlist.ui.viewmodels.AnalyticsViewModel
import com.vaddshah2626.vetted.ui.theme.TextTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun AnalyticsScreen(
    viewModel: AnalyticsViewModel = koinViewModel(),
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp),
    ) {
        Text(
            "Analytics",
            style = MaterialTheme.typography.headlineSmall,
            color = TextTheme.colors.textPrimary
        )
        Spacer(Modifier.height(12.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small
            ) {
                Column(
                    Modifier.padding(12.dp)
                ) {
                    Text(
                        "Average days items spend in Ready stage",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextTheme.colors.textSecondary
                    )
                    Text(
                        if (state.avgDaysInReady != null) "${state.avgDaysInReady} Days" else "N/A",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextTheme.colors.textPrimary
                    )
                }
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small
            ) {
                Column(
                    Modifier.padding(12.dp)
                ) {
                    Text(
                        "Percentage of wishlisted items that actually get bought",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextTheme.colors.textSecondary
                    )
                    Text(
                        "${state.conversionRate} %",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextTheme.colors.textPrimary
                    )
                }
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small
            ) {
                Column(
                    Modifier.padding(12.dp)
                ) {
                    Text(
                        "Total value of buy all Ready items",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextTheme.colors.textSecondary
                    )
                    Text(
                        state.pipelineValue.toKString() ?: "N/A",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextTheme.colors.textPrimary
                    )
                }
            }
        }
    }
}