package com.vaddshah2626.vetted.features.analytics.ui.composables

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.features.analytics.model.MonthlySpendingDto
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.Line

@Composable
fun MonthlySpendingTrend(
    trend: List<MonthlySpendingDto>
) {

    val data = listOf(
        Line(
            label = "Monthly Spent",
            values = trend.map { it.totalSpent },
            color = SolidColor(MaterialTheme.colorScheme.secondary),
            firstGradientFillColor = MaterialTheme.colorScheme.primary.copy(0.7f),
            secondGradientFillColor = Color.Transparent,
            strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
            gradientAnimationDelay = 1000,
            drawStyle = DrawStyle.Stroke(width = 2.dp),
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            Modifier.padding(12.dp)
        ) {
            Text("Category Breakdown")
            Spacer(Modifier.height(12.dp))
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                data = data,
                animationMode = AnimationMode.Together(delayBuilder = {
                    it * 500L
                }),
            )
        }
    }
}