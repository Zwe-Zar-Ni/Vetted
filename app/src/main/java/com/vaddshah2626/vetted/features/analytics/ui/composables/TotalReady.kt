package com.vaddshah2626.vetted.features.analytics.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.R
import com.vaddshah2626.vetted.core.db.toKString
import com.vaddshah2626.vetted.ui.theme.TextTheme

@Composable
fun TotalReady(
    value : Double
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Box(Modifier.fillMaxWidth().height(100.dp).padding(12.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ready),
                contentDescription = "Clock Icon",
                modifier = Modifier.size(100.dp).align(Alignment.TopEnd).rotate(15f).graphicsLayer(alpha = 0.7f)
            )
            Column(
                Modifier.align(Alignment.BottomStart)
            ) {
                Text(
                    value.toKString(),
                    style = MaterialTheme.typography.headlineLarge,
                    color = TextTheme.colors.textPrimary
                )
                Text(
                    "Total value of buy all Ready items",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextTheme.colors.textSecondary
                )
            }
        }
    }
}