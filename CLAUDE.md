# CadentFast

A premium fasting timer for people who love food.

Not a willpower app. A **delayed-gratification** app. The user picks the meal they're craving, locks it in as the reward, and the timer becomes the runway to that meal. The fast is the wait list at the best table in town — and the table is theirs.

## The insight

Every other fasting app treats the craving as the enemy. CadentFast treats the craving as the **goal**. The dish on the screen is not a temptation to resist — it's the reservation they're keeping with themselves. This single idea governs every screen, every word, every animation.

## Audience

Foodies. People who eat out often, eat out well, and look forward to the next meal more than most people look forward to the weekend. They are at home at:

- High-end Japanese wagyu AYCE and premium yakiniku
- Premium Korean BBQ
- Brazilian churrascaria; Mongolian-style grill
- Conveyor-belt sushi, neighborhood sushi bars, and omakase counters
- Elevated dumpling houses (soup dumplings, hand-pulled, dim sum)
- Specialty ramen shops
- Thai, Indian, Vietnamese, and broader Southeast Asian cuisine
- Protein-forward by default

They are not here to suppress cravings. They are here to **earn** them.

The app is not for people who struggle with delayed gratification. It is for people who choose it because they know the reward will be sharper for the wait.

## Product principle

The chosen dish is the protagonist of the entire fasting session. It is pinned, large, and beautiful throughout the timer. The countdown is the supporting character — present, but never the headline.

The voice of the app is the **maître d'**, not the trainer. Anticipatory, indulgent, confident, a little knowing. Never shaming. Never clinical. Never "wellness."

## Core flow

1. **Cuisine select** — a short, edited grid of category tiles. The catalog reads like a curated tasting menu, not a buffet.
2. **Menu browse** — within a category, a rich gallery of dishes. Each card carries:
   - Hero photograph, full-bleed where it serves the dish
   - Name (editorial serif) and a short evocative description
   - Single-serving portion
   - Approximate calorie count (always labeled *approximate* — never falsely precise)
3. **Search** — typo-tolerant, synonym-aware keyword filter across name + description + cuisine tags. "Soup dumpling" finds xiao long bao. "Spicy tuna" finds the roll and the hand roll. "Bun" finds vermicelli bowls and steamed buns both, ranked by intent.
4. **Lock the reward** — user confirms the dish. From this moment forward, it is the meal they are fasting toward, present on every screen.
5. **Fast setup** — pick a duration. A small, restrained set of presets (e.g. 12, 14, 16, 18, 20, 24h) plus a custom option. First-time users default to a gentle window; don't push hardcore fasts on day one.
6. **Timer** — the locked dish is the hero of the screen the entire time. Elapsed time and time-to-reward are present but quiet. The progress indicator is restrained and beautiful. No badges. No streak guilt-trips.
7. **The wait** — milestone moments along the way are framed as **anticipation**, not achievement. ("Halfway to the table." "The kitchen is plating.") One nudge per milestone. Never more.
8. **Break fast** — a celebratory hand-off into the meal: a slow reveal of the dish, a single signature chime, and an optional "find this near me" handoff that surfaces places likely to serve it (or close kin).
9. **After** — a quiet log entry. No score. No congratulations. The app remembers what they ordered, the way a good restaurant remembers a regular.

## Design language

The aesthetic target is the feeling of being seated at the best table in a high-end Japanese or Korean BBQ — dark, warm, intimate, lit from below by the grill. Cinematic food photography. Generous negative space. Restrained typography. Premium and grown-up.

- **Palette** — near-black charcoal base, warm neutrals (oxblood, smoked clay, ember), a single metallic accent (brushed gold or copper), and a restrained red used sparingly as a craving cue. No "wellness green."
- **Type** — one editorial serif for dish names and headlines, one clean humanist sans for UI. Tight tracking on display, generous leading on body.
- **Photography** — the product. Image quality, crop, and color grading are first-class concerns. Consistent grading across the catalog. No generic stock.
- **Iconography** — minimal, line-based, custom. No emoji in UI. No cartoon glyphs.
- **Motion** — slow, weighty, confident. Things settle; nothing bounces. The hero dish breathes. The countdown moves like a sweep, not a tick.
- **Sound** — restrained and signature. One bell-like chime at break-fast. Optional ambient room tone during a fast (off by default). No alert tones that feel like a phone.
- **Haptics** — used like punctuation, not exclamation marks. A soft confirmation when the reward is locked; a deeper resonance at break-fast.

