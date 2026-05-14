# CadentFast

A premium fasting-rhythm app for people who love food.

Not a willpower app. A **delayed-gratification** app. The user runs a continuous fast-and-feast rhythm, picks the meal they're craving for each break-fast moment, locks it in as the reward, and the timer becomes the runway to that meal. The fast is the wait list at the best table in town — and the table is theirs.

## The insight

Every other fasting app treats the craving as the enemy. CadentFast treats the craving as the **goal**. The dish on the screen is not a temptation to resist — it's the reservation they're keeping with themselves. This single idea governs every screen, every word, every animation.

## Audience

Foodies. People who eat out often, eat out well, and look forward to the next meal more than most people look forward to the weekend. They are at home at:

- High-end Japanese (wagyu AYCE, premium yakiniku, omakase counters, neighborhood sushi bars, specialty ramen)
- Premium Korean BBQ
- Vietnamese — pho counters, bún and grilled-protein houses, clay-pot specialists
- Indian — tandoor-forward, kebab houses, regional biryani specialists
- Armenian — khorovats, kebab and lavash houses, ceremonial breakfast traditions
- Greek — souvlaki and grilled-fish spots, the village-salad register
- Mediterranean diet generally — olive-oil-forward, fish-and-vegetable-heavy
- Vegetarian where it's the cuisine's natural register (Indian, Greek, Armenian)
- Protein-forward / low-grain dining for users practicing low-carb + intermittent fasting

They are not here to suppress cravings. They are here to **earn** them.

The app is not for people who struggle with delayed gratification. It is for people who choose it because they know the reward will be sharper for the wait.

## Product principle

The chosen dish is the protagonist of every fast period. It is pinned, large, and beautiful throughout the timer. The countdown, the ring, the rhythm itself — all supporting characters.

The voice of the app is the **maître d'**, not the trainer. Anticipatory, indulgent, confident, a little knowing. Never shaming. Never clinical. Never "wellness."

## How the rhythm works

CadentFast is a continuous fast/feast rhythm, not a one-shot timer. Once the user begins the rhythm, it cycles indefinitely:

- **Fast period** — the user is fasting, a dish is locked as the upcoming table, the hero screen is the dish with the ring counting down to the break-fast moment.
- **Break-fast moment** — when the fast period ends, the cinematic reveal fires (slow dissolve, signature chime, deep haptic). The user eats. The timer keeps running.
- **Feast period** — same hero screen but quieter: the locked dish dims, the sub-line shifts to feast-mode language (*"Linger." / "The kitchen is yours." / "The next table opens in 2h 41m."*). Late in the feast window, a quiet *"Choose your next table"* affordance appears so the user can pick what they're fasting toward next.
- **Next fast period** — the rhythm rolls over automatically. If the user has already locked a table, the timer reflects it; if not, the hero screen prompts them to pick.

The rhythm **never auto-ends**. Only the user explicitly ends it — a deliberate, low-emphasis affordance, not a button on the main screen. Default phrasing: *"The kitchen will be here when you're ready."*

## The rhythms

Cadence is presented to users with maître-d' names rather than intermittent-fasting ratios. The ratio sits as a quiet subtitle for users who recognize it; the name is what the user lives with day to day:

| Name | Cadence | Subtitle |
|---|---|---|
| The Long Lunch | 12:12 | Twelve and twelve |
| The Apéritif | 14:10 | Fourteen and ten |
| **The Daily** *(default)* | 16:8 | Sixteen and eight |
| The Tasting | 18:6 | Eighteen and six |
| The Chef's Counter | 20:4 | Twenty and four |

The names grade naturally from generous to disciplined — a long lunch is a forgiving day; the chef's counter is the most committed seat in the house. First-time users get **The Daily** as the default and can switch to any other rhythm later. The 4:2 dev cadence (four minutes fasting, two minutes feasting) is a debug-only `BuildConfig.DEBUG` rhythm; release builds never expose it.

## Core flow

1. **Begin the rhythm.** Welcome screen with a quiet *"A table for one is waiting."* and a single CTA: *"Show me to my table."*
2. **When did you last sit down?** A backdate-the-start step. Default option (pre-selected, copper-lit): *"Just now."* Secondary options: *"Earlier today."* / *"Yesterday evening."* (each opens a time picker). The math handles "already in feast window" naturally — the user lands on the feast screen if their backdated time puts them past the first fast.
3. **Lock the first table.** Picking the break-fast dish is part of starting the rhythm — the rhythm cannot begin without a locked table. The menu opens with smart-default filter chips pre-activated based on when the upcoming break-fast moment will fall (light-leaning filters for morning break-fasts; full catalog for afternoon and evening).
4. **Fast period — hero timer screen.** The dish, the ring, the textual time-to-table, the maître-d' sub-line that softens with proximity. The user may swap the locked table at any time during the fast (tap the dish name → *"Reconsider →"* re-opens the menu → returns with the new dish, timer state unchanged). Per *Personalization* below: appetites change.
5. **Break-fast moment.** Slow 2–3s reveal: timer block dissolves, dish takes the full screen at full grade, signature chime, deep haptic. A single *"Your table."* button hands off — in v1, into the feast window directly; in Phase 2+, into the "find this near me" path (the partner hand-off is a *separate, subsequent* tap, never co-located with the cinematic reveal moment).
6. **Feast period.** Same hero composition; dish dims by ~30%; feast-mode sub-line. The ring continues — now showing time-til-next-fast. The user eats. The app doesn't track what's eaten; the locked dish was the protagonist, not a checklist.
7. **Late in the feast window**, the quiet *"Choose your next table"* affordance appears so the user can plan ahead.
8. **Next fast period begins** automatically when the feast window closes. Loops to (4).

## Design language

