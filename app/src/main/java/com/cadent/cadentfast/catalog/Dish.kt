package com.cadent.cadentfast.catalog

import androidx.compose.ui.graphics.Color

/**
 * Catalog entry. The "image" in v1 is a pair of backdrop colors used to draw
 * the placeholder hero gradient so the ripening effect can be felt without
 * commissioned photography. Real imagery swaps in over time, dish-by-dish,
 * without losing identity, lock history, or personalization data.
 *
 * Branded overrides (Phase 3) replace the generic entry for users in the
 * partner restaurant's catchment; the v1 catalog ships entirely generic.
 */
data class Dish(
    val id: String,
    val name: String,
    val description: String,
    val portion: String,
    val approxCalories: Int,
    val cuisine: Cuisine,
    val dietaryTags: Set<DietaryTag>,
    val coolBackdrop: Color,
    val warmBackdrop: Color,
)