This is the *feeling* of the best room you've ever eaten in. No logos, marks, palettes, illustrations, photography, menu copy, fonts, or other proprietary identity from any real restaurant brand. All assets must be original or properly licensed.

## The hero timer screen

The most-looked-at screen in the app. The dish is the protagonist, the timer is the supporting character, and they share one frame without competing.

**Layout.** The dish photograph fills the top of the screen — full-bleed, the most cinematic crop, no letterboxing, no app chrome above it. Below the image sits a quiet typographic block: time remaining, a one-line maître-d' sub-line, and a thin metallic progress arc. Nothing else. No tabs, no badges, no toolbars. The image gets the room; the timer gets the bottom of a magazine page.

**Type.** Time is set in the editorial serif, large enough to read at arm's length but quiet in weight — confident, not shouty. The sub-line is in the humanist sans, small, restrained.

**Time formatting** follows restaurant cadence, not stopwatch. The minute count ticks down concretely all the way to one, so the phrase *"Any moment now"* still means **imminent** when it fires — it does not appear earlier in the fast:

- More than an hour out: "13h 47m"
- Within the hour: "47 minutes" … "5 minutes" … "1 minute" (the minute count ticks down concretely)
- Within thirty seconds: "Any moment now"
- At zero: "Now."

An earlier draft used a five-minute *"Any moment now"* window. On hardware, that window was too wide — the phrase lost its meaning when it sustained across several minutes. Narrowing it to the final thirty seconds restores its weight, and the textual minute countdown stays maître-d', not stopwatch.

Offer an alternate clock-time display as a setting ("Your table at 7:12 PM"). Some users anchor better to a time of arrival than a duration.

**Breathing.** The dish photograph oscillates in scale by 1–2% over a 6–8 second cycle — slow enough to feel alive, never enough to feel animated. Reduced-motion users see a still image with no breathing.

## Anticipation, by design

The hero screen must make the user *feel* the meal approaching, not just read a countdown. Numbers ticking down do not produce that feeling. Stacked quiet design moves do. None of these alone would work; together they create the "if I just hold on a little longer, the wagyu is going to be even better" effect.

- **The dish ripens.** A single hero photograph per dish is *dynamically graded* by the app as the fast progresses. Early in the fast, the grade is low-key — slightly cool, slightly desaturated, lighting waiting. As time passes, the grade walks toward the fully realized hero version: warmth comes up, saturation deepens, highlights bloom on the sear, the oil glistens. The user is literally watching the meal become ready. Each glance shows a more vivid dish than the glance before. Achievable with one master image per dish and a parameterized grading layer — no need for multiple commissioned shots.
- **The room warms.** A warm radial gradient — copper, ember — sits behind the dish photo and grows in intensity as the fast nears its end. Faint early, glowing late. Sells "the grill is being fired up" without ever showing flame.
- **Steam intensifies.** For hot dishes, a subtle steam animation overlays the image. Early: barely there. Late: dense, curling, drifting across the frame.
- **Language softens with proximity.** Numbers persist for clarity, but the sub-line shifts from clinical to anticipatory as milestones pass: *"Your table is being prepared." → "Halfway to the table." → "The kitchen is plating." → "Almost ready." → "Any moment now."* Pacing language, not motivation language.
- **Sound (opt-in).** Ambient room tone evolves from "early, kitchen quiet" toward "kitchen mid-service" as the fast nears completion — distant cutlery, faint sizzle of a grill, a plate being set down somewhere. At zero, the single signature chime.
- **Haptics as anticipation.** A whisper at midway and pre-arrival, more felt than noticed. A deeper, fuller resonance at break-fast — like a bell rung at the kitchen pass.
- **Progress as a metallic sweep.** Not a percentage bar. A thin gold or copper arc tracing the bottom of the dish, filling slowly. No labels, no numbers on it.
- **The break-fast reveal.** When the timer hits zero, a slow 2–3 second hand-off: the timer block dissolves, the dish takes the full screen at full grade, the chime sounds, a deep haptic confirms. Then a single button — *"Begin."* — that opens the "find this near me" hand-off or quietly logs the meal and closes.

