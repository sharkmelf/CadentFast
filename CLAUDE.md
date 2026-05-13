# CadentFast

A premium fasting timer for people who love food. Not a willpower app — a delayed-gratification app. The user picks the meal they're craving, locks it in as the reward, and the timer becomes the runway to that meal.

## Audience

Foodies who can't wait for their next meal. They eat out often and well:

- Japanese AYCE / wagyu (Chubby Cattle, Wagyu Master, Gyu-kaku tier)
- Korean BBQ (Gen tier)
- Brazilian churrascaria, Mongolian BBQ
- Sushi (Kura, Aikan, omakase counters)
- Dumplings (Din Tai Fung, Paradise Dynasty)
- Specialty ramen, Thai, Indian, Vietnamese
- Protein-forward by default

They are not trying to suppress cravings. They are trying to earn them.

## Product principle

The chosen meal is the protagonist of the entire fasting session. It is pinned, large, and beautiful throughout the timer. The timer is the supporting character — a countdown to the reward, not a discipline drill.

Tone the copy and UX accordingly: anticipatory, indulgent, confident. Never shaming, never clinical, never "wellness." This app is the maître d', not the trainer.

## Core flow

1. **Cuisine select** — large category tiles (Japanese AYCE, Korean BBQ, Brazilian, Mongolian, Sushi, Dumplings, Ramen, Thai, Indian, Vietnamese, …).
2. **Menu browse** — within a category, a rich gallery of dishes. Each card has:
   - High-quality hero photo (the dish is the hero, full bleed where possible)
   - Name and short evocative description
   - Single-serving portion
   - Estimated calorie count
3. **Search** — keyword/near-match filter across name + description + cuisine tags (e.g. "wagyu", "spicy tuna", "xiao long bao", "pad see ew").
4. **Lock the reward** — user confirms the dish; it becomes the meal they're fasting toward.
5. **Timer** — fasting countdown with the locked dish pinned front-and-center the entire time. Elapsed time and time-to-reward are present but secondary. Subtle, premium motion. No badges, no streak guilt-trips.
6. **Break fast** — celebratory hand-off into the meal.

## Design language

Aesthetic target: the feeling of being seated at the best table in a high-end AYCE Japanese or Korean BBQ — dark, warm, intimate, gold/copper accents, deep reds and charcoals, generous negative space, restrained typography, cinematic food photography. Premium and grown-up.

Inspired by the *feeling* of places like Chubby Cattle. Do not lift logos, marks, color palettes, illustrations, photography, menu copy, or proprietary visual identity from any real restaurant. All marks and assets must be original or properly licensed.

Design rules of thumb:

- Photography is the product. Treat image quality, crop, and color grading as a first-class concern.
- Type: one editorial serif for dish names, one clean sans for UI. Tight tracking, generous leading.
- Color: dark base (near-black charcoal), warm neutrals, a single metallic accent (brushed gold or copper), red sparingly as a craving cue.
- Motion: slow, weighty, confident. Never bouncy or playful.
- No emoji in UI. No cartoon iconography. No "wellness green."

## Out of scope (don't drift here)

- Calorie shaming, macro nagging, weight-loss framing
- Streaks/badges/leaderboards as primary loop
- Generic stock food photography
- Light, minimalist "health app" aesthetic
- Any copyrighted restaurant IP

## Working with this repo

- Default branch for this task: `claude/create-claude-md-fasting-F9UUq`.
- GitHub MCP scope is limited to `sharkmelf/cadentfast`.
- Stack is not yet committed — pick one that serves the design goals (rich imagery, smooth motion, offline-friendly timer). Confirm with the user before scaffolding.
- Before writing code, agree on: platform target (iOS-first vs. cross-platform vs. web), framework, image pipeline, and where the food catalog lives.

## Agent team prompt

When the user asks for "an optimal team of agents," propose roles that map to the work this product actually needs, not generic SWE archetypes. A good starting set:

- **Creative director** — owns the AYCE-premium feel end to end; vetoes anything that drifts toward wellness-app generic.
- **Brand & visual identity designer** — original wordmark, palette, type system, metallic accent treatment; ensures no real-restaurant IP leaks in.
- **Food photography / art director** — sourcing or commissioning licensed cinematic food imagery; crop, grading, and consistency standards across the catalog.
- **Product designer (UX)** — cuisine→dish→lock→timer flow, search interaction, "reward pinned" timer screen, break-fast moment.
- **Motion designer** — slow, weighty transitions; the timer's hero motion; break-fast celebration.
- **Copywriter** — dish descriptions and UI voice: anticipatory, indulgent, confident; never clinical.
- **Culinary researcher / menu curator** — builds the catalog across the listed cuisines with accurate portions and calorie estimates; sources licensed photography or shot lists.
- **Mobile/app engineer** — implements the flow on the chosen platform; image performance and timer reliability are the hard parts.
- **Timer/state engineer** — background-safe, drift-free fasting timer; survives app kill, device sleep, time-zone changes.
- **Search engineer** — fast, forgiving near-keyword search over the catalog (typo-tolerant, synonym-aware: "soup dumpling" ↔ "xiao long bao").
- **QA / device tester** — verifies timer accuracy across backgrounding, image fidelity across devices, no jank on the hero screen.
- **Legal/IP reviewer** — confirms zero infringement on restaurant brands, photography licenses, and font licensing.

Tailor the team to what the user actually wants built next; don't spawn all of them by default. Ask which slice to build first before delegating.
