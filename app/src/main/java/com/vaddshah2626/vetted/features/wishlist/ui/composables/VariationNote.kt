package com.vaddshah2626.vetted.features.wishlist.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.ui.theme.TextTheme

@Composable
fun VariationNote(
    note : String?,
) {
    val notes = note?.split(",") ?: emptyList()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Variations",
            style = MaterialTheme.typography.bodyLarge,
            color = TextTheme.colors.textTertiary
        )
        Spacer(Modifier.height(4.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            for (n in notes) {
                Card(
                    shape = MaterialTheme.shapes.small,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                ) {
                    Text(n , modifier= Modifier.padding(12.dp , 6.dp) , color= MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    }
}