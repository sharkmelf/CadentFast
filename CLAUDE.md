# CadentFast

A premium fasting-rhythm app for people who love food.

Not a willpower app. A **delayed-gratification** app. The user runs a continuous fast-and-feast rhythm, picks the meal they're craving for each break-fast moment, locks it in as the reward, and the timer becomes the runway to that meal. The fast is the wait list at the best table in town — and the table is theirs.

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

The chosen dish is the protagonist of every fast period. It is pinned, large, and beautiful throughout the timer. The countdown, the ring, the rhythm itself — all supporting characters.

The voice of the app is the **maître d'**, not the trainer. Anticipatory, indulgent, confident, a little knowing. Never shaming. Never clinical. Never "wellness."

## How the rhythm works

CadentFast is a continuous fast/feast rhythm, not a one-shot timer. Once the user begins the rhythm, it cycles indefinitely:

- **Fast period** — the user is fasting, a dish is locked as the upcoming reward, the hero screen is the dish with the ring counting down to the break-fast moment.
- **Break-fast moment** — when the fast period ends, the cinematic reveal fires (slow dissolve, signature chime, deep haptic). The user eats. The timer keeps running.
- **Feast period** — same hero screen but quieter: the locked dish dims, the sub-line shifts to feast-mode language ("Linger.", "The kitchen is yours.", "The next table opens in 2h 41m."). Late in the feast window, a quiet "Lock the next reward" affordance appears so the user can pick what they're fasting toward next.
- **Next fast period** — the rhythm rolls over automatically. If the user has already locked a reward, the timer reflects it; if not, the hero screen prompts them to pick.

The rhythm **never auto-ends**. Only the user explicitly ends it — a deliberate, low-emphasis affordance, not a button on the main screen. Default phrasing: *"The kitchen will be here when you're ready."*

**Cadence.** Default is 16:8 — sixteen hours fasting, eight hours feasting, on a daily cycle. The user can change the cadence later; the default exists so first-run is friction-free.

**Dev cadence.** Debug builds run a 4:2 rhythm (four minutes fasting, two minutes feasting) so device iteration cycles run in minutes, not hours. Release builds run real cadences. The toggle is a `BuildConfig.DEBUG` check at compile time — no in-app UI for it in v1.

## Core flow

1. **Begin the rhythm.** First-run shows the welcome screen with a quiet *"A table for one is waiting."* and a single CTA: *"Begin the rhythm."*
2. **Lock the first reward.** Picking the break-fast dish is part of starting the rhythm — the rhythm cannot begin without a locked reward. The dish picker opens to the menu (breakfast / lunch / dinner) appropriate to the time the upcoming break-fast moment will fall.
3. **Fast period — hero timer screen.** The dish, the ring, the textual time-to-table, the maître-d' sub-line that softens with proximity. The user may swap the locked reward at any time during the fast (tap the dish name → re-opens the picker → returns with the new dish, timer state unchanged). Per *Personalization* below: appetites change.
4. **Break-fast moment.** Slow 2–3s reveal: timer block dissolves, dish takes the full screen at full grade, signature chime, deep haptic. A single *"Begin."* button hands off to the "find this near me" path or quietly opens the feast window.
5. **Feast period.** Same hero composition; dish dims by ~30%; feast-mode sub-line. The ring continues — now showing time-til-next-fast. The user eats. The app doesn't track what's eaten; the locked dish was the protagonist, not a checklist.
6. **Late in the feast window**, the quiet "Lock the next reward" affordance appears so the user can plan ahead.
7. **Next fast period begins** automatically when the feast window closes. Loops to (3).

## Design language

The aesthetic target is the feeling of being seated at the best table in a high-end Japanese or Korean BBQ — dark, warm, intimate, lit from below by the grill. Cinematic food photography. Generous negative space. Restrained typography. Premium and grown-up.

