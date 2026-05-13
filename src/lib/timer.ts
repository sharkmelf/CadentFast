export const FAST_HOURS = 16;
export const EAT_HOURS = 8;
export const FAST_MS = FAST_HOURS * 60 * 60 * 1000;
export const EAT_MS = EAT_HOURS * 60 * 60 * 1000;

export type Phase = 'fasting' | 'eating';

export interface FastState {
  startedAt: number;
}

export interface PhaseInfo {
  phase: Phase;
  phaseStart: number;
  phaseEnd: number;
  elapsedMs: number;
  remainingMs: number;
  progress: number;
}

export function computePhase(startedAt: number, now: number): PhaseInfo {
  const cycleMs = FAST_MS + EAT_MS;
  const sinceStart = Math.max(0, now - startedAt);
  const intoCycle = sinceStart % cycleMs;
  const cyclesCompleted = Math.floor(sinceStart / cycleMs);
  const cycleStart = startedAt + cyclesCompleted * cycleMs;

  if (intoCycle < FAST_MS) {
    const phaseStart = cycleStart;
    const phaseEnd = cycleStart + FAST_MS;
    return {
      phase: 'fasting',
      phaseStart,
      phaseEnd,
      elapsedMs: intoCycle,
      remainingMs: FAST_MS - intoCycle,
      progress: intoCycle / FAST_MS,
    };
  }

  const eatElapsed = intoCycle - FAST_MS;
  const phaseStart = cycleStart + FAST_MS;
  const phaseEnd = phaseStart + EAT_MS;
  return {
    phase: 'eating',
    phaseStart,
    phaseEnd,
    elapsedMs: eatElapsed,
    remainingMs: EAT_MS - eatElapsed,
    progress: eatElapsed / EAT_MS,
  };
}

export function formatDuration(ms: number): string {
  const total = Math.max(0, Math.floor(ms / 1000));
  const h = Math.floor(total / 3600);
  const m = Math.floor((total % 3600) / 60);
  const s = total % 60;
  return `${String(h).padStart(2, '0')}:${String(m).padStart(2, '0')}:${String(s).padStart(2, '0')}`;
}

export function formatClock(ts: number): string {
  return new Date(ts).toLocaleTimeString(undefined, {
    hour: '2-digit',
    minute: '2-digit',
  });
}

export function formatDateTime(ts: number): string {
  return new Date(ts).toLocaleString(undefined, {
    weekday: 'short',
    hour: '2-digit',
    minute: '2-digit',
  });
}

const STORAGE_KEY = 'cadentfast.state.v1';

export function loadState(): FastState | null {
  try {
    const raw = localStorage.getItem(STORAGE_KEY);
    if (!raw) return null;
    const parsed = JSON.parse(raw) as Partial<FastState>;
    if (typeof parsed.startedAt !== 'number') return null;
    return { startedAt: parsed.startedAt };
  } catch {
    return null;
  }
}

export function saveState(state: FastState | null): void {
  if (state === null) {
    localStorage.removeItem(STORAGE_KEY);
    return;
  }
  localStorage.setItem(STORAGE_KEY, JSON.stringify(state));
}