The aesthetic target is the feeling of being seated at the best table in a high-end Japanese or Korean BBQ — dark, warm, intimate, lit from below by the grill. Cinematic food photography. Generous negative space. Restrained typography. Premium and grown-up.

- **Palette** — near-black charcoal base, warm neutrals (oxblood, smoked clay, ember), a single metallic accent (brushed gold or copper), and a restrained red used sparingly as a craving cue. No "wellness green."
- **Type** — one editorial serif for dish names and headlines, one clean humanist sans for UI. Tight tracking on display, generous leading on body.
- **Photography** — the product. Image quality, crop, and color grading are first-class concerns. Consistent grading across the catalog. No generic stock.
- **Iconography** — minimal, line-based, custom. No emoji in UI. No cartoon glyphs.
- **Motion** — slow, weighty, confident. Things settle; nothing bounces. The hero dish breathes. The ring's marker moves like a sweep, not a tick.
- **Sound** — restrained and signature. One bell-like chime at each break-fast moment. Optional ambient room tone during a fast (off by default). No alert tones that feel like a phone.
- **Haptics** — used like punctuation, not exclamation marks. A soft confirmation when the table is locked (or swapped); a deeper resonance at each break-fast.

This is the *feeling* of the best room you've ever eaten in. No logos, marks, palettes, illustrations, photography, menu copy, fonts, or other proprietary identity from any real restaurant brand. All assets must be original, properly licensed, or supplied by a paid partner under a partnership agreement (see *Business model* below).

## The hero timer screen

The most-looked-at screen in the app. The dish is the protagonist; the rhythm ring is the supporting character; they share one frame without competing.

**Layout.** A circular **rhythm ring** sits center-screen, sized to fill most of the viewport. The dish hero — the radial gradient placeholder in v1, real photography later — fills the inside of the ring. Below the ring sits a quiet typographic block: the textual time-to-next-transition, a one-line maître-d' sub-line. Nothing else above the fold. No tabs, no badges, no toolbars.

**The ring itself.** A single circle divided into two arcs proportional to the rhythm:

- **Fast arc** — Copper. 16/24 of the circle for a 16:8 cycle. Two-thirds of the ring.
- **Feast arc** — Brushed Gold. 8/24 of the circle. One-third.

A small marker — a brushed-gold tick or filled dot — sits on the ring at the user's current position in the cycle, and travels around the ring once per cycle. The arcs are static; only the marker moves. At a glance the user sees both *where in the cycle they are right now* and *the rhythm itself* — the same shape every day, the user's position varying.

**Two registers.** The ring is the same composition during the fast and feast periods, but the textual block below shifts:

- **Fast register** — *"3h 12m until your table"*, sub-line softens through the maître-d' stages.
- **Feast register** — *"2h 41m of feast remaining"*, sub-line is one of *"Linger." / "The kitchen is yours." / "The next table opens in 2h 41m." / "Eat well."* The dish hero dims by ~30%.

**Type.** Time is set in the editorial serif, large enough to read at arm's length but quiet in weight — confident, not shouty. The sub-line is in the humanist sans, small, restrained.

**Time formatting** keeps the number visible at all times. The countdown is textual, not stopwatch — *"3h 12m" / "47 minutes" / "1 minute" / "45 seconds" / "1 second"* — so the editorial voice holds, but a concrete number always tells the user how long is left:

- More than an hour out: *"3h 12m until your table"* / *"3h 12m of feast remaining"*
- Within the hour: *"47 minutes" … "5 minutes" … "1 minute"*
- Within the last minute: *"59 seconds" … "1 second"*
- At zero: *"Now."*

Earlier drafts replaced the number with the phrase *"Any moment now"* in the final stretch. On hardware the substitution made the timer feel ambiguous — users couldn't tell whether the meal was thirty seconds out or three minutes out, and the phrase lost its meaning sustained across multiple minutes. The number stays. The maître-d' voice still lives in the sub-line below the timer and in the break-fast notification copy.

**Breathing.** The dish hero oscillates in scale by 1–2% over a 6–8 second cycle — slow enough to feel alive, never enough to feel animated. Reduced-motion users see a still hero with no breathing.

**Swap the locked table.** The dish name is itself tappable, with a small copper-tinted affordance below it (*"Reconsider →"*). It re-opens the menu; choosing a new dish returns to the hero screen with the new table locked. The ring and the timer continue uninterrupted — only the dish (and its associated colors) swap. The ripening picks up wherever the progress was; no reset. Swaps in the last 60 seconds of a fast are accepted but the new dish ripens at the same progress percentage on its own color curve — there is no fresh-cold restart for late swaps.

## The feast window

The post-break-fast period until the next fast begins.

- **Same hero screen, different register.** The dish stays on screen but dims by roughly 30%. The textual block reads time-til-next-fast: *"2h 41m of feast remaining."*
- **Sub-line copy** is one of *"Linger." / "The kitchen is yours." / "The next table opens in 2h 41m." / "Eat well."*
- **No meal tracking.** What the user eats during the feast is the user's business. The locked dish was the protagonist; the rest of the meal is private. No logging, no totals, no nudges.
- **Choose your next table.** A quiet affordance appears in the final ~30% of the feast window — copper-tinted, low-emphasis — re-opening the menu. The user may also pre-lock earlier in the feast if they want.
- **If no table is locked when the feast ends**, the next fast begins anyway — the screen prompts *"Choose your next table when you're ready"* over a quiet still hero (the dimmed previous dish). The rhythm continues; the user simply hasn't picked their next target yet.

## The menu

