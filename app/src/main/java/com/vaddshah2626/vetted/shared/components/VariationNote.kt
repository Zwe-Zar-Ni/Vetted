package com.vaddshah2626.vetted.shared.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun VariationNote(
    note : String?,
    modifier: Modifier?
) {
   if(note == null) return
    val notes = note.split(",")
    Row(
        modifier=modifier ?: Modifier
    ) {
        for (n in notes) {
            Text(n)
        }
    }
}