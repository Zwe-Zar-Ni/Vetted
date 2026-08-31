package com.vaddshah2626.vetted.features.analytics.ui.composables

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.R
import com.vaddshah2626.vetted.core.db.toKString
import com.vaddshah2626.vetted.features.analytics.model.CategoryBreakdownDto
import com.vaddshah2626.vetted.ui.theme.TextTheme
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.GridProperties

@Composable
fun CategoryBreakdown(
    categoryBreakdown: List<CategoryBreakdownDto>
) {

    val data = categoryBreakdown.map {
        Bars(
            label = it.categoryName,
            values = listOf(
                Bars.Data(
                    label = "${it.categoryName} ${it.totalSpent.toKString()}",
                    value = it.itemCount.toDouble(),
                    color = SolidColor(MaterialTheme.colorScheme.secondary)
                )
            ),
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth().height(280.dp),
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
            if (data.isNotEmpty()) {
                ColumnChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    data = data,
                    barProperties = BarProperties(
                        spacing = 3.dp,
                        cornerRadius = Bars.Data.Radius.Rectangle(5.dp, 5.dp),
                    ),
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    gridProperties = GridProperties(
                        yAxisProperties = GridProperties.AxisProperties(
                            enabled = false
                        ),
                        xAxisProperties = GridProperties.AxisProperties(
                            color = SolidColor(
                                MaterialTheme.colorScheme.outline
                            )
                        )
                    ),
                )
            }
        }
    }
}