There is **one menu**. Food is food. No breakfast / lunch / dinner segmentation — that structure pressures the catalog to invent dishes for slots a cuisine doesn't naturally serve. Instead, the menu is a single vertical-scrolling list of dish cards in the curator's editorial sequence (light to rich, the chef's intended pacing).

**Filter chips** do all navigation:

- **Cuisine chips:** Japanese · Korean BBQ · Vietnamese · Indian · Armenian · Greek
- **Dietary register chips:** Mediterranean · Vegetarian · Protein-forward

Filter chips are toggleable; multiple may be active at once (e.g., *Greek* + *Mediterranean* + *Protein-forward* narrows to grilled-fish-with-veg territory). The chip row is horizontally scrollable above the dish list.

**Smart-default filter activation.** When the menu opens, the app checks when the upcoming break-fast moment will fall and pre-activates dietary register chips appropriately:

- Morning break-fast (≤ 11 AM local) → *Vegetarian* / *Mediterranean* pre-activated; lighter dishes surface first.
- Afternoon break-fast (11 AM – 5 PM local) → no pre-activation; full catalog visible.
- Evening break-fast (≥ 5 PM local) → no pre-activation; full catalog visible.

The user can clear any pre-activation with one tap. The behavior is invisible to the user; they just see the right dishes for their context. This replaces the meal-of-day tab structure entirely — the same intelligence lives in the filter pre-activation, not in a labeled tab.

**"Protein-forward" as a chip.** The dietary register the keto-IF crowd recognizes by carb count is labeled **"Protein-forward"** in the chip row, not "Keto-friendly." Same filter rule (`<15g net carbs per serving`), maître-d' voice. Describes what the dish *is*, not what diet it serves.

## Catalog data model

Every catalog entry is one of two shapes:

- **Generic entry** *(default)* — AI-generated imagery, in-house copy, no restaurant attribution. Example: *"Tonkotsu Ramen"*. Every dish in v1 is a generic entry.
- **Branded override** *(Phase 3+)* — a partner restaurant has paid to *own* this catalog entry. Carries the restaurant's name, branded copy (passed through our editorial voice review), branded imagery (passed through the food art director's reject gate), and a list of locations. Example: *"Ippudo's Tonkotsu Ramen"*. Replaces the generic entry for users in that restaurant's catchment area.

**Geographic exclusivity, with dual-feature on overlap.** Each branded entry has a defined catchment radius around the restaurant's locations. Within the catchment, the branded entry replaces the generic. Outside the catchment, the entry reverts to generic (or to another partner's branded variant if one exists in that region). When **two or more partners overlap in the same user's catchment** for the same dish — e.g., Ippudo *and* Hide-Chan both serve Tonkotsu Ramen in the user's neighborhood — **both branded variants are featured** in the menu list, ordered alphabetically by brand name. No exclusivity-upgrade tier; no single-winner auction. (See *Business model — Contract exhibit F* below.)

**Card visual treatment for branded entries.** A thin 0.5sp copper hairline divider sits below the dish name; below it, a humanist-sans line at ~11sp, 70% copper opacity, reads *"—— Ippudo, NYC"*. No logo on the card. The dish name remains the protagonist in editorial serif at 22sp. Partner photography (which may exceed the AI baseline) does the actual selling. No badge, no ribbon, no "Partner" tag. Branded and generic cards have identical dimensions, identical typography, identical hero treatment; the only delta is the attribution line.

**Menu ordering invariance.** Branded and generic entries intermix freely. Order is by the curator's editorial sequence per cuisine — the same shape the list has in Phase 1 (all generic) holds in late Phase 3 (mostly branded). No "featured" rail, no separate branded tab, no partner-first sorting.

## Anticipation, by design

The hero screen must make the user *feel* the meal approaching, not just read a countdown. Numbers ticking down do not produce that feeling. Stacked quiet design moves do. None of these alone would work; together they create the "if I just hold on a little longer, the wagyu is going to be even better" effect.

- **The dish ripens.** A single hero image per dish is *dynamically graded* by the app as the fast period progresses. Early in the fast, the grade is low-key — slightly cool, slightly desaturated, lighting waiting. As time passes, the grade walks toward the fully realized hero version: warmth comes up, saturation deepens, highlights bloom on the sear, the oil glistens. The user is literally watching the meal become ready. Each glance shows a more vivid dish than the glance before. Achievable with one master image per dish and a parameterized grading layer — no need for multiple commissioned shots.
- **The room warms.** A warm radial gradient behind the dish grows in intensity as the fast nears its end. Faint early, glowing late. Sells "the grill is being fired up" without ever showing flame.
- **Steam intensifies.** For hot dishes, a subtle steam animation overlays the image. Early: barely there. Late: dense, curling, drifting across the frame.
- **Language softens with proximity.** Numbers persist for clarity, but the sub-line shifts from clinical to anticipatory as milestones pass: *"Your table is being prepared." → "The wine has been opened." → "Halfway to the table." → "The kitchen is plating." → "Almost ready."* Pacing language, not motivation language.
- **The marker glows.** Late in the fast, the rhythm ring's marker emits a faint copper bloom — a few hundred milliseconds long, every few seconds, like a candle catching air. Reduced-motion: marker stays static.
- **Sound (opt-in).** Ambient room tone evolves from "early, kitchen quiet" toward "kitchen mid-service" as the fast nears completion — distant cutlery, faint sizzle of a grill, a plate being set down somewhere. At zero, the single signature chime.
- **Haptics as anticipation.** A whisper at midway and pre-arrival, more felt than noticed. A deeper, fuller resonance at break-fast — like a bell rung at the kitchen pass.
- **The break-fast reveal.** When the fast period hits zero, a slow 2–3 second hand-off: the timer block dissolves, the dish takes the full screen at full grade, the chime sounds, a deep haptic confirms. Then a single button — *"Your table."* — that opens the feast window (in v1) or the "find this near me" hand-off (Phase 2+). **The reveal is a closed loop unto itself; the partner hand-off is always a second, separate tap, never co-located with the cinematic moment.**