The trick is that none of these is loud. Each is a small, slow, premium move. Stacked, they pull the user forward without ever shaming them for being there.

## Voice & copy

- Anticipatory: *"Your table is being set."*
- Indulgent: *"Earned, not endured."*
- Confident: *"The kitchen knows."*
- Knowing: *"You'll know when you're ready."*
- Forgiving when a fast is ended early: *"The table will be here tomorrow."* Never *"You failed."* Never *"You broke your streak."*

Calorie counts are always labeled approximate. Numbers never carry moral weight.

## Notifications

Premium means restraint. Default to a handful of well-timed moments per fast, each one earning its place:

- Reward locked, fast begun ("Your reservation is set.")
- A single midway nudge ("Halfway to the table.")
- A pre-arrival ("The kitchen is plating.")
- Break-fast ready

Never punitive. Never streak-anxiety. Notifications are opt-out granular.

## Personalization

The app should feel like being recognized by name at a restaurant the user has been to many times before.

- Remember the dishes they have locked in before.
- Surface "your usuals" subtly, never as a leaderboard.
- Allow swapping the locked reward mid-fast without penalty — appetites change.
- No social features by default. No leaderboards, no friend feeds, no shareable streaks. This is a private indulgence.

## Trust & care

- Health disclaimer surfaced calmly at first-run: fasting isn't for everyone (pregnancy, eating disorder history, certain medications, etc.). A short, dignified tap-through to a "talk to a clinician" note. Never alarmist. Never repeated.
- Local-first data by default. No account required to start. If accounts are ever introduced, they unlock sync, not the core experience.
- The catalog should be transparent about where photography and nutrition estimates come from.

## Technical posture

- **Offline-first.** The timer must be accurate and reliable with no network. The catalog must be cacheable and browsable offline once seen.
- **Timer reliability is sacred.** Survives backgrounding, app kill, device sleep, reboot, time-zone change, and DST. No drift. No reset on relaunch.
- **Image performance is a feature.** Progressive loading, careful prefetch on the gallery, smart compression. The hero dish never appears at low fidelity.
- **Accessibility is not optional.** Strong contrast within the dark palette, dynamic type, VoiceOver labels on every image (dish name + description spoken), reduced-motion variants for all hero motion, full keyboard/switch control support on platforms that need it.
- **Privacy by default.** Minimal data collection. Clear about what is local and what is not.

## Out of scope (do not drift here)

- Calorie shaming, macro nagging, weight-loss framing
- Streaks, badges, leaderboards, social competition as primary loops
- Generic stock food photography
- Light, minimalist "health app" aesthetic; clinical "wellness" tone
- Emoji-laden UI, cartoon mascots, gamified celebration
- Any copyrighted or trademarked restaurant brand, menu, mark, photography, or identity used as more than private aesthetic reference

## Decisions for v1

