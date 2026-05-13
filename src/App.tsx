import { useEffect, useMemo, useRef, useState } from 'react';
import {
  computePhase,
  formatClock,
  formatDateTime,
  formatDuration,
  loadState,
  saveState,
  type FastState,
} from './lib/timer';
import './App.css';

function useNow(intervalMs = 1000): number {
  const [now, setNow] = useState(() => Date.now());
  useEffect(() => {
    const id = window.setInterval(() => setNow(Date.now()), intervalMs);
    return () => window.clearInterval(id);
  }, [intervalMs]);
  return now;
}

export default function App() {
  const [state, setState] = useState<FastState | null>(() => loadState());
  const now = useNow(1000);

  useEffect(() => {
    saveState(state);
  }, [state]);

  const info = useMemo(
    () => (state ? computePhase(state.startedAt, now) : null),
    [state, now],
  );

  const start = () => setState({ startedAt: Date.now() });
  const reset = () => setState(null);

  const startEarlier = (hoursAgo: number) => {
    setState({ startedAt: Date.now() - hoursAgo * 60 * 60 * 1000 });
  };

  return (
    <main className="app">
      <header className="header">
        <div className="brand">
          <span className="brand-dot" aria-hidden />
          <span className="brand-name">CadentFast</span>
        </div>
        <p className="tagline">16:8 fasting timer</p>
      </header>

      {info && state ? (
        <RunningView
          info={info}
          startedAt={state.startedAt}
          onReset={reset}
        />
      ) : (
        <IdleView onStart={start} onStartEarlier={startEarlier} />
      )}

      <footer className="footer">
        <span>Cadent · {new Date().getFullYear()}</span>
      </footer>
    </main>
  );
}

function IdleView({
  onStart,
  onStartEarlier,
}: {
  onStart: () => void;
  onStartEarlier: (hoursAgo: number) => void;
}) {
  return (
    <section className="card idle">
      <h1 className="card-title">Ready when you are</h1>
      <p className="card-sub">
        Start a 16-hour fast followed by an 8-hour eating window.
      </p>
      <button className="btn btn-primary" onClick={onStart}>
        Start fast now
      </button>
      <div className="backdate">
        <span className="backdate-label">Already started?</span>
        <div className="backdate-row">
          {[1, 2, 4, 8].map((h) => (
            <button
              key={h}
              className="btn btn-ghost"
              onClick={() => onStartEarlier(h)}
            >
              {h}h ago
            </button>
          ))}
        </div>
      </div>
    </section>
  );
}

function RunningView({
  info,
  startedAt,
  onReset,
}: {
  info: ReturnType<typeof computePhase>;
  startedAt: number;
  onReset: () => void;
}) {
  const isFasting = info.phase === 'fasting';
  const confirmTimer = useRef<number | null>(null);
  const [confirming, setConfirming] = useState(false);

  const handleReset = () => {
    if (!confirming) {
      setConfirming(true);
      confirmTimer.current = window.setTimeout(() => setConfirming(false), 3000);
      return;
    }
    if (confirmTimer.current) window.clearTimeout(confirmTimer.current);
    setConfirming(false);
    onReset();
  };

  useEffect(() => {
    return () => {
      if (confirmTimer.current) window.clearTimeout(confirmTimer.current);
    };
  }, []);

  return (
    <section className="card running">
      <div className={`phase-pill ${isFasting ? 'is-fasting' : 'is-eating'}`}>
        {isFasting ? 'Fasting' : 'Eating window'}
      </div>

      <Ring progress={info.progress} accent={isFasting ? 'fast' : 'eat'}>
        <div className="ring-label">{isFasting ? 'Time left fasting' : 'Time left to eat'}</div>
        <div className="ring-time">{formatDuration(info.remainingMs)}</div>
        <div className="ring-elapsed">
          {formatDuration(info.elapsedMs)} elapsed
        </div>
      </Ring>

      <dl className="meta">
        <div className="meta-row">
          <dt>Phase started</dt>
          <dd>{formatClock(info.phaseStart)}</dd>
        </div>
        <div className="meta-row">
          <dt>{isFasting ? 'Fast ends' : 'Eating ends'}</dt>
          <dd>{formatClock(info.phaseEnd)}</dd>
        </div>
        <div className="meta-row">
          <dt>Cycle started</dt>
          <dd>{formatDateTime(startedAt)}</dd>
        </div>
      </dl>

      <button
        className={`btn ${confirming ? 'btn-danger' : 'btn-ghost'}`}
        onClick={handleReset}
      >
        {confirming ? 'Tap again to confirm' : 'Reset'}
      </button>
    </section>
  );
}

function Ring({
  progress,
  accent,
  children,
}: {
  progress: number;
  accent: 'fast' | 'eat';
  children: React.ReactNode;
}) {
  const size = 240;
  const stroke = 14;
  const r = (size - stroke) / 2;
  const c = 2 * Math.PI * r;
  const clamped = Math.min(1, Math.max(0, progress));
  const dash = c * clamped;

  return (
    <div className="ring" style={{ width: size, height: size }}>
      <svg width={size} height={size} viewBox={`0 0 ${size} ${size}`}>
        <circle
          cx={size / 2}
          cy={size / 2}
          r={r}
          fill="none"
          stroke="var(--ring-track)"
          strokeWidth={stroke}
        />
        <circle
          cx={size / 2}
          cy={size / 2}
          r={r}
          fill="none"
          stroke={accent === 'fast' ? 'var(--accent-strong)' : 'var(--warn)'}
          strokeWidth={stroke}
          strokeLinecap="round"
          strokeDasharray={`${dash} ${c}`}
          transform={`rotate(-90 ${size / 2} ${size / 2})`}
        />
      </svg>
      <div className="ring-inner">{children}</div>
    </div>
  );
}