The trick is that none of these is loud. Each is a small, slow, premium move. Stacked, they pull the user forward without ever shaming them for being there.

## Voice & copy

Maître-d', not trainer. Restaurant world, not fitness world. Some load-bearing copy:

- Welcome CTA: *"Show me to my table."*
- Backdate question: *"When did you last sit down?"*
- Backdate default option: *"Just now."* with secondary *"Earlier today." / "Yesterday evening."*
- Backdate-to-table CTA: *"Show me to my table."*
- Already-in-feast landing copy: *"You're mid-meal. Linger — the next table opens in 2h 41m."*
- Foreground notification (fast register): *"Your reservation is set."*
- Break-fast notification: *"The kitchen is ready. Your A5 Ribeye, Tableside-Seared is plated."*
- Break-fast button: *"Your table."*
- Swap affordance: *"Reconsider →"*
- Choose-next affordance: *"Choose your next table"*
- Map toggle (template): *"{Brand}'s tables"* / *"{Dish} nearby"* — *"Ippudo's tables" / "Tonkotsu nearby."*
- Branded-to-generic fallback: *"Ippudo isn't on this block tonight. Here's where the tonkotsu is."*
- End-fast (no shame): *"The table will be here tomorrow."*
- End-rhythm: *"The kitchen will be here when you're ready."*

Calorie counts are always labeled *approximate* and **always displayed per dish only** — never summed into daily totals, never set against a goal, never used to nudge or warn. Information, not management. Numbers never carry moral weight.

## Notifications

Premium means restraint. Default to a handful of well-timed moments per cycle, each one earning its place:

- A single midway whisper during the fast (*"Halfway to the table."*).
- A pre-arrival nudge (*"The kitchen is plating."*).
- Break-fast ready — on a HIGH-importance channel with sound and vibration so it reaches the user with the phone locked. This is the only sustained-attention notification in the app.
- A quiet feast-mode reminder when the choose-next-table affordance appears, only if the user hasn't already pre-locked.

Never punitive. Never streak-anxiety. Notifications are opt-out granular.

## Personalization

The app should feel like being recognized by name at a restaurant the user has been to many times before.

- Remember the dishes they have locked in before, surfacing them subtly inside the menu (no separate history view, no list of past locks).
- Surface "your usuals" subtly, never as a leaderboard.
- **Allow swapping the locked table mid-fast without penalty — appetites change.** The swap is consequence-free: timer continues, ripening picks up where it was, no shame language.
- No social features. No leaderboards, no friend feeds, no shareable streaks. This is a private indulgence.

## Trust & care

- Health disclaimer surfaced calmly at first-run: fasting isn't for everyone (pregnancy, eating disorder history, certain medications, etc.). A short, dignified tap-through to a "talk to a clinician" note. Never alarmist. Never repeated.
- Local-first data **for the core ritual.** The rhythm, the locked dish, the timer state — all live on device, work offline, require no account.
- **More-than-minimal data is collected once partner features are live.** "Find this near me" requires geo. Restaurant attribution requires logging which dishes converted into directions / reservations. We will not pretend this is "minimal data collection" once the partner flow exists. It is opt-in, opt-out granular, surfaced honestly in first-run, and the app remains fully functional with geo and partner-attribution disabled.
- The catalog should be transparent about where photography and nutrition estimates come from. Partner-supplied content carries a small *"—— [Restaurant]"* attribution.

## Technical posture

- **Offline-first for the core ritual.** The timer must be accurate and reliable with no network. The catalog must be cacheable and browsable offline once seen. Partner features (geo, reservations) gracefully degrade when offline — the rhythm and break-fast still work.
- **Timer reliability is sacred.** Survives backgrounding, app kill, device sleep, reboot, time-zone change, and DST. No drift. No reset on relaunch.
- **The rhythm is derived state.** Computed from a single persisted `rhythmStartEpochMs` plus the cadence — every clock and every restart computes the same answer. Wall-clock representation is a render concern, not a state concern. Boundary alarms use `System.currentTimeMillis()` (RTC); animation smoothing uses `SystemClock.elapsedRealtime`.
- **Cadence-change rule.** Changing cadence mid-rhythm re-anchors `rhythmStartEpochMs` to the start of the *current* phase under the new cadence. The user does not get yanked across phase boundaries by a cadence change.
- **AlarmManager strategy: single alarm per phase, re-armed each fire.** When the break-fast alarm fires, the receiver (a) posts the notification, (b) immediately schedules the next phase-end alarm. Never schedule multiple alarms ahead.
- **Missed-alarm recovery.** Inevitable on some OEMs. On every app launch, every FGS tick, and every `BOOT_COMPLETED` / `TIME_SET` / `TIMEZONE_CHANGED` / `LOCKED_BOOT_COMPLETED` broadcast, recompute the current phase from `now` and `rhythmStartEpochMs` + cadence. If a boundary was crossed without firing, fire late with honest copy (*"The kitchen was ready 12 minutes ago"*) and re-arm forward.
- **Exact-alarm denial path.** Android 14+ can refuse `SCHEDULE_EXACT_ALARM`. The app gracefully degrades to inexact alarms + a self-check loop, with a documented honest user-facing note about timing precision.
- **Foreground-service posture.** Windowed FGS: active in the final hour of each phase plus on-demand when the app is foregrounded; AlarmManager is the source of truth between. The persistent notification carries two registers (fast: *"3h 12m until your table"*, feast: *"2h 41m of feast remaining"*). LOW-importance ongoing channel; the HIGH-importance break-fast notification is a separate one-shot on its own channel.
- **Swap/alarm race rule.** The break-fast notification receiver reads the latest locked dish from DataStore *at fire time*, never captures the dish at schedule time. Multiple rapid swaps collapse harmlessly to "last write wins."
- **Image performance is a feature.** Progressive loading, careful prefetch on the menu, smart compression. The hero dish never appears at low fidelity.
- **Accessibility is not optional.** Strong contrast within the dark palette, dynamic type, TalkBack labels on every image (dish name + description spoken), reduced-motion variants for all hero motion, full keyboard/switch control support on platforms that need it.
- **Privacy by default.** Honest about what is local and what crosses the wire. Partner features are opt-in and surfaced as such.

