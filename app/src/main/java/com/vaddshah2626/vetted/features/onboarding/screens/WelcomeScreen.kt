package com.vaddshah2626.vetted.features.onboarding.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vaddshah2626.vetted.features.categories.ui.CategoryViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WelcomeScreen(
    viewModel: CategoryViewModel = koinViewModel(),
    onContinue: () -> Unit
) {

    val categories by viewModel.categories.collectAsState()

    LaunchedEffect(categories) {
        if(categories == null) {
            return@LaunchedEffect;
        }
        if (categories!!.isEmpty()) {
            viewModel.seedInitialCategories()
        } else {
            onContinue()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Welcome",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onContinue,
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.errorContainer,
                disabledContentColor = MaterialTheme.colorScheme.onErrorContainer
            )
        ) {
            Text("Continue")
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Arrow Right"
            )
        }
    }
}