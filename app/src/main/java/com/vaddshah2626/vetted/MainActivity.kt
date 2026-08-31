package com.vaddshah2626.vetted

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vaddshah2626.vetted.core.navigation.AppNavigation
import com.vaddshah2626.vetted.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val initialRoute = handleWidgetIntent(intent)
        println(initialRoute)

        enableEdgeToEdge()
        setContent {
            AppTheme {
                AppNavigation(initialRoute)
            }
        }
    }

    private fun handleWidgetIntent(intent: Intent?): String {
        return if (intent?.getStringExtra("INITIAL_ROUTE") == "create_wishlist") {
            "create_wishlist"
        } else if(intent?.getStringExtra("INITIAL_ROUTE") == "analytics") {
            "analytics"
        } else {
            "welcome_route"
        }
    }

}