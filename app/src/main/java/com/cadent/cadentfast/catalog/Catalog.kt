package com.cadent.cadentfast.catalog

import com.cadent.cadentfast.ui.theme.BrushedGold
import com.cadent.cadentfast.ui.theme.Copper
import com.cadent.cadentfast.ui.theme.Ember
import com.cadent.cadentfast.ui.theme.Oxblood
import com.cadent.cadentfast.ui.theme.RestrainedRed
import com.cadent.cadentfast.ui.theme.SmokedClay

/**
 * The v1 catalog. Six cuisines, ~50 authentic dishes. Each entry real and
 * traceable to its cuisine — no inventions, no constructed "CadentFast-style"
 * registers. Portions are single-serving; calories are approximate per
 * `CLAUDE.md` rules.
 *
 * The descriptions here are placeholder maître-d' voice — the copywriter
 * polishes them in a later pass. They hold the register from day one so the
 * catalog never reads clinical.
 *
 * Backdrop colors are chosen per dish to feed the radial-gradient placeholder.
 * Real photography replaces the gradient dish-by-dish over time.
 *
 * See `CATALOG.md` at the repo root for the editorial source of truth and the
 * commission-from-day-one shortlist.
 */
object Catalog {

    val all: List<Dish> = buildList {
        // === Japanese ===
        add(Dish(
            id = "tamago-kake-gohan",
            name = "Tamago Kake Gohan",
            description = "Raw egg over hot rice, a thread of shoyu. The simplest plate; nothing to hide behind.",
            portion = "One egg over 180 g rice",
            approxCalories = 380,
            cuisine = Cuisine.Japanese,
            dietaryTags = emptySet(),
            coolBackdrop = SmokedClay,
            warmBackdrop = BrushedGold,
        ))
        add(Dish(
            id = "yaki-zakana-teishoku",
            name = "Yaki Zakana Teishoku",
            description = "Grilled salted mackerel, miso soup, rice on the side. The fish does the work.",
            portion = "120 g fish + sides",
            approxCalories = 420,
            cuisine = Cuisine.Japanese,
            dietaryTags = setOf(DietaryTag.Mediterranean),
            coolBackdrop = SmokedClay,
            warmBackdrop = Ember,
        ))
        add(Dish(
            id = "tamagoyaki",
            name = "Tamagoyaki",
            description = "Rolled dashi omelette, three slices. Layered, faintly sweet, set just past wet.",
            portion = "120 g, three slices",
            approxCalories = 220,
            cuisine = Cuisine.Japanese,
            dietaryTags = setOf(DietaryTag.Vegetarian, DietaryTag.ProteinForward),
            coolBackdrop = SmokedClay,
            warmBackdrop = BrushedGold,
        ))
        add(Dish(
            id = "wagyu-yakiniku-don",
            name = "Wagyu Yakiniku-Don",
            description = "Sliced A5 over rice, soy-mirin glaze, scallion. The fat finishes itself in the bowl.",
            portion = "180 g rice + 120 g A5",
            approxCalories = 780,
            cuisine = Cuisine.Japanese,
            dietaryTags = emptySet(),
            coolBackdrop = Oxblood,
            warmBackdrop = Ember,
        ))
        add(Dish(
            id = "chirashi",
            name = "Chirashi",
            description = "A scatter of the day's best cuts over sushi rice. Read like a menu.",
            portion = "220 g",
            approxCalories = 560,
            cuisine = Cuisine.Japanese,
            dietaryTags = emptySet(),
            coolBackdrop = SmokedClay,
            warmBackdrop = RestrainedRed,
        ))
        add(Dish(
            id = "tonkotsu-ramen",
            name = "Tonkotsu Ramen",
            description = "Pork-bone broth pulled white from a long boil. Noodles barely firm. Egg, chashu, scallion.",
            portion = "One bowl, ~500 g",
            approxCalories = 720,
            cuisine = Cuisine.Japanese,
            dietaryTags = emptySet(),
            coolBackdrop = SmokedClay,
            warmBackdrop = Copper,
        ))
        add(Dish(
            id = "a5-ribeye-tableside",
            name = "A5 Ribeye, Tableside-Seared",
            description = "Two slices, kissed on the grill for nine seconds a side. Salt, lemon, nothing else needed.",
            portion = "150 g (no rice)",
            approxCalories = 620,
            cuisine = Cuisine.Japanese,
            dietaryTags = setOf(DietaryTag.ProteinForward),
            coolBackdrop = SmokedClay,
            warmBackdrop = Ember,
        ))
        add(Dish(
            id = "toro-aburi-nigiri",
            name = "Toro Aburi Nigiri",
            description = "Fatty bluefin, seared just enough for the surface to bead and the underside to stay cool.",
            portion = "Five pieces (120 g)",
            approxCalories = 340,
            cuisine = Cuisine.Japanese,
            dietaryTags = emptySet(),
            coolBackdrop = SmokedClay,
            warmBackdrop = RestrainedRed,
        ))
        add(Dish(
            id = "ishiyaki-wagyu",
            name = "Ishiyaki Wagyu",
            description = "A single slab finished by the diner on a screaming-hot stone. The crust does the talking.",
            portion = "140 g + vegetables",
            approxCalories = 540,
            cuisine = Cuisine.Japanese,
            dietaryTags = setOf(DietaryTag.ProteinForward),
            coolBackdrop = Oxblood,
            warmBackdrop = Copper,
        ))

        // === Korean BBQ ===
        add(Dish(
            id = "seolleongtang",
            name = "Seolleongtang",
            description = "Ox-bone broth pulled to a pale white through hours of patience. Brisket sliced thin; rice held.",
            portion = "One bowl, ~500 g",
            approxCalories = 380,
            cuisine = Cuisine.KoreanBbq,
            dietaryTags = setOf(DietaryTag.ProteinForward),
            coolBackdrop = SmokedClay,
            warmBackdrop = BrushedGold,
        ))
        add(Dish(
            id = "sundubu-jjigae",
            name = "Sundubu Jjigae",
            description = "Silken tofu in a chili-rich stew, an egg cracked through. Stone pot still hissing.",
            portion = "One stone pot, ~450 g (rice held)",
            approxCalories = 320,
            cuisine = Cuisine.KoreanBbq,
            dietaryTags = setOf(DietaryTag.Vegetarian, DietaryTag.ProteinForward),
            coolBackdrop = Oxblood,
            warmBackdrop = Ember,
        ))
        add(Dish(
            id = "kimchi-jjigae",
            name = "Kimchi Jjigae",
            description = "Aged kimchi cooked down with pork belly until everything tastes of itself, only more.",
            portion = "One pot, ~450 g (rice held)",
            approxCalories = 480,
            cuisine = Cuisine.KoreanBbq,
            dietaryTags = setOf(DietaryTag.ProteinForward),
            coolBackdrop = Oxblood,
            warmBackdrop = Ember,
        ))
        add(Dish(
            id = "galbitang",
            name = "Galbitang",
            description = "Short-rib soup, clear broth, daikon, glass noodles. Beef that gives up easily.",
            portion = "One bowl, ~550 g (rice held)",
            approxCalories = 520,
            cuisine = Cuisine.KoreanBbq,
            dietaryTags = setOf(DietaryTag.ProteinForward),
            coolBackdrop = SmokedClay,
            warmBackdrop = Copper,
        ))
        add(Dish(
            id = "bibimbap",
            name = "Bibimbap",
            description = "Sizzling stone bowl: marinated ribeye, seasoned vegetables, a sunny egg. Stir thoroughly.",
            portion = "One stone bowl, ~500 g",
            approxCalories = 680,
            cuisine = Cuisine.KoreanBbq,
            dietaryTags = emptySet(),
            coolBackdrop = Oxblood,
            warmBackdrop = Ember,
        ))
        add(Dish(
            id = "yukhoe",
            name = "Yukhoe",
            description = "Beef tartare, julienned pear, sesame oil, a yolk. Eat it as soon as it lands.",
            portion = "150 g",
            approxCalories = 310,
            cuisine = Cuisine.KoreanBbq,
            dietaryTags = setOf(DietaryTag.ProteinForward),
            coolBackdrop = Oxblood,
            warmBackdrop = RestrainedRed,
        ))
        add(Dish(
            id = "galbi",
            name = "Galbi",
            description = "Marinated short rib over coals. Char on the edges, blush at the centre. Lettuce in your other hand.",
            portion = "200 g",
            approxCalories = 580,
            cuisine = Cuisine.KoreanBbq,
            dietaryTags = setOf(DietaryTag.ProteinForward),
            coolBackdrop = Oxblood,
            warmBackdrop = Ember,
        ))
        add(Dish(
            id = "samgyeopsal",
            name = "Samgyeopsal",
            description = "Pork belly over fire; ssamjang, garlic, perilla. The room smells like dinner is winning.",
            portion = "180 g",
            approxCalories = 720,
            cuisine = Cuisine.KoreanBbq,
            dietaryTags = setOf(DietaryTag.ProteinForward),
            coolBackdrop = Oxblood,
            warmBackdrop = Copper,
        ))
        add(Dish(
            id = "live-flame-wagyu-chadolbaegi",
            name = "Live-Flame Wagyu Chadolbaegi",
            description = "Brisket so thin it curls on contact. Three seconds a side; a single drop of sesame oil.",
            portion = "150 g",
            approxCalories = 540,
            cuisine = Cuisine.KoreanBbq,
            dietaryTags = setOf(DietaryTag.ProteinForward),
            coolBackdrop = SmokedClay,
            warmBackdrop = Ember,
        ))

        // === Lebanese ===
        add(Dish(
            id = "hummus-bil-tahini",
            name = "Hummus bil Tahini",
            description = "Chickpea, tahini, lemon, garlic. A pool of olive oil. Paprika, dust of cumin. Cucumber to lift.",
            portion = "200 g + cucumber",
            approxCalories = 320,
            cuisine = Cuisine.Lebanese,
            dietaryTags = setOf(DietaryTag.Vegetarian, DietaryTag.Mediterranean),
            coolBackdrop = SmokedClay,
            warmBackdrop = BrushedGold,
        ))
        add(Dish(
            id = "baba-ganoush",
            name = "Baba Ganoush",
            description = "Charred eggplant, smoked at the edges. Tahini, lemon, garlic. Pomegranate on top if the season allows.",
            portion = "180 g",
            approxCalories = 240,
            cuisine = Cuisine.Lebanese,
            dietaryTags = setOf(DietaryTag.Vegetarian, DietaryTag.Mediterranean),
            coolBackdrop = SmokedClay,
            warmBackdrop = Copper,
        ))
        add(Dish(
            id = "mujadara",
            name = "Mujadara",
            description = "Lentils, rice, onions caramelized for an hour until they are almost candy. Quiet, deep, satisfying.",
            portion = "320 g",
            approxCalories = 420,
            cuisine = Cuisine.Lebanese,
            dietaryTags = setOf(DietaryTag.Vegetarian, DietaryTag.Mediterranean),
            coolBackdrop = SmokedClay,
            warmBackdrop = Ember,
        ))
        add(Dish(
            id = "fattoush",
            name = "Fattoush",
            description = "Sumac-dressed summer vegetables, mint, parsley, crisp pita shards. Acidic enough to wake you.",
            portion = "280 g",
            approxCalories = 320,
            cuisine = Cuisine.Lebanese,
            dietaryTags = setOf(DietaryTag.Vegetarian, DietaryTag.Mediterranean),
            coolBackdrop = SmokedClay,
            warmBackdrop = BrushedGold,
        ))
        add(Dish(
            id = "warak-enab",
            name = "Warak Enab",
            description = "Grape leaves rolled tight around rice, herbs, lemon. Six pieces, served cool, eaten with the fingers.",
            portion = "Six pieces (200 g)",
            approxCalories = 280,
            cuisine = Cuisine.Lebanese,
            dietaryTags = setOf(DietaryTag.Vegetarian, DietaryTag.Mediterranean),
            coolBackdrop = SmokedClay,
            warmBackdrop = Ember,
        ))
        add(Dish(
            id = "shish-tawook",
            name = "Shish Tawook",
            description = "Yogurt-marinated chicken on a skewer, charcoal-grilled to a thin crust. Garlic toum on the side.",
            portion = "220 g",
            approxCalories = 420,
            cuisine = Cuisine.Lebanese,
            dietaryTags = setOf(DietaryTag.Mediterranean, DietaryTag.ProteinForward),
            coolBackdrop = SmokedClay,
            warmBackdrop = Copper,
        ))
        add(Dish(
            id = "lamb-kafta-skewers",
            name = "Lamb Kafta Skewers",
            description = "Hand-mixed lamb, onion, parsley, allspice. Pressed onto skewers, charred fast, eaten faster.",
            portion = "220 g",
            approxCalories = 520,
            cuisine = Cuisine.Lebanese,
            dietaryTags = setOf(DietaryTag.Mediterranean, DietaryTag.ProteinForward),
            coolBackdrop = Oxblood,
            warmBackdrop = Ember,
        ))
        add(Dish(
            id = "kibbeh-nayyeh",
            name = "Kibbeh Nayyeh",
            description = "Raw beef pounded with fine bulgur, mint, olive oil. A whisper of salt; the meat does the rest.",
            portion = "150 g",
            approxCalories = 280,
            cuisine = Cuisine.Lebanese,
            dietaryTags = setOf(DietaryTag.Mediterranean, DietaryTag.ProteinForward),
            coolBackdrop = Oxblood,
            warmBackdrop = RestrainedRed,
        ))
        add(Dish(
            id = "mashawi-mishakal",
            name = "Mashawi Mishakal",
            description = "Mixed grill: lamb, chicken, kafta, single portions. The whole grill on one plate.",
            portion = "280 g",
            approxCalories = 620,
            cuisine = Cuisine.Lebanese,
            dietaryTags = setOf(DietaryTag.Mediterranean, DietaryTag.ProteinForward),
            coolBackdrop = Oxblood,
            warmBackdrop = Ember,
        ))

        // === Indian ===
        add(Dish(
            id = "keema-pav",
            name = "Keema Pav",
            description = "Spiced lamb mince, soft butter-toasted roll. A street-cart morning made dignified.",
            portion = "350 g",
            approxCalories = 620,
            cuisine = Cuisine.Indian,
            dietaryTags = emptySet(),
            coolBackdrop = Oxblood,
            warmBackdrop = Ember,
        ))
        add(Dish(
            id = "masala-dosa",
            name = "Masala Dosa",
            description = "Fermented crepe, crackled at the edges, around a soft turmeric potato. Coconut chutney, sambar.",
            portion = "One dosa (280 g)",
            approxCalories = 480,
            cuisine = Cuisine.Indian,
            dietaryTags = setOf(DietaryTag.Vegetarian),
            coolBackdrop = SmokedClay,
            warmBackdrop = BrushedGold,
        ))
        add(Dish(
            id = "anda-bhurji",
            name = "Anda Bhurji",
            description = "Eggs scrambled with green chili, tomato, onion, cilantro. Spice you taste; spice you don't see.",
            portion = "Three eggs (180 g)",
            approxCalories = 320,
            cuisine = Cuisine.Indian,
            dietaryTags = setOf(DietaryTag.Vegetarian, DietaryTag.ProteinForward),
            coolBackdrop = SmokedClay,
            warmBackdrop = Ember,
        ))
        add(Dish(
            id = "butter-chicken",
            name = "Butter Chicken",
            description = "Tandoor-charred chicken in a tomato-cream gravy held just at simmer. Fenugreek to finish.",
            portion = "250 g + sauce",
            approxCalories = 540,
            cuisine = Cuisine.Indian,
            dietaryTags = setOf(DietaryTag.ProteinForward),
            coolBackdrop = Oxblood,
            warmBackdrop = Copper,
        ))
        add(Dish(
            id = "rogan-josh",
            name = "Rogan Josh",
            description = "Kashmiri lamb, slow-cooked with red chili and ginger until the sauce clings to the spoon.",
            portion = "250 g",
            approxCalories = 580,
            cuisine = Cuisine.Indian,
            dietaryTags = setOf(DietaryTag.ProteinForward),
            coolBackdrop = Oxblood,
            warmBackdrop = Ember,
        ))
        add(Dish(
            id = "chana-masala",
            name = "Chana Masala",
            description = "Chickpeas in a tomato-onion gravy, finished with amchur and chaat masala. Tart, deep, weeknight royalty.",
            portion = "300 g",
            approxCalories = 420,
            cuisine = Cuisine.Indian,
            dietaryTags = setOf(DietaryTag.Vegetarian),
            coolBackdrop = SmokedClay,
            warmBackdrop = Ember,
        ))
        add(Dish(
            id = "tandoori-lamb-chops",
            name = "Tandoori Lamb Chops",
            description = "Yogurt-marinated chops, ash-charred in a clay oven. Four bones, two minutes, no second chances.",
            portion = "220 g, four chops",
            approxCalories = 520,
            cuisine = Cuisine.Indian,
            dietaryTags = setOf(DietaryTag.ProteinForward),
            coolBackdrop = Oxblood,
            warmBackdrop = Ember,
        ))
        add(Dish(
            id = "seekh-kebab",
            name = "Seekh Kebab",
            description = "Minced lamb pressed onto a skewer, threaded with green chili and onion, fired hard and fast.",
            portion = "200 g",
            approxCalories = 460,
            cuisine = Cuisine.Indian,
            dietaryTags = setOf(DietaryTag.ProteinForward),
            coolBackdrop = Oxblood,
            warmBackdrop = Copper,
        ))
        add(Dish(
            id = "tandoori-prawns",
            name = "Tandoori Prawns",
            description = "Jumbo prawns, yogurt-and-spice marinade, ash-cooked until the tails curl tight.",
            portion = "180 g",
            approxCalories = 280,
            cuisine = Cuisine.Indian,
            dietaryTags = setOf(DietaryTag.Mediterranean, DietaryTag.ProteinForward),
            coolBackdrop = SmokedClay,
            warmBackdrop = RestrainedRed,
        ))

        // === Armenian ===
        add(Dish(
            id = "sujukh-and-egg",
            name = "Sujukh-and-Egg",
            description = "Cured beef sausage, spiced with cumin and garlic, sliced thick over a fried egg. The cure does the work.",
            portion = "150 g",
            approxCalories = 480,
            cuisine = Cuisine.Armenian,
            dietaryTags = setOf(DietaryTag.ProteinForward),
            coolBackdrop = Oxblood,
            warmBackdrop = Ember,
        ))
        add(Dish(
            id = "khorovats",
            name = "Khorovats",
            description = "Charcoal-grilled lamb or pork skewers, smoke from the wood as much as the meat. Lavash held.",
            portion = "220 g (no lavash)",
            approxCalories = 520,
            cuisine = Cuisine.Armenian,
            dietaryTags = setOf(DietaryTag.Mediterranean, DietaryTag.ProteinForward),
            coolBackdrop = Oxblood,
            warmBackdrop = Ember,
        ))
        add(Dish(
            id = "harissa",
            name = "Harissa",
            description = "Lamb and cracked wheat cooked all day until they forget themselves. Cinnamon. Butter to finish.",
            portion = "One bowl, ~400 g",
            approxCalories = 520,
            cuisine = Cuisine.Armenian,
            dietaryTags = emptySet(),
            coolBackdrop = SmokedClay,
            warmBackdrop = Copper,
        ))
        add(Dish(
            id = "ghapama",
            name = "Ghapama",
            description = "A whole pumpkin stuffed with rice, dried apricots, walnuts, baked until the rind softens. Cut tableside.",
            portion = "300 g wedge",
            approxCalories = 380,
            cuisine = Cuisine.Armenian,
            dietaryTags = setOf(DietaryTag.Vegetarian),
            coolBackdrop = SmokedClay,
            warmBackdrop = BrushedGold,
        ))
        add(Dish(
            id = "khorovats-lamb-rack",
            name = "Khorovats Lamb Rack",
            description = "A full rack over open coals, rotated by hand. Wild herbs in the smoke. Carve at the table.",
            portion = "250 g",
            approxCalories = 640,
            cuisine = Cuisine.Armenian,
            dietaryTags = setOf(DietaryTag.Mediterranean, DietaryTag.ProteinForward),
            coolBackdrop = Oxblood,
            warmBackdrop = Ember,
        ))
        add(Dish(
            id = "ishkhan-i-tapaka",
            name = "Ishkhan ի Tapaka",
            description = "Sevan trout pressed under a hot iron, skin crisp, flesh just set. Lemon, dill, nothing more.",
            portion = "220 g",
            approxCalories = 380,
            cuisine = Cuisine.Armenian,
            dietaryTags = setOf(DietaryTag.Mediterranean, DietaryTag.ProteinForward),
            coolBackdrop = SmokedClay,
            warmBackdrop = Copper,
        ))
        add(Dish(
            id = "dolma",
            name = "Dolma",
            description = "Grape leaves around lamb and rice, served small, dressed with yogurt and dill.",
            portion = "Six pieces (250 g)",
            approxCalories = 380,
            cuisine = Cuisine.Armenian,
            dietaryTags = setOf(DietaryTag.Mediterranean),
            coolBackdrop = SmokedClay,
            warmBackdrop = Ember,
        ))

        // === Greek ===
        add(Dish(
            id = "strapatsada",
            name = "Strapatsada",
            description = "Eggs scrambled into stewed summer tomato; feta crumbled in at the end. The pan is the recipe.",
            portion = "Three eggs + 80 g tomato + feta",
            approxCalories = 380,
            cuisine = Cuisine.Greek,
            dietaryTags = setOf(DietaryTag.Vegetarian, DietaryTag.Mediterranean, DietaryTag.ProteinForward),
            coolBackdrop = SmokedClay,
            warmBackdrop = RestrainedRed,
        ))
        add(Dish(
            id = "horiatiki",
            name = "Horiatiki",
            description = "Village salad: tomato, cucumber, red onion, olives, a slab of feta, generous olive oil. Bread held.",
            portion = "350 g",
            approxCalories = 380,
            cuisine = Cuisine.Greek,
            dietaryTags = setOf(DietaryTag.Vegetarian, DietaryTag.Mediterranean, DietaryTag.ProteinForward),
            coolBackdrop = SmokedClay,
            warmBackdrop = BrushedGold,
        ))
        add(Dish(
            id = "souvlaki-kalamaki",
            name = "Souvlaki Kalamaki",
            description = "Pork skewers, oregano, lemon, charred at the edges. Pita held; the meat is the meal.",
            portion = "220 g",
            approxCalories = 460,
            cuisine = Cuisine.Greek,
            dietaryTags = setOf(DietaryTag.Mediterranean, DietaryTag.ProteinForward),
            coolBackdrop = SmokedClay,
            warmBackdrop = Copper,
        ))
        add(Dish(
            id = "octopus-sti-schara",
            name = "Octopus Sti Schara",
            description = "Octopus, slow-braised then thrown onto coals. Olive oil and lemon, served warm.",
            portion = "180 g",
            approxCalories = 280,
            cuisine = Cuisine.Greek,
            dietaryTags = setOf(DietaryTag.Mediterranean, DietaryTag.ProteinForward),
            coolBackdrop = SmokedClay,
            warmBackdrop = RestrainedRed,
        ))
        add(Dish(
            id = "paidakia",
            name = "Paidakia",
            description = "Lamb chops, oregano, salt, lemon. Open fire. Carry them off the grill the moment they speak.",
            portion = "240 g, four chops",
            approxCalories = 580,
            cuisine = Cuisine.Greek,
            dietaryTags = setOf(DietaryTag.Mediterranean, DietaryTag.ProteinForward),
            coolBackdrop = Oxblood,
            warmBackdrop = Ember,
        ))
        add(Dish(
            id = "psari-plaki",
            name = "Psari Plaki",
            description = "Whole sea bream baked over tomato, olive oil, parsley, lemon. Pulled apart at the table.",
            portion = "300 g fish",
            approxCalories = 420,
            cuisine = Cuisine.Greek,
            dietaryTags = setOf(DietaryTag.Mediterranean, DietaryTag.ProteinForward),
            coolBackdrop = SmokedClay,
            warmBackdrop = BrushedGold,
        ))
        add(Dish(
            id = "moussaka",
            name = "Moussaka",
            description = "Layered eggplant, spiced lamb, béchamel set golden under a final blast of heat. Cut clean to the plate.",
            portion = "One portion, ~380 g",
            approxCalories = 620,
            cuisine = Cuisine.Greek,
            dietaryTags = emptySet(),
            coolBackdrop = Oxblood,
            warmBackdrop = Ember,
        ))
    }

    /** Default dish used as a placeholder before the user has chosen one. */
    val default: Dish get() = all.first()

    fun byId(id: String): Dish? = all.firstOrNull { it.id == id }

    fun byCuisine(cuisine: Cuisine): List<Dish> = all.filter { it.cuisine == cuisine }

    fun byTag(tag: DietaryTag): List<Dish> = all.filter { tag in it.dietaryTags }

    /**
     * Apply the picker's filter chip selection. Empty filter sets mean
     * "no restriction" on that axis.
     *
     * Semantics:
     * - Multiple cuisines are OR'd ("Japanese OR Korean BBQ" → dishes from
     *   either).
     * - Multiple dietary tags are AND'd ("Vegetarian AND Protein-forward"
     *   → dishes that are both, e.g. tamagoyaki, anda bhurji).
     */
    fun filter(
        cuisines: Set<Cuisine>,
        tags: Set<DietaryTag>,
    ): List<Dish> = all.filter { dish ->
        (cuisines.isEmpty() || dish.cuisine in cuisines) &&
            (tags.isEmpty() || tags.all { it in dish.dietaryTags })
    }
}