1. **Platform — Android-first, native (Kotlin + Jetpack Compose).** The owner is on Android and must be able to test on-device. Native gives the best image performance, smoothest premium motion, the strongest background-timer story (foreground service + AlarmManager + WorkManager), and the cleanest haptics/audio. iOS port is out of scope for v1; revisit after the loop is validated.
2. **Catalog source — curated in-house.** Every dish entered by hand. The catalog is treated as an edited menu, not a database.
3. **Food photography — AI-generated as MVP placeholder, replaced over time.** AI imagery is the v1 MVP source — fast, cheap, controllable. The food art director runs a strict reject gate (warped hands, fused chopsticks, plastic noodles, off-grain rice all get killed). The roadmap explicitly plans to replace AI imagery with higher-quality alternatives — licensed stock, commissioned shoots, or restaurant-partner photography — cuisine-by-cuisine as the catalog matures. Use an AI service with unambiguous commercial-use rights. Design the catalog schema so a dish can swap its imagery without losing identity, lock history, or personalization data.
4. **"Find this near me" at break-fast — in v1.** Implies a places provider (default: Google Places, since Android-native). Discovery is a graceful add-on, not a blocker — if offline or rate-limited, break-fast still works without it.
5. **Monetization — one-time purchase.** Browse and lock a reward without paying; the paywall sits at "begin fast," where craving-driven conversion is highest. The core lock-and-fast loop is never paywalled awkwardly once unlocked.
6. **v1 cuisine — high-end Japanese wagyu only (AYCE / yakiniku / premium small-format).** One cuisine, art-directed to perfection. Target catalog size: ~25–50 dishes. The expansion strategy is **one cuisine at a time** — each new cuisine added only when it can match the v1 quality bar. Korean BBQ wagyu, sushi, dumplings, ramen, etc. follow in their own releases.

## Open considerations

- **AI imagery is the biggest brand risk.** A bad image undermines the entire premium feel more than any other defect. The food art director's reject gate is the hill to die on.
- **Restaurant partnerships are the long-term photography source.** Design the catalog schema so a dish can swap its imagery (AI → commissioned) without losing its identity, lock history, or personalization data.
- **Places provider lock-in.** Google Places is the path of least resistance on Android but carries per-call cost; if the app grows, evaluate Foursquare/Mapbox+OSM. Wrap the provider behind an interface from day one.
- **Paywall placement** is a real design decision, not an afterthought — it deserves the same care as the timer screen.
- **Restaurant partnership terms.** When deals are made, lock down: photography license, exclusivity, take-down rights, and how a partner dish is marked (subtly — never as an ad).

## Working with this repo

- Default working branch for this CLAUDE.md task: `claude/create-claude-md-fasting-F9UUq`.
- GitHub MCP scope is limited to `sharkmelf/cadentfast`.
- **Stack:** Android, Kotlin, Jetpack Compose, Material 3 with heavy custom theming. Coil for images. Foreground service + AlarmManager + WorkManager for the timer. DataStore for local persistence (Room only if/when relational queries are actually needed). Google Places (wrapped behind an interface) for discovery. Google Play Billing for one-time purchase.
- Keep the dependency footprint tight. Premium feel is hand-built, not glued together from a dozen libraries.
- Minimum SDK: pick a recent floor (target Android 14/15, min 26 or higher). Premium audience tolerates a tighter device matrix in exchange for a better experience.

## The first slice — vertical, end-to-end

Before scaffolding any other code, build the **thinnest end-to-end loop** that proves the product on the owner's device. Everything else waits.

**In scope:**

- **Lock screen** — a hardcoded list of 3–5 Japanese wagyu dishes. Tap one to lock it as the reward.
- **Duration screen** — exactly three presets: **15m / 1h / 4h**. The short windows are for iteration; the 4h option is the only "real" fast in the slice.
- **Hero timer screen** — full-bleed dish on top, quiet typographic timer block below, the dynamic-grading progression ("the dish ripens"), the breathing motion, the metallic progress arc, the maître-d' sub-line softening with proximity.
- **Break-fast screen** — slow 2–3s reveal at zero, single signature chime, deep haptic, one *"Begin."* button that closes back to home.
- **Foreground-service-backed timer** that survives backgrounding, screen lock, app kill, reboot. DataStore-backed lock state — the locked dish and remaining time resume on relaunch.
- **Audio + haptics** in the slice: the break-fast chime, lock-confirm haptic, milestone whisper haptics, deep break-fast haptic.

**Explicitly cut from the slice** (these come in later slices):

- Search
- The full ~25–50 dish catalog
- Multiple cuisines and the category-select screen
- Full preset set (12/14/16/18/20/24h) and custom duration
- "Find this near me" / Google Places
- Google Play Billing / paywall
- System notifications (chime + haptic only at zero)
- Ambient room tone
- Personalization (usuals, recents, history)
- Settings screen
- Account / sync
- First-run health disclaimer
- Dedicated accessibility audit (basic contrast and dynamic type are still respected; the audit comes when the screens settle)

