package com.cadent.cadentfast.ui.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cadent.cadentfast.catalog.Dish
import com.cadent.cadentfast.timer.Phase
import com.cadent.cadentfast.timer.Rhythm
import com.cadent.cadentfast.ui.theme.Charcoal
import com.cadent.cadentfast.ui.theme.Copper
import com.cadent.cadentfast.ui.theme.Parchment
import com.cadent.cadentfast.ui.theme.ParchmentDim

@Composable
fun HeroTimerScreen(vm: HeroTimerViewModel = viewModel()) {
    val state by vm.state.collectAsStateWithLifecycle()
    when (val s = state) {
        is HeroTimerState.Welcome ->
            WelcomeHero(onBegin = vm::onShowMeToMyTable)

        is HeroTimerState.ChoosingDish ->
            DishSelectionScreen(
                onDishChosen = vm::onDishChosen,
                onBack = vm::onBackFromPicker,
                initialDietaryFilters = s.initialDietaryFilters,
                headerTitle = when (s.context) {
                    ChooseContext.BeginRhythm -> "Tonight's table"
                    ChooseContext.SwapMidRhythm -> "Reconsider"
                },
                headerSubtitle = when (s.context) {
                    ChooseContext.BeginRhythm -> "What are you fasting toward?"
                    ChooseContext.SwapMidRhythm -> "Appetites change."
                },
            )

        is HeroTimerState.FastRunning ->
            RhythmHero(
                rhythm = s.rhythm,
                dish = s.dish,
                nowMs = s.nowMs,
                phase = Phase.Fast,
                onReconsider = vm::onReconsider,
                onEnd = vm::endRhythm,
            )

        is HeroTimerState.FeastRunning ->
            RhythmHero(
                rhythm = s.rhythm,
                dish = s.dish,
                nowMs = s.nowMs,
                phase = Phase.Feast,
                onReconsider = vm::onReconsider,
                onEnd = vm::endRhythm,
            )

        is HeroTimerState.BreakFastReveal ->
            BreakFastReveal(
                dish = s.dish,
                onAcknowledge = vm::acknowledgeBreakFastReveal,
            )
    }
}

@Composable
private fun WelcomeHero(onBegin: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Charcoal)
            .systemBarsPadding(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "A table for one is waiting.",
                style = MaterialTheme.typography.headlineLarge,
                color = Parchment,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(24.dp))
            TextButton(onClick = onBegin) {
                Text(
                    text = "SHOW ME TO MY TABLE",
                    style = MaterialTheme.typography.labelLarge,
                    color = Copper,
                )
            }
        }
    }
}

@Composable
private fun RhythmHero(
    rhythm: Rhythm,
    dish: Dish,
    nowMs: Long,
    phase: Phase,
    onReconsider: () -> Unit,
    onEnd: () -> Unit,
) {
    val phaseProgress = rhythm.phaseProgress(nowMs)
    val ringPosition = rhythm.ringPosition(nowMs)
    val remainingMs = rhythm.remainingInPhaseMs(nowMs)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Charcoal)
            .systemBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        ) {
            Box(
                modifier = Modifier.size(320.dp),
                contentAlignment = Alignment.Center,
            ) {
                DishHero(
                    dish = dish,
                    progress = if (phase == Phase.Fast) phaseProgress else 1f,
                    modifier = Modifier.size(240.dp),
                    dimmed = phase == Phase.Feast,
                )
                RhythmRing(
                    ringPosition = ringPosition,
                    cadence = rhythm.cadence,
                    size = 320.dp,
                )
            }

            // Dish name + tappable Reconsider affordance. Appetites change;
            // swapping mid-fast is a first-class action, not a buried setting.
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = dish.name,
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 22.sp),
                    color = Parchment,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.clickable(onClick = onReconsider),
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "Reconsider →",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 11.sp,
                        letterSpacing = 1.sp,
                    ),
                    color = Copper.copy(alpha = 0.85f),
                    modifier = Modifier.clickable(onClick = onReconsider),
                )
            }

            Text(
                text = formatRemaining(remainingMs),
                style = MaterialTheme.typography.displayMedium.copy(
                    fontSize = 44.sp,
                    lineHeight = 50.sp,
                ),
                color = Parchment,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
            )

            Text(
                text = subLineFor(phase, phaseProgress),
                style = MaterialTheme.typography.bodyMedium,
                color = ParchmentDim,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onEnd) {
                Text(
                    text = "THE TABLE WILL BE HERE TOMORROW",
                    style = MaterialTheme.typography.labelLarge,
                    color = ParchmentDim,
                )
            }
        }
    }
}

@Composable
private fun BreakFastReveal(
    dish: Dish,
    onAcknowledge: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Charcoal)
            .systemBarsPadding(),
    ) {
        DishHero(
            dish = dish,
            progress = 1f,
            modifier = Modifier.fillMaxSize(),
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Text(
                text = "Your ${dish.name} is plated.",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 26.sp,
                    lineHeight = 32.sp,
                ),
                color = Parchment,
                textAlign = TextAlign.Center,
            )
            TextButton(onClick = onAcknowledge) {
                Text(
                    text = "YOUR TABLE",
                    style = MaterialTheme.typography.labelLarge,
                    color = Copper,
                )
            }
        }
    }
}

/**
 * The number ticks down concretely all the way -- "X minutes" while there are
 * minutes left, "X seconds" inside the final minute. The user always sees the
 * time remaining; no ambient phrase ever replaces the number.
 */
private fun formatRemaining(remainingMs: Long): String {
    if (remainingMs <= 0) return "Now."
    val totalSeconds = (remainingMs + 999) / 1000
    if (totalSeconds < 60) {
        return if (totalSeconds == 1L) "1 second" else "$totalSeconds seconds"
    }
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    return when {
        hours > 0 -> "${hours}h ${minutes}m"
        minutes == 1L -> "1 minute"
        else -> "$minutes minutes"
    }
}

/** Maitre-d' sub-line; softens through the fast / lingers through the feast. */
private fun subLineFor(phase: Phase, progress: Float): String = when (phase) {
    Phase.Fast -> when {
        progress < 0.10f -> "Your table is being prepared."
        progress < 0.45f -> "The wine has been opened."
        progress < 0.55f -> "Halfway to the table."
        progress < 0.85f -> "The kitchen is plating."
        progress < 0.97f -> "Almost ready."
        else -> "The grill is lit."
    }
    Phase.Feast -> when {
        progress < 0.30f -> "Linger."
        progress < 0.70f -> "The kitchen is yours."
        else -> "The next table opens soon."
    }
}
