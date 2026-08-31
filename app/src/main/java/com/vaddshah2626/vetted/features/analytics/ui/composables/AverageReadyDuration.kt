package com.vaddshah2626.vetted.features.analytics.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.vaddshah2626.vetted.ui.theme.TextTheme

@Composable
fun AverageReadyDuration(
    duration : Double?,
    modifier : Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Box(Modifier.fillMaxWidth().padding(12.dp)) {
            Column(
                Modifier.align(Alignment.BottomEnd),
            ) {
                Text("Average Stall" , style = MaterialTheme.typography.titleSmall , color = TextTheme.colors.textSecondary)
                Text(
                    if (duration != null) "$duration Days" else "N/A",
                    style = MaterialTheme.typography.headlineLarge,
                    color = TextTheme.colors.textPrimary
                )
                Text(
                    "Average days items spend in Ready stage",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTheme.colors.textSecondary
                )
            }
        }
    }
}