- **Palette** — near-black charcoal base, warm neutrals (oxblood, smoked clay, ember), a single metallic accent (brushed gold or copper), and a restrained red used sparingly as a craving cue. No "wellness green."
- **Type** — one editorial serif for dish names and headlines, one clean humanist sans for UI. Tight tracking on display, generous leading on body.
- **Photography** — the product. Image quality, crop, and color grading are first-class concerns. Consistent grading across the catalog. No generic stock.
- **Iconography** — minimal, line-based, custom. No emoji in UI. No cartoon glyphs.
- **Motion** — slow, weighty, confident. Things settle; nothing bounces. The hero dish breathes. The ring's marker moves like a sweep, not a tick.
- **Sound** — restrained and signature. One bell-like chime at each break-fast moment. Optional ambient room tone during a fast (off by default). No alert tones that feel like a phone.
- **Haptics** — used like punctuation, not exclamation marks. A soft confirmation when the reward is locked (or swapped); a deeper resonance at each break-fast.

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
- **Feast register** — *"2h 41m of feast remaining"*, sub-line is one of *"Linger." / "The kitchen is yours." / "The next table opens in 2h 41m."* The dish hero dims by ~30%.

**Type.** Time is set in the editorial serif, large enough to read at arm's length but quiet in weight — confident, not shouty. The sub-line is in the humanist sans, small, restrained.

**Time formatting** keeps the number visible at all times. The countdown is textual, not stopwatch — *"3h 12m" / "47 minutes" / "1 minute" / "45 seconds" / "1 second"* — so the editorial voice holds, but a concrete number always tells the user how long is left:

- More than an hour out: *"3h 12m until your table"* / *"3h 12m of feast remaining"*
- Within the hour: *"47 minutes" … "5 minutes" … "1 minute"*
- Within the last minute: *"59 seconds" … "1 second"*
- At zero: *"Now."*

Earlier drafts replaced the number with the phrase *"Any moment now"* in the final stretch. On hardware the substitution made the timer feel ambiguous — users couldn't tell whether the meal was thirty seconds out or three minutes out, and the phrase lost its meaning sustained across multiple minutes. The number stays. The maître-d' voice still lives in the sub-line below the timer and in the break-fast notification copy.

**Breathing.** The dish hero oscillates in scale by 1–2% over a 6–8 second cycle — slow enough to feel alive, never enough to feel animated. Reduced-motion users see a still hero with no breathing.

**Swap the locked reward.** The dish name is itself tappable, with a small copper-tinted affordance below it (*"Reconsider →"*). It re-opens the menu picker; choosing a new dish returns to the hero screen with the new dish locked. The ring and the timer continue uninterrupted — only the dish (and its associated colors) swap. The ripening picks up wherever the progress was; no reset.

## Anticipation, by design

The hero screen must make the user *feel* the meal approaching, not just read a countdown. Numbers ticking down do not produce that feeling. Stacked quiet design moves do. None of these alone would work; together they create the "if I just hold on a little longer, the wagyu is going to be even better" effect.

- **The dish ripens.** A single hero image per dish is *dynamically graded* by the app as the fast period progresses. Early in the fast, the grade is low-key — slightly cool, slightly desaturated, lighting waiting. As time passes, the grade walks toward the fully realized hero version: warmth comes up, saturation deepens, highlights bloom on the sear, the oil glistens. The user is literally watching the meal become ready. Each glance shows a more vivid dish than the glance before. Achievable with one master image per dish and a parameterized grading layer — no need for multiple commissioned shots.
- **The room warms.** A warm radial gradient behind the dish grows in intensity as the fast nears its end. Faint early, glowing late. Sells "the grill is being fired up" without ever showing flame.
- **Steam intensifies.** For hot dishes, a subtle steam animation overlays the image. Early: barely there. Late: dense, curling, drifting across the frame.
- **Language softens with proximity.** Numbers persist for clarity, but the sub-line shifts from clinical to anticipatory as milestones pass: *"Your table is being prepared." → "The wine has been opened." → "Halfway to the table." → "The kitchen is plating." → "Almost ready."* Pacing language, not motivation language.
- **The marker glows.** Late in the fast, the rhythm ring's marker emits a faint copper bloom — a few hundred milliseconds long, every few seconds, like a candle catching air. Reduced-motion: marker stays static.
- **Sound (opt-in).** Ambient room tone evolves from "early, kitchen quiet" toward "kitchen mid-service" as the fast nears completion — distant cutlery, faint sizzle of a grill, a plate being set down somewhere. At zero, the single signature chime.
- **Haptics as anticipation.** A whisper at midway and pre-arrival, more felt than noticed. A deeper, fuller resonance at break-fast — like a bell rung at the kitchen pass.
- **The break-fast reveal.** When the fast period hits zero, a slow 2–3 second hand-off: the timer block dissolves, the dish takes the full screen at full grade, the chime sounds, a deep haptic confirms. Then a single button — *"Begin."* — that opens the "find this near me" hand-off (Phase 2+) or quietly slides into the feast window.

