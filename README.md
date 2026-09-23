# Home Command Center

A mobile-first Android app for running a whole-house renovation and home-improvement backlog as a dependency-aware project system instead of a flat checklist.

## What V0.1 does

- Tracks the visible room-by-room tasks from the original renovation notes.
- Separates purchases, DIY work, contractor work, decisions, measurements, and appointments.
- Ranks **Best next moves** by what each task unlocks.
- Shows blockers and prerequisites directly on tasks.
- Bundles contractor jobs by trade so one visit can cover work across multiple rooms.
- Tracks whole-house and room completion.
- Provides a dedicated shopping view grouped by room.
- Persists completed tasks locally on the device.

A key dependency encoded in the seed data is:

**Finish Master Bedroom enough to move the mattress → free the Guest Room → move Garage boxes → install gym flooring → install gym mirrors.**

## Roadmap

The source of truth is `docs/content-roadmap.md`. Future work should take the highest-value unchecked item whose prerequisites are satisfied, implement and verify it, then check it off.

## Build locally

Requirements:

- JDK 17
- Android SDK 35
- Gradle 8.9

Run:

```bash
gradle testDebugUnitTest assembleDebug
```

Debug APK:

```
app/build/outputs/apk/debug/app-debug.apk
```

## GitHub APK builds

Every pull request and every push to `main` runs Android CI. A successful workflow uploads an artifact named:

`home-command-center-debug-apk`

That artifact contains the installable debug APK.