## State-gap rules

UX edge cases the implementation must honor:

- **First fast begins with no table locked.** The rhythm cannot start without one — the welcome flow always lands on the menu picker before the timer begins.
- **Subsequent fast begins with no table locked** (user did not choose during the feast window). The hero screen shows a quiet still hero (dimmed previous dish) and the *"Choose your next table when you're ready"* prompt. The ring runs; the dish-area waits.
- **Empty-feast hero (first ~70% of feast window).** The hero shows the dimmed previous dish until the choose-next affordance activates late in the feast. No ghost-state, no neutral placeholder.
- **Break-fast fires while user is in the menu picker.** The picker dismisses; the reveal plays. The user returns to the new locked dish if they had been swapping mid-fast.
- **Return-after-absence (user opens the app mid-rhythm after hours or days).** The rhythm meets the user wherever they are. No "you missed three tables" surfacing. The maître-d' doesn't mention skipped meals.
- **System clock skew / NTP correction.** Boundaries are computed from epoch ms; alarms re-fire correctly at the new wall time. UI animation uses elapsedRealtime to remain smooth.
- **Time-zone change / DST.** The math is invariant — the user's break-fast still falls at the same epoch moment. The local-time display shifts. No re-anchoring needed.
- **Permission denials.** Notifications denied: the app warns once at first-run, then degrades gracefully (alarm still fires; user must open the app to see break-fast). Exact-alarm denied: degrades as documented above.

## Out of scope (do not drift here)

- Calorie totals, daily goals, macro tracking, weight-loss framing
- Meal logging beyond the single locked break-fast dish (no "what did you eat today" diary)
- Streaks, badges, leaderboards, social competition as primary loops
- **The hero timer screen never gains a fourth element.** Ring, dish, text block. That is the composition forever.
- **One cinematic break-fast reveal per cycle.** Never escalated, never gamified, never multiplied. The 2–3s dissolve + chime + deep haptic is a sacred moment and stays singular.
- Generic stock food photography
- Light, minimalist "health app" aesthetic; clinical "wellness" tone
- Emoji-laden UI, cartoon mascots, gamified celebration
- Any copyrighted or trademarked restaurant brand, menu, mark, photography, or identity used as more than private aesthetic reference (partner content excepted, under explicit agreement)
- Promoted dishes that outrank organic ones without clear *"—— [Restaurant]"* attribution
- Push notifications selling deals, promotions, or restaurant ads
- Social features (no leaderboards, no friend feeds, no shareable streaks — full stop)
- Fake or invented dishes in the catalog — every entry must be authentic to its cuisine, with traceable culinary provenance

## Business model

CadentFast is **free to install and free to use.** Users never pay for the core ritual.

The customer is the restaurant. The product they buy is **a uniquely high-intent foodie funnel**: users who have spent hours anticipating a specific dish, with their geographic location known, on a predictable daily cycle. No existing restaurant marketing channel (Yelp impressions, Resy reservations, OpenTable bookings, Instagram ads) offers that combination of intent + ritual + cadence.

**Marketing positioning: niche down without narrowing the product.** Landing-page copy and App Store positioning lean keto-IF and premium-foodie ("*If you're doing low-carb intermittent fasting and you actually love eating out, this is for you*") without the product itself being keto-only. The broader audience — vegetarian, Mediterranean diet, generally protein-forward foodies — uses the same product without seeing a niche brand identity.

### Phased rollout

- **Phase 1** — User-facing product only. Generic catalog. No restaurant features in-app beyond the curated catalog. Goal: the rhythm earns its right to exist as a thing foodies want to use daily.
- **Phase 2** — *Generic discovery.* "Find this near me" via places API. Every nearby restaurant serving an approximation of the user's locked dish surfaces — no partnerships required, no attribution measurement yet. Every metro has coverage from day one. Userbase grows.
- **Phase 3** — *Branded overrides for sale.* Partner restaurants pay to **own a catalog entry** — their branded version replaces the generic for users in their catchment. Branded entries surface that restaurant's nearest locations preferentially in the map UI. Geographic exclusivity; dual-feature on overlap. (See pricing below.)
- **Phase 4** — Reservations via **OpenTable / Resy / Tock deep links** for branded entries. We eat the third-party cover charges; do not pass them to partners. Hard **18-month sunset** on third-party deep links per metro from Phase 4 launch — if direct POS integration hasn't landed by then, Phase 4 pulls from that metro rather than extends.
- **Phase 5** — Direct POS integration (Toast, Square, Resy POS) for own-rails reservations. Real cover-level attribution becomes possible.

### Phase 3 pricing — "own this dish entry"

Flat monthly subscription per location, tiered by dish AOV:

| Tier | Example dishes | Price per location per month |
|---|---|---|
| Premium | A5 Ribeye, Ishiyaki Wagyu, Paidakia, Tandoori Lamb Chops | **$449** |
| Standard | Bibimbap, Butter Chicken, Bún Chả, Souvlaki, Galbi | **$249** |
| Entry | Tamago Kake Gohan, Masala Dosa, Anda Bhurji, Greek Yogurt | **$149** |

