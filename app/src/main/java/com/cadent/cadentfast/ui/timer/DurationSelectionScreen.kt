package com.cadent.cadentfast.ui.timer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cadent.cadentfast.catalog.Dish
import com.cadent.cadentfast.ui.theme.CharcoalRaised
import com.cadent.cadentfast.ui.theme.Copper
import com.cadent.cadentfast.ui.theme.Parchment
import com.cadent.cadentfast.ui.theme.ParchmentDim

private data class DurationPreset(
    val label: String,
    val helper: String,
    val durationMs: Long,
)

private val PRESETS = listOf(
    DurationPreset(
        label = "3.5 minutes",
        helper = "a tasting course",
        durationMs = 3 * 60_000L + 30_000L,
    ),
    DurationPreset(
        label = "1 hour",
        helper = "a kitchen pace",
        durationMs = 60 * 60_000L,
    ),
    DurationPreset(
        label = "4 hours",
        helper = "the long table",
        durationMs = 4 * 60 * 60_000L,
    ),
)

@Composable
fun DurationSelectionScreen(
    dish: Dish,
    onDurationChosen: (Long) -> Unit,
    onBack: () -> Unit,
) {
    BackHandler(onBack = onBack)

    val haptics = LocalHapticFeedback.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(com.cadent.cadentfast.ui.theme.Charcoal)
            .systemBarsPadding()
            .padding(horizontal = 24.dp, vertical = 32.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "How long until",
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 22.sp),
                color = ParchmentDim,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = dish.name,
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 26.sp, lineHeight = 30.sp),
                color = Parchment,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(40.dp))

            PRESETS.forEach { preset ->
                DurationCard(
                    preset = preset,
                    onClick = {
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        onDurationChosen(preset.durationMs)
                    },
                )
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun DurationCard(preset: DurationPreset, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CharcoalRaised)
            .border(
                width = 0.5.dp,
                color = Copper.copy(alpha = 0.4f),
                shape = RoundedCornerShape(16.dp),
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = preset.label,
            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 32.sp),
            color = Parchment,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = preset.helper,
            style = MaterialTheme.typography.bodyMedium,
            color = ParchmentDim,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
        )
    }
}
