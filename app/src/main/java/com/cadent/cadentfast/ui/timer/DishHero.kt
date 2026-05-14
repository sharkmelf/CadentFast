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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.lerp
import com.cadent.cadentfast.catalog.Dish
import com.cadent.cadentfast.ui.theme.Charcoal

/**
 * The dish placeholder hero: radial gradient between the dish's cool and warm
 * backdrops, lerped by `progress` (the ripening). Optionally breathes -- a slow
 * 1.5% scale oscillation that makes the dish feel alive without ever looking
 * animated. Dish-selection cells render this with `breathing = false` because
 * a wall of breathing thumbnails would feel wrong; the running and break-fast
 * screens render it with breathing on.
 */
@Composable
fun DishHero(
    dish: Dish,
    progress: Float,
    modifier: Modifier = Modifier,
    breathing: Boolean = true,
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
            .background(
                Brush.radialGradient(colors = listOf(center, rim, Charcoal))
            ),
    )
}