**Multi-dish bundle:** second dish at the same location **−40%**, third+ at **−55%**, cap at 4 dishes per location. **90-day repricing notice** in every contract (these numbers are first-instinct guesses; reprice after the first six months of real data). No annual prepay discount in v1 — we haven't earned retention yet.

### Phase 3 attribution — "Locked-Intent Funnel"

Four events tracked, reported weekly to partners:

1. **Locked impression** — user locked the dish during a fast where this partner's entry was eligible in catchment.
2. **Branded-card view** — partner's branded entry rendered on the break-fast hand-off screen.
3. **Directions tap** — user tapped "directions to this location."
4. **Self-attested visit** — optional in-app *"I went"* tap during the feast window, prompted once, never nagged.

Geo dwell-time at venue is **rejected** — battery cost, privacy debt, and gameable. We don't have it and we don't pretend to.

**Explicit limit, on every dashboard and in every contract, in plain language:** *"CadentFast measures intent and outbound action. We cannot prove an incremental cover until direct POS integration (Phase 5). These numbers are a directional funnel, not attributable revenue."* Dispute window: 30 days from invoice, scoped to event-count disputes only — causation is governed by the limit above.

### Multi-partner overlap pricing

Both branded variants pay full price. No exclusivity-upgrade tier. Stable, non-purchasable ordering rule: nearest location to user first, ties broken alphabetically by brand name. Documented, public, immutable.

Selling exclusivity would corrode the maître-d' frame and turn the catalog into a placement auction. Leaves money on the table in dense markets (Manhattan ramen, LA Korean BBQ) — accepted cost of holding the line.

### Contract exhibits (non-negotiable structural pieces)

Every partnership agreement carries these exhibits. Codified as contract terms, not aesthetic principles.

| | What it covers |
|---|---|
| **A** | Content quality SLA — image spec, copy length and tone bounds, 5-business-day review SLA, rejection categories enumerated, two-resubmission ceiling before slot pause. |
| **B** | Take-down rights — mutual; partner can pull content in 5 business days for any reason; we can pull in 24 hours for quality / legal / brand-safety with written cause and re-listing path. |
| **C** | Editorial control of copy — partner supplies factual content (ingredients, provenance); CadentFast retains sole right to render in maître-d' voice. Partner reviews final copy; cannot dictate it. Disputes resolved by creative director, final. |
| **D** | Logo & attribution placement — monogram only, max 24pt height, bottom-right of card, max 60% opacity, no animation, no color override of app palette. Single normative diagram is the contract. |
| **E** | No promoted slot — express covenant: no payment, gift, or consideration causes partner ranking to exceed the nearest-location-first rule. Material breach triggers refund-and-terminate. |
| **F** | Exclusivity policy — default none. No-exclusivity language is contractual, not just stated. Closes the door on later sales pressure. |
| **G** | Multi-location handling — per-location subscription; corporate parent may consolidate billing but pricing remains per-location. Defines "location" by physical address, not brand. |
| **H** | Churn handoff — on termination, partner content is removed within 7 days; the dish entry reverts to the generic Phase 2 places-API version. User-facing transition language is ours alone; no *"[Partner] has left CadentFast"* surfacing. |

### Defending the user side

The user is the *audience*, not the *product*. The funnel collapses the moment users feel like inventory. Yelp's reputation eroded once it was accused of manipulating reviews of advertisers; we will not repeat that. Concrete rules:

- Partner dishes are never surfaced above organic ones without clear attribution.
- The maître-d' voice and dark premium aesthetic are non-negotiable; restaurant branding lives quietly within the app's design language, never co-equal with it.
- No promoted dishes, no ad slots, no push notifications selling deals. Partner restaurants benefit by being the natural fulfillment point for an already-locked craving, not by buying user attention.
- **The first enforcement moment will cost a partner.** Budget for losing one large partner in year one to an Exhibit A or Exhibit E violation. The exhibits have teeth only if we exercise them.

### Optional later: premium subscription

Not a hedge against the restaurant model. A real product for users who want history, advanced personalization, multi-cycle planning, or other power features. The core ritual is never paywalled.

## Decisions for v1

1. **Platform — Android-first, native (Kotlin + Jetpack Compose).** The owner is on Android and must be able to test on-device. Native gives the best image performance, smoothest premium motion, the strongest background-timer story (foreground service + AlarmManager + WorkManager), and the cleanest haptics/audio. iOS port is out of scope for v1; revisit after the loop is validated.
2. **Catalog source — curated in-house.** Every dish entered by hand. The catalog is treated as an edited menu, not a database. Schema partitions entries by `cuisine` and tags entries with optional dietary registers (`mediterranean`, `vegetarian`, `protein_forward`).
3. **Food photography — AI-generated as MVP placeholder, replaced over time.** AI imagery is the v1 MVP source — fast, cheap, controllable. The food art director runs a strict reject gate. The roadmap explicitly plans to replace AI imagery with higher-quality alternatives — licensed stock, commissioned shoots, or restaurant-partner photography — cuisine-by-cuisine and partner-by-partner as the catalog matures. Use an AI service with unambiguous commercial-use rights. Design the catalog schema so a dish can swap its imagery without losing identity, lock history, or personalization data. **Commission-from-day-one shortlist:** dishes the Curator identified as unreliable for AI rendering (white-on-white compositions, pale broths, multi-component noodle dishes with chopsticks) — see `CATALOG.md` for the specific seven.
4. **Default cadence — The Daily (16:8).** First-run uses the maître-d'-named rhythm. The user can switch to *The Long Lunch / The Apéritif / The Tasting / The Chef's Counter* later. Debug builds run a 4:2 dev rhythm for fast device iteration; release builds never expose it.
5. **"Find this near me" — Phase 2, not v1.** Implies a places provider (default: Google Places, since Android-native). The v1 slice ships without it; the architectural seams are in place from day one so Phase 2 only adds the provider, not the plumbing.
6. **Reservations — Phase 4, via OpenTable / Resy / Tock deep links to start.** Direct POS integration in Phase 5. v1 ships with no reservation surface at all.
7. **Monetization — restaurant partnerships, not user payments.** Free install. No paywall on any user-facing feature. A premium user subscription may land later; v1 has none.
8. **v1 cuisines — six.** Japanese · Korean BBQ · Vietnamese · Indian · Armenian · Greek. ~40–48 authentic dishes across the six cuisines. Each dish is real and traceable to its cuisine — no inventions, no constructed registers. See `CATALOG.md`.
9. **v1 dietary registers — three.** Mediterranean · Vegetarian · Protein-forward. Applied as cross-cutting tags, surfaced as filter chips, with smart-default pre-activation based on upcoming break-fast time-of-day.

