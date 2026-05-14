package com.cadent.cadentfast.ui.timer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cadent.cadentfast.catalog.Dish
import com.cadent.cadentfast.timer.FastSession
import com.cadent.cadentfast.ui.theme.Charcoal
import com.cadent.cadentfast.ui.theme.Copper
import com.cadent.cadentfast.ui.theme.Parchment
import com.cadent.cadentfast.ui.theme.ParchmentDim

@Composable
fun HeroTimerScreen(vm: HeroTimerViewModel = viewModel()) {
    val state by vm.state.collectAsStateWithLifecycle()
    when (val s = state) {
        is HeroTimerState.Idle -> IdleHero(onBegin = vm::startDefault15Minutes)
        is HeroTimerState.Running -> RunningHero(state = s, onEnd = vm::endFast)
        is HeroTimerState.Complete -> BreakFastHero(state = s, onAcknowledge = vm::endFast)
    }
}

@Composable
private fun IdleHero(onBegin: () -> Unit) {
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
                    text = "BEGIN A 15-MINUTE FAST",
                    style = MaterialTheme.typography.labelLarge,
                    color = Copper,
                )
            }
        }
    }
}

@Composable
private fun RunningHero(state: HeroTimerState.Running, onEnd: () -> Unit) {
    val progress = state.session.progress(state.nowMs)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Charcoal)
            .systemBarsPadding(),
    ) {
        DishHero(
            dish = state.dish,
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.62f),
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = state.dish.name,
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 22.sp),
                color = Parchment,
                textAlign = TextAlign.Center,
            )
            Text(
                text = formatRemaining(state.session.remainingMs(state.nowMs)),
                // Quiet enough to share the page with the dish, large enough to
                // read at arm's length, small enough that "Any moment now" sits
                // on a single line on a typical phone.
                style = MaterialTheme.typography.displayMedium.copy(
                    fontSize = 44.sp,
                    lineHeight = 50.sp,
                ),
                color = Parchment,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
            )
            Text(
                text = maitreDhintFor(progress),
                style = MaterialTheme.typography.bodyMedium,
                color = ParchmentDim,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(12.dp))
            ProgressSweep(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp),
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
private fun BreakFastHero(state: HeroTimerState.Complete, onAcknowledge: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Charcoal)
            .systemBarsPadding(),
    ) {
        DishHero(
            dish = state.dish,
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
                text = "Your ${state.dish.name} is plated.",
                // Smaller than the default headlineLarge so long dish names
                // wrap to two dignified lines instead of three orphaned ones.
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 26.sp,
                    lineHeight = 32.sp,
                ),
                color = Parchment,
                textAlign = TextAlign.Center,
            )
            TextButton(onClick = onAcknowledge) {
                Text(
                    text = "BEGIN",
                    style = MaterialTheme.typography.labelLarge,
                    color = Copper,
                )
            }
        }
    }
}

@Composable
private fun DishHero(
    dish: Dish,
    progress: Float,
    modifier: Modifier = Modifier,
) {
    // Ripening: interpolate the placeholder's center hue from cool to warm as the fast advances.
    val ripening by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1_200, easing = LinearEasing),
        label = "ripening",
    )
    val center = lerp(dish.coolBackdrop, dish.warmBackdrop, ripening.coerceIn(0f, 1f))
    val rim = lerp(Charcoal, dish.coolBackdrop, 0.6f)

    // Breathing: a slow, 1.5% scale oscillation. The dish is alive but never animated-looking.
    val infinite = rememberInfiniteTransition(label = "breath")
    val breath by infinite.animateFloat(
        initialValue = 0.992f,
        targetValue = 1.008f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6_500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "breath_scale",
    )

    Box(
        modifier = modifier.scale(breath).background(
            Brush.radialGradient(
                colors = listOf(center, rim, Charcoal),
            )
        ),
    )
}

@Composable
private fun ProgressSweep(progress: Float, modifier: Modifier = Modifier) {
    val animated by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 900, easing = LinearEasing),
        label = "sweep",
    )
    androidx.compose.foundation.Canvas(modifier = modifier) {
        val y = size.height / 2f
        drawLine(
            color = Color(0x33B87333),
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = size.height,
            cap = StrokeCap.Round,
        )
        drawLine(
            color = Copper,
            start = Offset(0f, y),
            end = Offset(size.width * animated, y),
            strokeWidth = size.height,
            cap = StrokeCap.Round,
        )
    }
}

private fun formatRemaining(remainingMs: Long): String {
    // The number ticks down concretely all the way -- "X minutes" while there
    // are minutes left, "X seconds" inside the final minute. The user always
    // sees the time remaining; no ambient phrase ever replaces the number.
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

private fun maitreDhintFor(progress: Float): String = when {
    progress < 0.1f -> "Your table is being prepared."
    progress < 0.45f -> "The wine has been opened."
    progress < 0.55f -> "Halfway to the table."
    progress < 0.85f -> "The kitchen is plating."
    progress < 0.97f -> "Almost ready."
    else -> "Any moment now."
}
