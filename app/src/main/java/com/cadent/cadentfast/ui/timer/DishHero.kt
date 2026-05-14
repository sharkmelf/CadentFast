package com.cadent.cadentfast.ui.timer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.lerp
import com.cadent.cadentfast.catalog.Dish
import com.cadent.cadentfast.ui.theme.Charcoal

/**
 * The dish placeholder hero: a radial gradient between the dish's cool and warm
 * backdrops, lerped by [progress] (the ripening). The breathing animation
 * oscillates at 1.5% scale on a 6.5s cycle — slow enough to feel alive, never
 * enough to feel animated. Real photography swaps in later; the data model
 * supports it without losing identity or lock history.
 *
 * [dimmed] = true reduces the overall opacity by ~30% for the feast register,
 * where the dish has already been the protagonist of a fast and is now in its
 * resting state.
 */
@Composable
fun DishHero(
    dish: Dish,
    progress: Float,
    modifier: Modifier = Modifier,
    breathing: Boolean = true,
    dimmed: Boolean = false,
) {
    val ripening by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1_200, easing = LinearEasing),
        label = "ripening",
    )
    val center = lerp(dish.coolBackdrop, dish.warmBackdrop, ripening.coerceIn(0f, 1f))
    val rim = lerp(Charcoal, dish.coolBackdrop, 0.6f)

    val scaleModifier = if (breathing) {
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
        Modifier.scale(breath)
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .then(scaleModifier)
            .alpha(if (dimmed) 0.70f else 1f)
            .background(
                Brush.radialGradient(colors = listOf(center, rim, Charcoal))
            ),
    )
}
