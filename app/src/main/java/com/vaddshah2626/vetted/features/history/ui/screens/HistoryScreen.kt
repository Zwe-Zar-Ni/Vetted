package com.vaddshah2626.vetted.features.history.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.features.history.ui.history.HistoryItem
import com.vaddshah2626.vetted.features.history.ui.viewmodels.HistoryViewModel
import com.vaddshah2626.vetted.ui.theme.TextTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = koinViewModel(),
    onItemClick: (itemId: Int) -> Unit
) {
    val history by viewModel.history.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp),
    ) {
        Text(
            "History",
            style = MaterialTheme.typography.headlineSmall,
            color = TextTheme.colors.textPrimary
        )
        Spacer(Modifier.height(12.dp))
        LazyColumn() {
            items(
                items = history,
                key = { it.item.id }
            ) { itemWithDetails ->
                HistoryItem(
                    wishlist = itemWithDetails,
                    onItemClick = { itemId -> onItemClick(itemId) }
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}