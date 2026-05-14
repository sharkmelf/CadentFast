#!/usr/bin/env bash
# Install the Android command-line SDK plus the packages CadentFast needs.
#
# Idempotent: exits 0 if the SDK + required packages are already present.
# Graceful:  exits 0 with a warning if dl.google.com is not reachable
#            (e.g. sandbox host allowlist), so a SessionStart hook calling
#            this script never blocks the session.
#
# Persisting ANDROID_HOME / PATH is the caller's job — see the lines printed
# at the end of a successful install.

set -u

ANDROID_HOME="${ANDROID_HOME:-$HOME/android-sdk}"
PLATFORM="android-35"
BUILD_TOOLS="35.0.0"
CMDLINE_URL="https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip"

log() { printf '[setup-android-sdk] %s\n' "$*"; }

sdkmanager_bin="$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager"

if [ -x "$sdkmanager_bin" ] \
  && [ -d "$ANDROID_HOME/platforms/$PLATFORM" ] \
  && [ -d "$ANDROID_HOME/build-tools/$BUILD_TOOLS" ] \
  && [ -d "$ANDROID_HOME/platform-tools" ]; then
  log "Already installed at $ANDROID_HOME (platforms/$PLATFORM, build-tools/$BUILD_TOOLS, platform-tools)."
  exit 0
fi

log "Probing $CMDLINE_URL"
http_code=$(curl -sI -o /dev/null -w "%{http_code}" --max-time 10 "$CMDLINE_URL" || echo "ERR")
if [ "$http_code" != "200" ] && [ "$http_code" != "302" ]; then
  log "WARN: $CMDLINE_URL returned $http_code."
  log "WARN: dl.google.com is likely not in the sandbox host allowlist."
  log "WARN: Skipping install. Re-run after the host is allowlisted."
  exit 0
fi

log "Downloading cmdline-tools..."
mkdir -p "$ANDROID_HOME/cmdline-tools"
tmpzip="$(mktemp /tmp/cmdline-tools.XXXXXX.zip)"
trap 'rm -f "$tmpzip"' EXIT

if ! curl -fsSL --retry 3 --retry-delay 2 -o "$tmpzip" "$CMDLINE_URL"; then
  log "WARN: download failed. Skipping install."
  exit 0
fi

log "Unpacking..."
unzip -q -o "$tmpzip" -d "$ANDROID_HOME/cmdline-tools"
rm -rf "$ANDROID_HOME/cmdline-tools/latest"
mv "$ANDROID_HOME/cmdline-tools/cmdline-tools" "$ANDROID_HOME/cmdline-tools/latest"

export PATH="$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH"

log "Accepting licenses..."
yes | sdkmanager --licenses >/dev/null 2>&1 || true

log "Installing platform-tools, platforms;$PLATFORM, build-tools;$BUILD_TOOLS..."
sdkmanager "platform-tools" "platforms;$PLATFORM" "build-tools;$BUILD_TOOLS"

log "Installed at $ANDROID_HOME"
log "To make it persistent in your shell, add:"
log "  export ANDROID_HOME=\"$ANDROID_HOME\""
log "  export PATH=\"\$ANDROID_HOME/cmdline-tools/latest/bin:\$ANDROID_HOME/platform-tools:\$PATH\""