The trick is that none of these is loud. Each is a small, slow, premium move. Stacked, they pull the user forward without ever shaming them for being there.

## The feast window

The post-break-fast period until the next fast begins.

- **Same hero screen, different register.** The dish stays on screen but dims by roughly 30%. The textual block reads time-til-next-fast: *"2h 41m of feast remaining."*
- **Sub-line copy** is one of *"Linger." / "The kitchen is yours." / "The next table opens in 2h 41m." / "Eat well."*
- **No meal tracking.** What the user eats during the feast is the user's business. The locked dish was the protagonist; the rest of the meal is private. No logging, no totals, no nudges.
- **Lock the next reward.** A quiet affordance appears in the final ~30% of the feast window — copper-tinted, low-emphasis — re-opening the menu picker (appropriate to the next break-fast time-of-day). The user may also pre-lock earlier in the feast if they want.
- **If no reward is locked when the feast ends**, the next fast begins anyway — the screen prompts *"Lock a reward when you're ready"* over a quiet still hero. The rhythm continues; the user simply hasn't picked their next target yet.

## Three menus

The catalog is organized into three menus matching the natural rhythm of the day:

- **Breakfast** — lighter wagyu compositions for morning break-fasts: wagyu okayu (porridge) with cured yolk, soft-cooked egg over warmed A5 chirashi, wagyu donburi.
- **Lunch** — mid-tier wagyu plates: wagyu yakiniku-don, wagyu sando, wagyu sukiyaki for one.
- **Dinner** — the headline plates: A5 Ribeye Tableside-Seared, Ishiyaki Wagyu, Toro Aburi Nigiri, Hand-Cut Wagyu Tartare, Uni and Wagyu Hand Roll.

When the picker opens, it defaults to the menu matching the time of the upcoming break-fast moment (e.g. a break-fast at 7 AM opens the breakfast menu; 7 PM opens dinner). The user can override via a small tab affordance — three short labels, no decorative chrome.

Three to five dishes per menu in v1; the dinner menu inherits the five existing wagyu entries.

## Voice & copy

- Anticipatory: *"Your table is being set."*
- Indulgent: *"Earned, not endured."*
- Confident: *"The kitchen knows."*
- Knowing: *"You'll know when you're ready."*
- Forgiving when a fast is ended early: *"The table will be here tomorrow."* Never *"You failed."* Never *"You broke your streak."*
- Forgiving when the whole rhythm is ended: *"The kitchen will be here when you're ready."*

Calorie counts are always labeled *approximate* and **always displayed per dish only** — never summed into daily totals, never set against a goal, never used to nudge or warn. Information, not management. Numbers never carry moral weight.

## Notifications

Premium means restraint. Default to a handful of well-timed moments per cycle, each one earning its place:

- A single midway whisper during the fast (*"Halfway to the table."*).
- A pre-arrival nudge (*"The kitchen is plating."*).
- Break-fast ready — on a HIGH-importance channel with sound and vibration so it reaches the user with the phone locked. This is the only sustained-attention notification in the app.
- A quiet feast-mode reminder when the lock-next-reward affordance appears, only if the user hasn't already pre-locked.

Never punitive. Never streak-anxiety. Notifications are opt-out granular.

## Personalization

The app should feel like being recognized by name at a restaurant the user has been to many times before.

- Remember the dishes they have locked in before.
- Surface "your usuals" subtly, never as a leaderboard.
- **Allow swapping the locked reward mid-fast without penalty — appetites change.** The swap is consequence-free: timer continues, ripening picks up where it was, no shame language.
- No social features by default. No leaderboards, no friend feeds, no shareable streaks. This is a private indulgence.

## Trust & care