## Open considerations

- **AI imagery is the biggest brand risk.** A bad image undermines the entire premium feel more than any other defect. The food art director's reject gate is the hill to die on. Commission-from-day-one for the seven unreliable dishes; iterate the rest.
- **Vietnamese broth-noodle photography.** Pho, bún bò huế, bún chả are canonical AI failure modes (fused noodles, warped chopsticks). Expect to commission these earlier than the catalog-wide roadmap suggests.
- **Geographic density before partner rollout.** Phase 3 lives or dies on having enough partners per metro that branded-entry coverage feels non-sparse. Phase 2's generic discovery decouples the user launch from this — every metro is covered via places API regardless.
- **Restaurant branding tension.** Partner content (photography, menu copy) must be ingested without diluting the app's premium minimalism. Rigorous partner content guidelines (Exhibit A above). Toast and Yelp visibly struggle with this; our defense is contract terms, not aesthetic preferences.
- **No-show flexibility (Phase 4+).** Fasts are voluntary. If a user breaks fast early, any held reservation needs to gracefully cancel or shift without burning the restaurant. The maître-d' voice still applies — *"The table will be here tomorrow."* The booking system has to be operationally forgiving.
- **Places provider lock-in.** Google Places is the path of least resistance on Android but carries per-call cost; if the app grows, evaluate Foursquare / Mapbox+OSM. Wrap the provider behind an interface from day one.
- **Resy/OpenTable cannibalization (Phase 4 → 5 window).** Resy keeps the reservation data — a real strategic cost, not just a financial one. The 18-month sunset is enforcement against extending the dependency.
- **Privacy revisited at Phase 2.** Once geo + visit attribution + (Phase 4) reservation data exist, the first-run experience needs a careful, honest pass on what's collected and why. Foodies will tolerate it for the convenience; deception would not survive contact with the press.

## Working with this repo

- GitHub MCP scope is limited to `sharkmelf/cadentfast`.
- **Stack:** Android, Kotlin, Jetpack Compose, Material 3 with heavy custom theming. Coil for images. Foreground service + AlarmManager + WorkManager for the rhythm. DataStore for local persistence (Room only if/when relational queries are actually needed). Google Places (wrapped behind an interface) for Phase 2 discovery. OpenTable / Resy deep links for Phase 4 reservations.
- Keep the dependency footprint tight. Premium feel is hand-built, not glued together from a dozen libraries.
- Minimum SDK: pick a recent floor (target Android 14/15, min 26 or higher). Premium audience tolerates a tighter device matrix in exchange for a better experience.
- **CI:** `.github/workflows/android-build.yml` runs `assembleDebug` + `lint` on every PR. Pin AGP version changes to their own dedicated PRs — never folded into feature work.

## The first slice — vertical, end-to-end

Before scaffolding any other code, build the **thinnest end-to-end rhythm** that proves the product on the owner's device. Everything else waits.

**In scope:**

- **Begin the rhythm.** Welcome screen with *"A table for one is waiting."* and *"Show me to my table."* CTA.
- **Backdate the start.** *"When did you last sit down?"* with *"Just now."* default and time-picker secondaries. The math handles "already in feast" landings.
- **The menu (one menu).** Vertical scrolling list of dish cards. Cuisine and dietary register filter chips with smart-default pre-activation based on upcoming break-fast time-of-day. Tap a dish → soft lock-confirm haptic → returns to hero.
- **Hero timer screen with the rhythm ring.** Two-arc ring (copper fast, brushed gold feast) with a moving marker, dish hero inside the ring, textual time below, maître-d' sub-line, two registers (fast / feast).
- **Swap-mid-fast affordance.** Tap the dish name → *"Reconsider →"* re-opens the menu → returns with new dish locked. Timer state preserved, ripening continues from current progress.
- **Break-fast moment.** Cinematic 2–3s reveal at the end of each fast: timer block dissolves, dish at full grade, signature chime, deep haptic, single *"Your table."* button.
- **Feast window screen behavior.** Same composition, dish dims by 30%, feast-mode sub-line, time-til-next-fast displayed, *"Choose your next table"* affordance appears in the final ~30% of the feast window.
- **Auto-cycling.** When the feast period ends, the next fast period begins automatically. If a table is locked, the hero shows it; if not, prompts the user.
- **End the rhythm.** Deliberate, low-emphasis affordance (small gear or text link, not a button on the main hero) with a confirmation step. Default copy: *"End the rhythm — the kitchen will be here when you're ready."*
- **Foreground-service-backed rhythm** that survives backgrounding, screen lock, app kill, reboot. DataStore-backed rhythm state — `rhythmStartEpochMs` + cadence + locked dish ID.
- **Audible break-fast notification** on a HIGH-importance channel with default sound + vibration. System defaults for now; signature chime asset lands in a later slice.
- **`BOOT_COMPLETED` receiver** to re-arm the next break-fast alarm after device reboot.

**Explicitly cut from the slice** (these come in later slices):

