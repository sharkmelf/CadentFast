package com.cadent.cadentfast.timer

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.fastingDataStore by preferencesDataStore(name = "fasting")

class FastingTimerStore(private val context: Context) {

    val session: Flow<FastSession?> = context.fastingDataStore.data.map { prefs ->
        val dishId = prefs[KEY_DISH_ID] ?: return@map null
        val start = prefs[KEY_START] ?: return@map null
        val duration = prefs[KEY_DURATION] ?: return@map null
        FastSession(dishId, start, duration)
    }

    suspend fun save(session: FastSession) {
        context.fastingDataStore.edit { prefs ->
            prefs[KEY_DISH_ID] = session.dishId
            prefs[KEY_START] = session.startEpochMs
            prefs[KEY_DURATION] = session.durationMs
        }
    }

    suspend fun clear() {
        context.fastingDataStore.edit { it.clear() }
    }

    private companion object {
        val KEY_DISH_ID = stringPreferencesKey("dish_id")
        val KEY_START = longPreferencesKey("start_epoch_ms")
        val KEY_DURATION = longPreferencesKey("duration_ms")
    }
}
