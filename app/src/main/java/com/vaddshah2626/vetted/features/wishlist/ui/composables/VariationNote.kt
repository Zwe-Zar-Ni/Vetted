package com.vaddshah2626.vetted.features.wishlist.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VariationNote(
    note : String?,
    modifier: Modifier?
) {
   if(note == null) return
    val notes = note.split(",")
    Row(
        modifier=modifier ?: Modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        for (n in notes) {
            Card(
                shape = MaterialTheme.shapes.small,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Text(n , modifier= Modifier.padding(12.dp , 6.dp) , color= MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}