- Health disclaimer surfaced calmly at first-run: fasting isn't for everyone (pregnancy, eating disorder history, certain medications, etc.). A short, dignified tap-through to a "talk to a clinician" note. Never alarmist. Never repeated.
- Local-first data **for the core ritual.** The rhythm, the locked dish, the timer state — all live on device, work offline, require no account.
- **More-than-minimal data is collected once partner features are live.** "Find this near me" requires geo. Restaurant attribution requires logging which dishes converted into directions / reservations. We will not pretend this is "minimal data collection" once the partner flow exists. It is opt-in, opt-out granular, surfaced honestly in first-run, and the app remains fully functional with geo and partner-attribution disabled.
- The catalog should be transparent about where photography and nutrition estimates come from. Partner-supplied content carries a small "from [Restaurant]" attribution.

## Technical posture

- **Offline-first for the core ritual.** The timer must be accurate and reliable with no network. The catalog must be cacheable and browsable offline once seen. Partner features (geo, reservations) gracefully degrade when offline — the rhythm and break-fast still work.
- **Timer reliability is sacred.** Survives backgrounding, app kill, device sleep, reboot, time-zone change, and DST. No drift. No reset on relaunch. The rhythm is computed from a single persisted `rhythmStartEpochMs` plus the cadence — derived state, not stored state, so every clock and every restart computes the same answer.
- **Image performance is a feature.** Progressive loading, careful prefetch on the gallery, smart compression. The hero dish never appears at low fidelity.
- **Accessibility is not optional.** Strong contrast within the dark palette, dynamic type, TalkBack labels on every image (dish name + description spoken), reduced-motion variants for all hero motion, full keyboard/switch control support on platforms that need it.
- **Privacy by default.** Honest about what is local and what crosses the wire. Partner features are opt-in and surfaced as such.

## Out of scope (do not drift here)

- Calorie totals, daily goals, macro tracking, weight-loss framing
- Meal logging beyond the single locked break-fast dish (no "what did you eat today" diary)
- Streaks, badges, leaderboards, social competition as primary loops
- Generic stock food photography
- Light, minimalist "health app" aesthetic; clinical "wellness" tone
- Emoji-laden UI, cartoon mascots, gamified celebration
- Any copyrighted or trademarked restaurant brand, menu, mark, photography, or identity used as more than private aesthetic reference (partner content excepted, under explicit agreement)
- Promoted dishes that outrank organic ones without clear *"partner"* labeling
- Push notifications selling deals, promotions, or restaurant ads

## Business model

CadentFast is **free to install and free to use.** Users never pay for the core ritual.

The customer is the restaurant. The product they buy is **a uniquely high-intent foodie funnel**: users who have spent hours anticipating a specific dish, with their geographic location known, on a predictable daily cycle. No existing restaurant marketing channel (Yelp impressions, Resy reservations, OpenTable bookings, Instagram ads) offers that combination of intent + ritual + cadence.

The phased rollout:

- **Phase 1** — User-facing product only. No restaurant features in-app beyond the curated wagyu catalog. Goal: the rhythm earns its right to exist as a thing foodies want to use daily. *(This is the slice we're building now.)*
- **Phase 2** — Manual restaurant pilot in a single metro. 5–10 partner restaurants signed in person. The app gains a *"find this near me"* affordance at break-fast that surfaces partner restaurants serving the locked dish (or close kin). Conversion metric: *of users who locked dish X, what % opened directions or visited the restaurant within the same feast window?* This number is the value proposition for everything that follows.
- **Phase 3** — Productize the partner relationship. Self-service partner sign-up. Pricing follows the Phase 2 conversion data — likely a feature-placement subscription per location plus optional reservation booking via **OpenTable / Resy / Tock deep links**. Reservations close the loop: dish locked → fast period → table booked → seated. The maître-d' voice (*"Your table is being prepared"*) becomes literally true.
- **Phase 4** — City-by-city expansion. Build direct restaurant POS integrations (Toast, Square, Resy POS, etc.) to escape platform-rent on reservations. Own the reservation rails for partner restaurants.

**Optional later: premium subscription.** Not a hedge against the restaurant model. A real product for users who want history, advanced personalization, multi-cycle planning, or other power features. The core ritual is never paywalled.

**Defending the user side.** The user is the *audience*, not the *product*. The funnel collapses the moment users feel like inventory. Yelp's reputation eroded once it was accused of manipulating reviews of advertisers; we will not repeat that. Concrete rules:

- Partner dishes are never surfaced above organic ones without clear *"from [Restaurant]"* labeling.
- The maître-d' voice and dark premium aesthetic are non-negotiable; restaurant branding lives quietly within the app's design language, never co-equal with it.
- No promoted dishes, no ad slots, no push notifications selling deals. Partner restaurants benefit by being the natural fulfillment point for an already-locked craving, not by buying user attention.

## Decisions for v1

1. **Platform — Android-first, native (Kotlin + Jetpack Compose).** The owner is on Android and must be able to test on-device. Native gives the best image performance, smoothest premium motion, the strongest background-timer story (foreground service + AlarmManager + WorkManager), and the cleanest haptics/audio. iOS port is out of scope for v1; revisit after the loop is validated.
2. **Catalog source — curated in-house.** Every dish entered by hand. The catalog is treated as an edited menu, not a database. The catalog schema partitions by `mealCategory` (breakfast / lunch / dinner).
3. **Food photography — AI-generated as MVP placeholder, replaced over time.** AI imagery is the v1 MVP source — fast, cheap, controllable. The food art director runs a strict reject gate (warped hands, fused chopsticks, plastic noodles, off-grain rice all get killed). The roadmap explicitly plans to replace AI imagery with higher-quality alternatives — licensed stock, commissioned shoots, or restaurant-partner photography — cuisine-by-cuisine and partner-by-partner as the catalog matures. Use an AI service with unambiguous commercial-use rights. Design the catalog schema so a dish can swap its imagery without losing identity, lock history, or personalization data.
4. **Default cadence — 16:8.** First-run uses sixteen hours fasting / eight hours feasting. The user can adjust later. Dev cadence — debug builds run 4:2 (four minutes fasting / two minutes feasting) for fast device iteration.
5. **"Find this near me" — Phase 2, not v1.** Implies a places provider (default: Google Places, since Android-native). The v1 slice ships without it; the architectural seams are in place from day one so Phase 2 only adds the provider, not the plumbing.
6. **Reservations — Phase 3, via OpenTable / Resy / Tock deep links to start.** Direct POS integration in Phase 4. v1 ships with no reservation surface at all.
7. **Monetization — restaurant partnerships, not user payments.** Free install. No paywall on any user-facing feature. A premium user subscription may land later; v1 has none.
8. **v1 cuisine — high-end Japanese wagyu only, partitioned across breakfast / lunch / dinner menus.** One cuisine, art-directed to perfection. Target catalog size: ~12–15 dishes total in v1 (3–5 per menu). The expansion strategy is **one cuisine at a time** — each new cuisine added only when it can match the v1 quality bar.

## Open considerations

- **AI imagery is the biggest brand risk.** A bad image undermines the entire premium feel more than any other defect. The food art director's reject gate is the hill to die on.
- **Geographic density before partner rollout.** Phase 2 lives or dies on having enough partners in one metro that a user actually has somewhere to go. Slow, deliberate, one city at a time.
- **Restaurant branding tension.** Partner content (photography, menu copy) must be ingested without diluting the app's premium minimalism. Rigorous partner content guidelines: small monogram attribution, maître-d' voice maintained for descriptions even when restaurants supply photography. Toast and Yelp visibly struggle with this; our defense is design discipline, not partner generosity.
- **No-show flexibility (Phase 3+).** Fasts are voluntary. If a user breaks fast early, any held reservation needs to gracefully cancel or shift without burning the restaurant. The maître-d' voice still applies — *"The table will be here tomorrow."* The booking system has to be operationally forgiving.
- **Places provider lock-in.** Google Places is the path of least resistance on Android but carries per-call cost; if the app grows, evaluate Foursquare / Mapbox+OSM. Wrap the provider behind an interface from day one.
- **Partner agreement terms (Phase 2+).** When deals are made, lock down: photography license, exclusivity (or its absence), take-down rights, partner labeling guidelines, and the dispute path when a restaurant feels their dish is being misrepresented.
- **Privacy revisited at Phase 2.** Once geo + visit attribution + (Phase 3) reservation data exist, the first-run experience needs a careful, honest pass on what's collected and why. Foodies will tolerate it for the convenience; deception would not survive contact with the press.

## Working with this repo

- GitHub MCP scope is limited to `sharkmelf/cadentfast`.
- **Stack:** Android, Kotlin, Jetpack Compose, Material 3 with heavy custom theming. Coil for images. Foreground service + AlarmManager + WorkManager for the rhythm. DataStore for local persistence (Room only if/when relational queries are actually needed). Google Places (wrapped behind an interface) for Phase 2 discovery. OpenTable / Resy deep links for Phase 3 reservations.
- Keep the dependency footprint tight. Premium feel is hand-built, not glued together from a dozen libraries.
- Minimum SDK: pick a recent floor (target Android 14/15, min 26 or higher). Premium audience tolerates a tighter device matrix in exchange for a better experience.
- **CI:** `.github/workflows/android-build.yml` runs `assembleDebug` + `lint` on every PR. Pin AGP version changes to their own dedicated PRs — never folded into feature work.

## The first slice — vertical, end-to-end

Before scaffolding any other code, build the **thinnest end-to-end rhythm** that proves the product on the owner's device. Everything else waits.

**In scope:**

- **Begin the rhythm.** Welcome screen with a single CTA. Tap → opens dish picker → returns to hero timer with the rhythm running.
- **Dish picker with three menus.** Vertical scrolling list of full-width dish cards, grouped under breakfast / lunch / dinner tabs. Tap a dish → soft lock-confirm haptic → returns to hero.
- **Hero timer screen with the rhythm ring.** Two-arc ring (copper for fast, brushed gold for feast) with a moving marker, dish hero inside the ring, textual time below, maître-d' sub-line, two registers (fast / feast).
- **Swap-mid-fast affordance.** Tap the dish name on the hero screen → re-opens the picker → returns with new dish locked. Timer state preserved, ripening continues from current progress.
- **Break-fast moment.** Cinematic 2–3s reveal at the end of each fast: timer block dissolves, dish at full grade, signature chime, deep haptic, single *"Begin."* button.
- **Feast window screen behavior.** Same composition, dish dims by 30%, feast-mode sub-line, time-til-next-fast displayed, "Lock the next reward" affordance appears in the final ~30% of the feast window.
- **Auto-cycling.** When the feast period ends, the next fast period begins automatically. If a reward is locked, the hero shows it; if not, prompts the user.
- **End the rhythm.** A deliberate, low-emphasis affordance (small gear or text link, not a button on the main hero) with a confirmation step. Default copy: *"End the rhythm — the kitchen will be here when you're ready."*
- **Foreground-service-backed timer** that survives backgrounding, screen lock, app kill, reboot. DataStore-backed rhythm state — `rhythmStartEpochMs` + cadence is enough to compute everything else.
- **Audible break-fast notification** on a HIGH-importance channel with default sound + vibration so a locked phone gets the moment. System defaults for now; signature chime asset lands in a later slice.
- **`BOOT_COMPLETED` receiver** to re-arm the next break-fast alarm after device reboot.

**Explicitly cut from the slice** (these come in later slices):

- Search
- Cuisines beyond wagyu (the category-select screen)
- Custom cadences (the user can't change 16:8 in v1; debug cadence is `BuildConfig` only)
- "Find this near me" / Google Places integration → Phase 2
- Reservations / OpenTable deep links → Phase 3
- Restaurant partner attribution / partner content → Phase 2+
- Sustained ambient room tone (chime + haptic only at zero)
- Personalization (usuals, recents, history)
- Settings screen beyond "End the rhythm"
- Account / sync
- First-run health disclaimer
- Dedicated accessibility audit (basic contrast and dynamic type are still respected; the audit comes when the screens settle)
- Premium subscription / Play Billing

**Slice cadence for dev work:** 4:2 (four minutes fasting, two minutes feasting). Each device-iteration round takes six minutes per full cycle, not 24 hours. Release builds use 16:8.

**Minimum agent set for this slice:** brand & identity designer (palette/type/metallic accent), product designer (welcome / picker / hero / break-fast / feast screens), motion designer (breathing, ripening, ring marker, break-fast reveal), food art director (color-gradient placeholder hero per dish, real photography deferred), sound designer (chime + haptic vocabulary), copywriter (maître-d' phrasing for lock / swap / milestones / break-fast / feast / end-rhythm), culinary curator (3–5 dishes per menu × 3 menus = 9–15 dishes), Android engineer (Compose + navigation + Coil grading), timer & state engineer (foreground service + AlarmManager + DataStore + rhythm math), QA / device tester (rhythm survival across kill / reboot / DST).

Skipped for the slice: search engineer, personalization engineer, discovery / geo engineer, reservation integration engineer, restaurant partnership manager, commerce engineer, accessibility specialist, legal / IP reviewer.

**Scaffolding posture:** single-module Android app, Kotlin DSL Gradle, ViewModel + StateFlow per screen, no DI framework, no Room. Premium feel is hand-built; the slice doesn't need a library cabinet.

## The agent team

When the user asks for "an optimal team of agents," propose roles that map to the work this product actually needs — not generic SWE archetypes. A good full ensemble:

- **Creative director** — owns the AYCE-premium feeling end to end; the single voice that vetoes anything drifting toward wellness-app generic. Final say on aesthetic.
- **Brand & identity designer** — original wordmark, palette, type system, metallic accent treatment, iconography. Guarantees no real-restaurant IP leaks in.
- **Food art director** — sourcing AI imagery and grading it to spec for v1; running the reject gate; owning the dynamic-grading curve that ripens the dish during a fast; designing the eventual hand-off to stock / commissioned / partner photography. Owns the consistency of the catalog as a visual whole.
- **Product designer (UX)** — the welcome → picker → hero → break-fast → feast → swap loop, the rhythm ring, the empty / error / loading states.
- **Motion designer** — slow, weighty transitions; the hero dish's breathing motion; the rhythm ring marker; the break-fast reveal.
- **Sound designer** — the signature break-fast chime, the optional ambient room tone, the haptic vocabulary. One signature sonic moment, not a sound library.
- **Copywriter** — dish descriptions and UI voice in the maître-d' register: anticipatory, indulgent, confident, forgiving. Owns the language of milestones, of swapping, of ending a fast early, of ending the rhythm.
- **Culinary curator** — builds the breakfast / lunch / dinner wagyu menus with accurate portions and approximate calorie counts; owns shot lists for the AI imagery pipeline and, later, partner shoots.
- **Android engineer** — Kotlin / Jetpack Compose / Material 3 custom theme / Coil. Owns the welcome → picker → hero → break-fast → feast loop and image performance.
- **Timer & state engineer** — foreground service + AlarmManager + WorkManager; background-safe, drift-free rhythm that survives kill, doze, sleep, reboot, time-zone change, and DST. Owns the rhythm math (cycle from `rhythmStartEpochMs` + cadence).
- **Search engineer** — fast, forgiving near-keyword search over the catalog (typo-tolerant, synonym-aware across menus and dish aliases). Phase-aligned with the broader catalog rollout.
- **Personalization engineer** — the "regular at the restaurant" memory: usuals, recents, gentle surfacing without nagging.
- **Discovery / geo engineer (Phase 2)** — "find this near me" at break-fast. Owns the Google Places integration behind a provider interface; designs the offline / rate-limit graceful-degrade path.
- **Reservation integration engineer (Phase 3+)** — OpenTable / Resy / Tock deep links to start; direct POS integration (Toast, Square, Resy POS) in Phase 4. Owns the no-show flexibility logic and the cancellation grace path.
- **Restaurant partnership manager (Phase 2+)** — signs partners in target metros; sets per-partner content guidelines; owns the partner-attribution rules and the partner-side dashboard product.
- **QA & device tester** — rhythm accuracy across backgrounding scenarios; image fidelity across devices; no jank on the hero screen; reservation booking accuracy in Phase 3+.
- **Accessibility & inclusion specialist** — contrast in the dark palette, dynamic type, TalkBack for image-heavy screens, reduced motion, dignity of copy in sensitive moments.
- **Legal & IP reviewer** — confirms zero infringement on restaurant brands, photography licenses, font licensing, trademarks; vets AI-imagery service commercial-use terms; drafts and reviews partnership contracts.

**Tailor the team to the slice of work the user actually wants built next.** Do not spawn all of them by default. Ask which slice to build first, then delegate only the roles that slice needs.