**Minimum agent set for this slice:** brand & identity designer (palette/type/metallic accent only), product designer (four screens), motion designer (breathing, grade progression, progress arc, break-fast reveal), food art director (one master image per dish + the grading curve), sound designer (chime + haptic vocabulary), copywriter (maître-d' phrasing for lock/milestones/break-fast), culinary curator (3–5 dishes), Android engineer (Compose + navigation + Coil grading transform), timer & state engineer (foreground service + AlarmManager + DataStore), QA/device tester (timer survival on the owner's device).

Skipped for the slice: search engineer, personalization engineer, discovery/geo engineer, commerce engineer, accessibility specialist, legal/IP reviewer.

**Scaffolding posture:** single-module Android app, Kotlin DSL Gradle, ViewModel + StateFlow per screen, no DI framework, no Room. Premium feel is hand-built; the slice doesn't need a library cabinet.

## The agent team

When the user asks for "an optimal team of agents," propose roles that map to the work this product actually needs — not generic SWE archetypes. A good full ensemble:

- **Creative director** — owns the AYCE-premium feeling end to end; the single voice that vetoes anything drifting toward wellness-app generic. Final say on aesthetic.
- **Brand & identity designer** — original wordmark, palette, type system, metallic accent treatment, iconography. Guarantees no real-restaurant IP leaks in.
- **Food art director** — sourcing AI imagery and grading it to spec for v1; running the reject gate; owning the dynamic-grading curve that ripens the dish on the timer screen; designing the eventual hand-off to stock/commissioned/partner photography. Owns the consistency of the catalog as a visual whole.
- **Product designer (UX)** — the cuisine → dish → lock → timer flow, the search interaction, the reward-pinned timer screen, the break-fast moment, the empty/error/loading states.
- **Motion designer** — slow, weighty transitions; the hero dish's breathing motion; the countdown sweep; the break-fast reveal.
- **Sound designer** — the single break-fast chime, the optional ambient room tone, the haptic vocabulary. One signature sonic moment, not a sound library.
- **Copywriter** — dish descriptions and UI voice in the maître-d' register: anticipatory, indulgent, confident, forgiving. Owns the language of milestones and the language of giving up.
- **Culinary curator** — builds the v1 wagyu catalog with accurate portions and approximate calorie counts; owns shot lists for the AI imagery pipeline and, later, partner shoots.
- **Android engineer** — Kotlin / Jetpack Compose / Material 3 custom theme / Coil. Owns the cuisine → dish → lock → timer flow and image performance.
- **Timer & state engineer** — foreground service + AlarmManager + WorkManager; background-safe, drift-free fasting timer that survives kill, doze, sleep, reboot, time-zone change, and DST.
- **Search engineer** — fast, forgiving near-keyword search over the catalog (typo-tolerant, synonym-aware across cuisines and dish aliases).
- **Personalization engineer** — the "regular at the restaurant" memory: usuals, recents, gentle surfacing without nagging.
- **Discovery / geo engineer** — "find this near me" at break-fast. Owns the Google Places integration behind a provider interface; designs the offline/rate-limit graceful-degrade path.
- **Commerce engineer** — Google Play Billing for one-time purchase. Owns paywall placement (at "begin fast"), purchase restoration, and refund-safe state.
- **QA & device tester** — timer accuracy across backgrounding scenarios; image fidelity across devices; no jank on the hero screen; accessibility verification.
- **Accessibility & inclusion specialist** — contrast in the dark palette, dynamic type, TalkBack for image-heavy screens, reduced motion, dignity of copy in sensitive moments.
- **Legal & IP reviewer** — confirms zero infringement on restaurant brands, photography licenses, font licensing, trademarks; vets AI-imagery service commercial-use terms and future restaurant-partnership contracts.

**Tailor the team to the slice of work the user actually wants built next.** Do not spawn all of them by default. Ask which slice to build first, then delegate only the roles that slice needs.
