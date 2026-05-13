# CadentFast

Fasting timer by Cadent — a simple 16:8 intermittent fasting tracker.

Built with Vite + React + TypeScript.

## Develop

```bash
npm install
npm run dev
```

## Build

```bash
npm run build
npm run preview
```

## How it works

- Tap **Start fast now** to begin a 16-hour fast.
- A ring shows time remaining in the current phase.
- When the 16-hour fast ends, the app switches automatically to the 8-hour eating window, then cycles back.
- Use the "started X hours ago" shortcuts to backdate a fast you already began.
- State is persisted to `localStorage` so refreshing keeps your timer.