- Cuisines beyond the v1 six (the broader catalog rollout)
- Search
- Custom cadences beyond the five named rhythms (the user picks from the named set in v1)
- "Find this near me" / Google Places integration → Phase 2
- Branded overrides → Phase 3
- Reservations / OpenTable deep links → Phase 4
- Restaurant partner attribution / partner content → Phase 3+
- Sustained ambient room tone (chime + haptic only at zero)
- Personalization beyond persistence (usuals, recents)
- Settings screen beyond "End the rhythm" and cadence selection
- Account / sync
- First-run health disclaimer copy (drafted, but the screen lands in a slice after the core loop)
- Dedicated accessibility audit (basic contrast and dynamic type are still respected; the audit comes when the screens settle)
- Premium subscription / Play Billing

**Slice cadence for dev work:** 4:2 (four minutes fasting, two minutes feasting). Each device-iteration round takes six minutes per full cycle, not 24 hours. Release builds use the named rhythms.

**Minimum agent set for this slice:** brand & identity designer (palette/type/metallic accent), product designer (welcome / backdate / menu / hero / break-fast / feast screens), motion designer (breathing, ripening, ring marker, break-fast reveal), food art director (color-gradient placeholder hero per dish for now, AI-imagery + commission-shortlist work for catalog), sound designer (chime + haptic vocabulary), copywriter (maître-d' phrasing for every surface — see *Voice & copy*), culinary curator (~40–48 dishes across six cuisines, see `CATALOG.md`), Android engineer (Compose + navigation + Coil grading), timer & state engineer (foreground service + AlarmManager + DataStore + rhythm math + state-gap rules), QA / device tester (rhythm survival across kill / reboot / DST).

Skipped for the slice: search engineer, personalization engineer, discovery / geo engineer, reservation integration engineer, restaurant partnership manager, commerce engineer, accessibility specialist, legal / IP reviewer.

**Scaffolding posture:** single-module Android app, Kotlin DSL Gradle, ViewModel + StateFlow per screen, no DI framework, no Room. Premium feel is hand-built; the slice doesn't need a library cabinet.

## The agent team

When the user asks for "an optimal team of agents," propose roles that map to the work this product actually needs — not generic SWE archetypes. A good full ensemble:

- **Creative director** — owns the AYCE-premium feeling end to end; the single voice that vetoes anything drifting toward wellness-app generic. Final say on aesthetic.
- **Brand & identity designer** — original wordmark, palette, type system, metallic accent treatment, iconography. Guarantees no real-restaurant IP leaks in.
- **Food art director** — sourcing AI imagery and grading it to spec for v1; running the reject gate; owning the dynamic-grading curve that ripens the dish during a fast; designing the eventual hand-off to stock / commissioned / partner photography. Owns the consistency of the catalog as a visual whole.
- **Product designer (UX)** — the welcome → backdate → menu → hero → break-fast → feast → swap loop, the rhythm ring, the empty / error / loading states.
- **Motion designer** — slow, weighty transitions; the hero dish's breathing motion; the rhythm ring marker; the break-fast reveal.
- **Sound designer** — the signature break-fast chime, the optional ambient room tone, the haptic vocabulary. One signature sonic moment, not a sound library.
- **Copywriter** — dish descriptions and UI voice in the maître-d' register: anticipatory, indulgent, confident, forgiving. Owns the language of milestones, of swapping, of ending a fast early, of ending the rhythm, of fallback states.
- **Culinary curator** — builds the v1 menu of ~40–48 dishes across six cuisines with accurate portions and approximate calorie counts; owns shot lists for the AI imagery pipeline and, later, partner shoots.
- **Android engineer** — Kotlin / Jetpack Compose / Material 3 custom theme / Coil. Owns the welcome → backdate → menu → hero → break-fast → feast loop and image performance.
- **Timer & state engineer** — foreground service + AlarmManager + WorkManager; background-safe, drift-free rhythm that survives kill, doze, sleep, reboot, time-zone change, and DST. Owns the rhythm math (cycle from `rhythmStartEpochMs` + cadence), the missed-alarm recovery, the cadence-change rule, and the swap/alarm race rule.
- **Search engineer** — fast, forgiving near-keyword search over the catalog (typo-tolerant, synonym-aware). Phase-aligned with the broader catalog rollout.
- **Personalization engineer** — the "regular at the restaurant" memory: usuals, recents, gentle surfacing inside the menu without nagging.
- **Discovery / geo engineer (Phase 2)** — "find this near me" at break-fast. Owns the Google Places integration behind a provider interface; designs the offline / rate-limit graceful-degrade path.
- **Reservation integration engineer (Phase 4+)** — OpenTable / Resy / Tock deep links to start; direct POS integration (Toast, Square, Resy POS) in Phase 5. Owns the no-show flexibility logic and the cancellation grace path.
- **Restaurant partnership manager (Phase 3+)** — signs partners in target metros; sets per-partner content guidelines; owns the partner-attribution rules; runs the Phase 3 pricing-and-tiers function; codifies the contract exhibits.
- **QA & device tester** — rhythm accuracy across backgrounding scenarios; image fidelity across devices; no jank on the hero screen; reservation booking accuracy in Phase 4+.
- **Accessibility & inclusion specialist** — contrast in the dark palette, dynamic type, TalkBack for image-heavy screens, reduced motion, dignity of copy in sensitive moments.
- **Legal & IP reviewer** — confirms zero infringement on restaurant brands, photography licenses, font licensing, trademarks; vets AI-imagery service commercial-use terms; drafts and reviews partnership contracts and Exhibit A–H content.

**Tailor the team to the slice of work the user actually wants built next.** Do not spawn all of them by default. Ask which slice to build first, then delegate only the roles that slice needs.
