package com.vaddshah2626.vetted.widget

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.components.CircleIconButton
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.vaddshah2626.vetted.MainActivity
import com.vaddshah2626.vetted.R
import com.vaddshah2626.vetted.features.analytics.data.AnalyticsRepository
import com.vaddshah2626.vetted.features.analytics.model.WishlistStatusCountsDto
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

val parameterKey = ActionParameters.Key<String>("INITIAL_ROUTE")

class MyGlanceWidget : GlanceAppWidget(), KoinComponent {

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    private val analyticsRepository: AnalyticsRepository by inject()


    @SuppressLint("RestrictedApi")
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val pipelineValue by produceState(initialValue = WishlistStatusCountsDto()) {
                analyticsRepository.getWishlistAndReadyCounts().collect { value ->
                    this.value = value
                }
            }
            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(Color(0xFF181818)).padding(8.dp),
            ) {
                Row(
                    modifier = GlanceModifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Wishlisted : ",
                        style = TextStyle(color = ColorProvider(Color.White)),
                        modifier = GlanceModifier.defaultWeight()
                    )
                    Text(
                        text = "${pipelineValue.wishlistedCount}",
                        style = TextStyle(color = ColorProvider(Color.White) , fontSize = 21.sp),
                    )
                }
                Row(
                    modifier = GlanceModifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Ready : ",
                        style = TextStyle(color = ColorProvider(Color.White)),
                        modifier = GlanceModifier.defaultWeight()
                    )
                    Text(
                        text = "${pipelineValue.readyCount}",
                        style = TextStyle(color = ColorProvider(Color.White) , fontSize = 21.sp),
                    )
                }
                Spacer(GlanceModifier.height(8.dp))
                Row(
                    modifier=GlanceModifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircleIconButton(
                        imageProvider = ImageProvider(R.drawable.line),
                        contentDescription = "Analytics",
                        onClick = actionStartActivity<MainActivity>(
                            actionParametersOf(parameterKey to "analytics")
                        ),
                        backgroundColor = ColorProvider(Color.Transparent),
                        contentColor = ColorProvider(Color.LightGray),
                    )
                    Spacer(GlanceModifier.width(8.dp))
                    CircleIconButton(
                        imageProvider = ImageProvider(R.drawable.plus),
                        contentDescription = "Create",
                        onClick = actionStartActivity<MainActivity>(
                            actionParametersOf(parameterKey to "create_wishlist")
                        ),
                        backgroundColor = ColorProvider(Color.Transparent),
                        contentColor = ColorProvider(Color.LightGray),
                    )
                }
            }
        }
    }
}
