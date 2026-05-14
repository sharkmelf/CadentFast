package com.cadent.cadentfast.ui.timer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cadent.cadentfast.timer.Cadence
import com.cadent.cadentfast.ui.theme.BrushedGold
import com.cadent.cadentfast.ui.theme.Copper
import com.cadent.cadentfast.ui.theme.Parchment
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * The rhythm ring: a single circle divided into two arcs proportional to the
 * cadence (fast = Copper, feast = Brushed Gold), with a small marker that sits
 * at the user's current position in the cycle and travels around the ring once
 * per cycle.
 *
 * The arcs are static; only the marker moves. At a glance the user sees both
 * where in the cycle they are right now and the rhythm itself — the same shape
 * every day, the user's position varying.
 *
 * @param ringPosition position in [0f, 1f] around the ring. 0 is the start of
 *   the fast arc; the position progresses clockwise. From [Rhythm.ringPosition].
 * @param cadence determines the proportion of fast arc to feast arc.
 * @param size diameter of the ring.
 * @param strokeWidth thickness of the arcs.
 */
@Composable
fun RhythmRing(
    ringPosition: Float,
    cadence: Cadence,
    modifier: Modifier = Modifier,
    size: Dp = 320.dp,
    strokeWidth: Dp = 4.dp,
) {
    val animated by animateFloatAsState(
        targetValue = ringPosition.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 900, easing = LinearEasing),
        label = "ring_position",
    )

    val fastFraction = cadence.fastMs.toFloat() / cadence.cycleMs.toFloat()
    val fastSweep = 360f * fastFraction
    val feastSweep = 360f * (1f - fastFraction)
    // Compose arcs sweep clockwise from 0deg (3 o'clock). We start the ring at
    // 12 o'clock (-90deg) and lay the fast arc first, then the feast arc.
    val fastStart = -90f
    val feastStart = fastStart + fastSweep

    Canvas(
        modifier = modifier.size(size),
    ) {
        val canvasSize = min(this.size.width, this.size.height)
        val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        val ringTopLeft = Offset(
            (this.size.width - canvasSize) / 2f + stroke.width / 2f,
            (this.size.height - canvasSize) / 2f + stroke.width / 2f,
        )
        val arcSize = Size(
            canvasSize - stroke.width,
            canvasSize - stroke.width,
        )

        // Fast arc — Copper. Small gap at each end via slightly inset sweeps so
        // the two arcs read as separate strokes, not one fused ring.
        val GAP_DEG = 1.5f
        drawArc(
            color = Copper,
            startAngle = fastStart + GAP_DEG / 2f,
            sweepAngle = fastSweep - GAP_DEG,
            useCenter = false,
            topLeft = ringTopLeft,
            size = arcSize,
            style = stroke,
        )

        // Feast arc — Brushed Gold.
        drawArc(
            color = BrushedGold,
            startAngle = feastStart + GAP_DEG / 2f,
            sweepAngle = feastSweep - GAP_DEG,
            useCenter = false,
            topLeft = ringTopLeft,
            size = arcSize,
            style = stroke,
        )

        // Marker. A small filled dot in Parchment that sits on the ring at the
        // user's current position. Travels around once per cycle.
        val center = Offset(
            ringTopLeft.x + arcSize.width / 2f,
            ringTopLeft.y + arcSize.height / 2f,
        )
        val radius = arcSize.width / 2f
        val angleDeg = fastStart + animated * 360f
        val angleRad = Math.toRadians(angleDeg.toDouble())
        val markerCenter = Offset(
            (center.x + radius * cos(angleRad)).toFloat(),
            (center.y + radius * sin(angleRad)).toFloat(),
        )
        val markerRadius = strokeWidth.toPx() * 1.8f
        // Soft bloom behind the marker.
        drawCircle(
            color = Color.White.copy(alpha = 0.18f),
            radius = markerRadius * 1.9f,
            center = markerCenter,
        )
        drawCircle(
            color = Parchment,
            radius = markerRadius,
            center = markerCenter,
        )
    }
}
