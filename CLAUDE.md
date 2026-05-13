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

## Open questions to resolve before scaffolding

Before any code is written, agree with the user on:

1. **Platform target** — iOS-first (best vehicle for the premium feel), cross-platform native (Swift + Kotlin), cross-platform framework (Flutter / React Native / Expo), or web/PWA?
2. **Catalog source** — curated in-house (most premium, most expensive), licensed dataset (faster), or hybrid?
3. **Food photography pipeline** — commissioned shoots, licensed library, AI-generated reference imagery (with clear policy on whether AI imagery is acceptable for a premium brand), or a combination?
4. **Geographic restaurant discovery** — is "find this near me" at break-fast in v1 or later? Implies a places API and licensing decisions.
5. **Monetization stance** — free, one-time purchase, or subscription? Whatever the answer, the core "lock a reward and fast toward it" loop is never paywalled awkwardly.
6. **Cuisine list for v1** — the audience section is the long list; pick a smaller starting set that can be done excellently rather than a wide set done generically.

## Working with this repo

- Default working branch for this task: `claude/create-claude-md-fasting-F9UUq`.
- GitHub MCP scope is limited to `sharkmelf/cadentfast`.
- No stack is committed yet. Do not scaffold until the open questions above are answered.
- When you do scaffold, keep the dependency footprint tight. Premium feel is hand-built, not glued together from a dozen libraries.

## The agent team

When the user asks for "an optimal team of agents," propose roles that map to the work this product actually needs — not generic SWE archetypes. A good full ensemble:

- **Creative director** — owns the AYCE-premium feeling end to end; the single voice that vetoes anything drifting toward wellness-app generic. Final say on aesthetic.
- **Brand & identity designer** — original wordmark, palette, type system, metallic accent treatment, iconography. Guarantees no real-restaurant IP leaks in.
- **Food art director** — sourcing, commissioning, and grading licensed cinematic food imagery. Owns the consistency of the catalog as a visual whole.
- **Product designer (UX)** — the cuisine → dish → lock → timer flow, the search interaction, the reward-pinned timer screen, the break-fast moment, the empty/error/loading states.
- **Motion designer** — slow, weighty transitions; the hero dish's breathing motion; the countdown sweep; the break-fast reveal.
- **Sound designer** — the single break-fast chime, the optional ambient room tone, the haptic vocabulary. One signature sonic moment, not a sound library.
- **Copywriter** — dish descriptions and UI voice in the maître-d' register: anticipatory, indulgent, confident, forgiving. Owns the language of milestones and the language of giving up.
- **Culinary curator** — builds the catalog across the chosen cuisines with accurate portions and approximate calorie counts; owns shot lists; sources or commissions photography.
- **Mobile app engineer** — implements the flow on the chosen platform; image performance and timer reliability are the hard parts.
- **Timer & state engineer** — background-safe, drift-free fasting timer; survives kill, sleep, reboot, time-zone change, DST.
- **Search engineer** — fast, forgiving near-keyword search over the catalog (typo-tolerant, synonym-aware across cuisines and dish aliases).
- **Personalization engineer** — the "regular at the restaurant" memory: usuals, recents, gentle surfacing without nagging.
- **QA & device tester** — timer accuracy across backgrounding scenarios; image fidelity across devices; no jank on the hero screen; accessibility verification.
- **Accessibility & inclusion specialist** — contrast in the dark palette, dynamic type, VoiceOver for image-heavy screens, reduced motion, dignity of copy in sensitive moments.
- **Legal & IP reviewer** — confirms zero infringement on restaurant brands, photography licenses, font licensing, and trademarks.

**Tailor the team to the slice of work the user actually wants built next.** Do not spawn all of them by default. Ask which slice to build first, then delegate only the roles that slice needs.
