package com.cadent.cadentfast.catalog

import androidx.compose.ui.graphics.Color
import com.cadent.cadentfast.ui.theme.Copper
import com.cadent.cadentfast.ui.theme.Ember
import com.cadent.cadentfast.ui.theme.Oxblood
import com.cadent.cadentfast.ui.theme.RestrainedRed
import com.cadent.cadentfast.ui.theme.SmokedClay

/**
 * Placeholder catalog entry. Real catalog will carry hero imagery, grade curves
 * keyed to fast progress, and partner-license metadata. For the first slice the
 * "image" is a pair of brand colors used to draw the placeholder hero gradient
 * so the ripening effect can be felt without commissioned photography.
 */
data class Dish(
    val id: String,
    val name: String,
    val description: String,
    val portion: String,
    val approxCalories: Int,
    val coolBackdrop: Color,
    val warmBackdrop: Color,
)

object Catalog {
    val wagyu: List<Dish> = listOf(
        Dish(
            id = "a5-ribeye-tableside",
            name = "A5 Ribeye, Tableside-Seared",
            description = "Two slices, kissed on the grill for nine seconds a side. Salt, lemon, nothing else needed.",
            portion = "Two cuts (about 60 g)",
            approxCalories = 280,
            coolBackdrop = SmokedClay,
            warmBackdrop = Ember,
        ),
        Dish(
            id = "ishiyaki-wagyu",
            name = "Ishiyaki Wagyu",
            description = "A single slab, finished by the diner on a screaming-hot stone. The crust does the talking.",
            portion = "One slab (about 80 g)",
            approxCalories = 360,
            coolBackdrop = Oxblood,
            warmBackdrop = Copper,
        ),
        Dish(
            id = "toro-aburi-nigiri",
            name = "Toro Aburi Nigiri",
            description = "Fatty bluefin, seared just enough for the surface to bead and the underside to stay cool.",
            portion = "Two pieces",
            approxCalories = 180,
            coolBackdrop = SmokedClay,
            warmBackdrop = RestrainedRed,
        ),
        Dish(
            id = "wagyu-tartare",
            name = "Hand-Cut Wagyu Tartare",
            description = "Knife-cut, never ground. Cured yolk, a whisper of soy, a few crisp shallots.",
            portion = "One small plate",
            approxCalories = 220,
            coolBackdrop = Oxblood,
            warmBackdrop = Ember,
        ),
        Dish(
            id = "uni-wagyu-handroll",
            name = "Uni and Wagyu Hand Roll",
            description = "Crisp nori, warm rice, a curl of seared A5, a generous lobe of uni. Eat within sixty seconds.",
            portion = "One roll",
            approxCalories = 240,
            coolBackdrop = SmokedClay,
            warmBackdrop = Copper,
        ),
    )

    val default: Dish get() = wagyu.first()

    fun byId(id: String): Dish? = wagyu.firstOrNull { it.id == id }
}
