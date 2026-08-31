package com.vaddshah2626.vetted.features.analytics.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vaddshah2626.vetted.features.analytics.ui.composables.AverageReadyDuration
import com.vaddshah2626.vetted.features.analytics.ui.composables.CategoryBreakdown
import com.vaddshah2626.vetted.features.analytics.ui.composables.ConversionRate
import com.vaddshah2626.vetted.features.analytics.ui.composables.DesireConversion
import com.vaddshah2626.vetted.features.analytics.ui.composables.MonthlySpendingTrend
import com.vaddshah2626.vetted.features.analytics.ui.composables.TotalReady
import com.vaddshah2626.vetted.features.analytics.ui.viewmodels.AnalyticsViewModel
import com.vaddshah2626.vetted.ui.theme.TextTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun AnalyticsScreen(
    viewModel: AnalyticsViewModel = koinViewModel(),
) {

    val avgDays by viewModel.avgDaysInReady.collectAsStateWithLifecycle()
    val pipelineValue by viewModel.pipelineValue.collectAsStateWithLifecycle()
    val conversionRate by viewModel.conversionRate.collectAsStateWithLifecycle()

    val desireConversions by viewModel.desireConversions.collectAsStateWithLifecycle()
    val categoryBreakdown by viewModel.categoryBreakdown.collectAsStateWithLifecycle()
    val monthlySpendingTrend by viewModel.monthlySpendingTrend.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp),
    ) {
        item {
            Text(
                "Analytics",
                style = MaterialTheme.typography.headlineSmall,
                color = TextTheme.colors.textPrimary
            )
            Spacer(Modifier.height(12.dp))
        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TotalReady(pipelineValue)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    ConversionRate(conversionRate, Modifier.weight(1f))
                    AverageReadyDuration(avgDays, Modifier.weight(1f))
                }
            }
        }

        item {
            Spacer(Modifier.height(8.dp))
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DesireConversion(desireConversions)
                CategoryBreakdown(categoryBreakdown)
                MonthlySpendingTrend(monthlySpendingTrend)
            }
        }
    }
}