package com.cadent.cadentfast.timer

data class FastSession(
    val dishId: String,
    val startEpochMs: Long,
    val durationMs: Long,
) {
    val endEpochMs: Long get() = startEpochMs + durationMs

    fun remainingMs(nowMs: Long = System.currentTimeMillis()): Long =
        (endEpochMs - nowMs).coerceAtLeast(0L)

    fun elapsedMs(nowMs: Long = System.currentTimeMillis()): Long =
        (nowMs - startEpochMs).coerceIn(0L, durationMs)

    fun progress(nowMs: Long = System.currentTimeMillis()): Float =
        if (durationMs <= 0) 1f else (elapsedMs(nowMs).toFloat() / durationMs).coerceIn(0f, 1f)

    fun isComplete(nowMs: Long = System.currentTimeMillis()): Boolean =
        nowMs >= endEpochMs
}
