package com.cadent.cadentfast.catalog

/**
 * The v1 cuisine cohort. Six cuisines, art-directed and authentic — no
 * inventions. Order in this enum is the order chips appear in the picker.
 */
enum class Cuisine(val displayName: String) {
    Japanese("Japanese"),
    KoreanBbq("Korean BBQ"),
    Lebanese("Lebanese"),
    Indian("Indian"),
    Armenian("Armenian"),
    Greek("Greek"),
}

/**
 * Cross-cutting dietary register tags applied per dish. A dish may carry zero,
 * one, two, or three of these.
 *
 * - [Mediterranean] — olive-oil-forward, fish-and-vegetable-heavy,
 *   grain-and-legume-inclusive.
 * - [Vegetarian] — no meat, no fish, no shellfish. Dairy and eggs OK.
 * - [ProteinForward] — approximately <15g net carbs per single serving. The
 *   label the keto-IF crowd recognizes; surfaced as "Protein-forward" in the
 *   chip row, not "Keto-friendly," because "keto" is wellness-app vocabulary.
 */
enum class DietaryTag(val displayName: String) {
    Mediterranean("Mediterranean"),
    Vegetarian("Vegetarian"),
    ProteinForward("Protein-forward"),
}
