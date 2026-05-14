package com.cadent.cadentfast.timer

/**
 * Phase of the daily rhythm.
 */
enum class Phase { Fast, Feast }

/**
 * A user's active fast/feast rhythm. The single source of truth for "where in
 * the cycle am I right now." Every clock and every restart computes the same
 * answer from the persisted [startEpochMs] + [cadence].
 *
 * The rhythm is derived state: there is no separate "current phase" field
 * stored anywhere. We always compute it from (now - startEpochMs) mod cycleMs.
 *
 * Swapping the locked dish during a fast updates [lockedDishId] only; the
 * timing is unchanged.
 *
 * The rhythm is ended explicitly by setting [endedEpochMs]; we never
 * auto-terminate.
 */
data class Rhythm(
    val startEpochMs: Long,
    val cadence: Cadence,
    val lockedDishId: String?,
    val endedEpochMs: Long? = null,
) {

    /** True if the rhythm has not been explicitly ended. */
    fun isActive(now: Long = System.currentTimeMillis()): Boolean =
        endedEpochMs == null || now < endedEpochMs

    /** Zero-based index of the current cycle. */
    fun cycleIndex(now: Long = System.currentTimeMillis()): Int {
        val elapsed = (now - startEpochMs).coerceAtLeast(0L)
        return (elapsed / cadence.cycleMs).toInt()
    }

    /** Milliseconds elapsed within the current cycle. */
    fun elapsedInCycleMs(now: Long = System.currentTimeMillis()): Long {
        val elapsed = (now - startEpochMs).coerceAtLeast(0L)
        return elapsed % cadence.cycleMs
    }

    /** Current phase. */
    fun phase(now: Long = System.currentTimeMillis()): Phase =
        if (elapsedInCycleMs(now) < cadence.fastMs) Phase.Fast else Phase.Feast

    /** Milliseconds remaining in the current phase. Never negative. */
    fun remainingInPhaseMs(now: Long = System.currentTimeMillis()): Long {
        val elapsedInCycle = elapsedInCycleMs(now)
        return if (elapsedInCycle < cadence.fastMs) {
            cadence.fastMs - elapsedInCycle
        } else {
            cadence.cycleMs - elapsedInCycle
        }.coerceAtLeast(0L)
    }

    /**
     * Fractional progress through the current phase, in [0f, 1f]. Used by the
     * dish-ripening curve and any phase-internal animation.
     */
    fun phaseProgress(now: Long = System.currentTimeMillis()): Float {
        val elapsedInCycle = elapsedInCycleMs(now)
        return if (elapsedInCycle < cadence.fastMs) {
            (elapsedInCycle.toFloat() / cadence.fastMs).coerceIn(0f, 1f)
        } else {
            ((elapsedInCycle - cadence.fastMs).toFloat() / cadence.feastMs)
                .coerceIn(0f, 1f)
        }
    }

    /**
     * Fractional position around the rhythm ring, in [0f, 1f]. The ring marker
     * sits at this position; it travels around the ring once per [cadence.cycleMs].
     * Zero is the start of the fast arc.
     */
    fun ringPosition(now: Long = System.currentTimeMillis()): Float =
        (elapsedInCycleMs(now).toFloat() / cadence.cycleMs).coerceIn(0f, 1f)

    /**
     * The exact epoch ms at which the next phase boundary occurs. This is what
     * the AlarmManager fires at; the receiver then re-arms forward.
     */
    fun nextBoundaryEpochMs(now: Long = System.currentTimeMillis()): Long =
        now + remainingInPhaseMs(now)

    /**
     * A new rhythm with [newCadence] applied, re-anchored so the user is at the
     * same phase boundary they were at before. We don't yank the user across
     * phases just because their cadence changed.
     *
     * The rule: under the new cadence, [startEpochMs] is moved so that "now" is
     * at the same elapsed-in-phase position. So if the user was 30 minutes into
     * a 4-hour feast under the old cadence and switches to a new cadence with a
     * 6-hour feast, they stay 30 minutes in (not yanked to a different point).
     */
    fun withCadence(newCadence: Cadence, now: Long = System.currentTimeMillis()): Rhythm {
        if (newCadence == cadence) return this
        val currentPhase = phase(now)
        val elapsedInPhase = when (currentPhase) {
            Phase.Fast -> elapsedInCycleMs(now)
            Phase.Feast -> elapsedInCycleMs(now) - cadence.fastMs
        }
        // We want: at epoch now, under newCadence, phase == currentPhase, and
        // elapsedInPhase under newCadence == old elapsedInPhase (clamped to the
        // new phase length so we don't artificially shorten).
        val targetElapsedInPhase = when (currentPhase) {
            Phase.Fast -> elapsedInPhase.coerceAtMost(newCadence.fastMs)
            Phase.Feast -> elapsedInPhase.coerceAtMost(newCadence.feastMs)
        }
        val targetElapsedInCycle = when (currentPhase) {
            Phase.Fast -> targetElapsedInPhase
            Phase.Feast -> newCadence.fastMs + targetElapsedInPhase
        }
        // newStartEpochMs is anchored so that elapsedInCycle == targetElapsedInCycle at `now`.
        val newStartEpochMs = now - targetElapsedInCycle
        return copy(startEpochMs = newStartEpochMs, cadence = newCadence)
    }
}
