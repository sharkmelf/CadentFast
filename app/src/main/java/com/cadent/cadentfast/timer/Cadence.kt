package com.cadent.cadentfast.timer

import com.cadent.cadentfast.BuildConfig

/**
 * A daily fast/feast cadence. Each cadence is two consecutive phases that repeat
 * indefinitely once the rhythm begins:
 *
 *   |---- fast ----|-- feast --|---- fast ----|-- feast --| ...
 *
 * Cadences are presented to users with maitre-d' names rather than the
 * intermittent-fasting ratio. The ratio is exposed as a subtitle for users who
 * recognize it; the name is what they live with.
 */
data class Cadence(
    val id: String,
    val name: String,
    val subtitle: String,
    val fastMs: Long,
    val feastMs: Long,
) {
    val cycleMs: Long get() = fastMs + feastMs
}

/**
 * The five named rhythms in v1 plus the debug-only dev rhythm. Ordered from
 * gentlest to most disciplined; UI surfaces them in this order in the cadence
 * picker.
 *
 * Release builds never expose the dev rhythm. The default is [Daily].
 */
object Cadences {
    private const val MIN = 60_000L
    private const val HR = 60L * MIN

    val LongLunch = Cadence(
        id = "long_lunch",
        name = "The Long Lunch",
        subtitle = "Twelve and twelve",
        fastMs = 12 * HR,
        feastMs = 12 * HR,
    )

    val Aperitif = Cadence(
        id = "aperitif",
        name = "The Apéritif",
        subtitle = "Fourteen and ten",
        fastMs = 14 * HR,
        feastMs = 10 * HR,
    )

    val Daily = Cadence(
        id = "daily",
        name = "The Daily",
        subtitle = "Sixteen and eight",
        fastMs = 16 * HR,
        feastMs = 8 * HR,
    )

    val Tasting = Cadence(
        id = "tasting",
        name = "The Tasting",
        subtitle = "Eighteen and six",
        fastMs = 18 * HR,
        feastMs = 6 * HR,
    )

    val ChefsCounter = Cadence(
        id = "chefs_counter",
        name = "The Chef's Counter",
        subtitle = "Twenty and four",
        fastMs = 20 * HR,
        feastMs = 4 * HR,
    )

    /** Debug-only dev cadence. Four-minute fasts, two-minute feasts. */
    val Dev = Cadence(
        id = "dev",
        name = "Dev",
        subtitle = "Four and two",
        fastMs = 4 * MIN,
        feastMs = 2 * MIN,
    )

    /** Cadences exposed to the user. Release builds omit [Dev]. */
    val all: List<Cadence>
        get() = if (BuildConfig.DEBUG) {
            listOf(Dev, LongLunch, Aperitif, Daily, Tasting, ChefsCounter)
        } else {
            listOf(LongLunch, Aperitif, Daily, Tasting, ChefsCounter)
        }

    /**
     * The cadence first-run uses by default. Debug builds run the dev cadence so
     * device iteration takes six minutes per cycle instead of twenty-four hours;
     * release builds run [Daily] (16:8).
     */
    val default: Cadence
        get() = if (BuildConfig.DEBUG) Dev else Daily

    fun byId(id: String): Cadence? = all.firstOrNull { it.id == id }
}
