package com.cadent.cadentfast.timer

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.rhythmDataStore by preferencesDataStore(name = "rhythm")

/**
 * DataStore-backed persistence for the active rhythm. The rhythm lives offline
 * and survives backgrounding, kill, and reboot. We persist only the four
 * source-of-truth values; everything else (current phase, time remaining, ring
 * position) is computed from them.
 */
class RhythmStore(private val context: Context) {

    val rhythm: Flow<Rhythm?> = context.rhythmDataStore.data.map { prefs ->
        val start = prefs[KEY_START] ?: return@map null
        val cadenceId = prefs[KEY_CADENCE_ID] ?: return@map null
        val cadence = Cadences.byId(cadenceId) ?: return@map null
        Rhythm(
            startEpochMs = start,
            cadence = cadence,
            lockedDishId = prefs[KEY_LOCKED_DISH_ID],
            endedEpochMs = prefs[KEY_ENDED],
        )
    }

    /** Persist a full rhythm. Used when the rhythm begins or the cadence is changed. */
    suspend fun save(rhythm: Rhythm) {
        context.rhythmDataStore.edit { prefs ->
            prefs[KEY_START] = rhythm.startEpochMs
            prefs[KEY_CADENCE_ID] = rhythm.cadence.id
            if (rhythm.lockedDishId != null) {
                prefs[KEY_LOCKED_DISH_ID] = rhythm.lockedDishId
            } else {
                prefs.remove(KEY_LOCKED_DISH_ID)
            }
            if (rhythm.endedEpochMs != null) {
                prefs[KEY_ENDED] = rhythm.endedEpochMs
            } else {
                prefs.remove(KEY_ENDED)
            }
        }
    }

    /**
     * Atomic swap of the locked dish. Done without touching the timing so swap-
     * mid-fast preserves ripening / ring position. The receiver reads the
     * latest dish at fire time from DataStore, so any swap that lands before
     * fire wins; rapid swaps collapse harmlessly to "last write wins."
     */
    suspend fun setLockedDishId(dishId: String?) {
        context.rhythmDataStore.edit { prefs ->
            if (dishId != null) prefs[KEY_LOCKED_DISH_ID] = dishId
            else prefs.remove(KEY_LOCKED_DISH_ID)
        }
    }

    /** End the rhythm. Subsequent reads return a rhythm with [Rhythm.endedEpochMs] set. */
    suspend fun end(now: Long = System.currentTimeMillis()) {
        context.rhythmDataStore.edit { prefs ->
            prefs[KEY_ENDED] = now
        }
    }

    /** Clear everything. Used when the rhythm is fully reset (post-end-then-restart). */
    suspend fun clear() {
        context.rhythmDataStore.edit { it.clear() }
    }

    private companion object {
        val KEY_START = longPreferencesKey("rhythm_start_epoch_ms")
        val KEY_CADENCE_ID = stringPreferencesKey("rhythm_cadence_id")
        val KEY_LOCKED_DISH_ID = stringPreferencesKey("rhythm_locked_dish_id")
        val KEY_ENDED = longPreferencesKey("rhythm_ended_epoch_ms")
    }
}
