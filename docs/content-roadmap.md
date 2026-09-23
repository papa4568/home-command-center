# Home Command Center Roadmap

This file is the implementation source of truth. Work the highest-value unchecked item whose prerequisites are satisfied, verify it, then check it off. Completed milestones remain here for history; old detail can later move to an archive section.

## V0.1 — Functional command center

- [x] Create Android/Kotlin project structure.
- [x] Add mobile-first four-tab shell: Home, Rooms, Work, Shop.
- [x] Transcribe the visible renovation list into structured room/task data.
- [x] Separate DIY, purchase, contractor, decision, measurement, and appointment work.
- [x] Encode high-value dependencies, including Master Bedroom → Guest Room → Garage sequencing.
- [x] Add dependency-aware “Best next moves” ranking and unlock scores.
- [x] Add room progress and whole-house completion tracking.
- [x] Add contractor bundling by trade.
- [x] Add blocking-decision dashboard.
- [x] Add shopping view grouped by room.
- [x] Persist completed tasks locally on device.
- [x] Add unit tests for dependency logic.
- [x] Add GitHub Actions workflow that tests, builds, and uploads a debug APK.
- [x] Verify CI passes and the generated APK is structurally valid.
- [ ] Smoke-test install and launch on a physical Android device.

## V0.2 — Make the data editable

- [ ] Add task create/edit/delete UI.
- [ ] Add room create/edit/archive UI.
- [ ] Add dependency editor with blocked/unblocked preview.
- [ ] Add task notes, links, quantity, store, ordered date, delivered date, and return deadline.
- [ ] Add purchase status: research → selected → ordered → delivered → installed.
- [ ] Add decision records with options, selected choice, and rationale.
- [ ] Add reusable tags and custom filters.
- [ ] Add archive view for completed tasks.

## V0.3 — Budget and purchasing engine

- [ ] Add estimated, quoted, committed, and actual cost fields.
- [ ] Add whole-house remaining-cost forecast.
- [ ] Add room and trade budget summaries.
- [ ] Add monthly cash-flow view.
- [ ] Add “buy when needed” planning using installation dependencies.
- [ ] Flag return-window risk when installation is too far away.
- [ ] Add receipt attachment and warranty metadata.

## V0.4 — Measurements and house records

- [ ] Add measurement records per room/object.
- [ ] Add photo attachments for measurements.
- [ ] Add “measurement mission” checklist.
- [ ] Add home asset registry: brand, model, serial, purchase date, installer, warranty.
- [ ] Add paint/material registry for exact future matching.
- [ ] Add before/during/after project photos.
- [ ] Add hidden-work photo records for electrical/plumbing/wall work.

## V0.5 — Contractor mode

- [ ] Generate contractor punch lists by trade.
- [ ] Export bid-ready scopes with room, task, photos, and measurements.
- [ ] Track quotes, contractor, scheduled date, paid amount, and completion.
- [ ] Compare bids against the same scope.
- [ ] Add one-tap “bundle this trip” suggestions.
- [ ] Add contractor contact and warranty history.

## V0.6 — Smarter planning

- [ ] Improve unlock score using downstream dependency depth.
- [ ] Add “I have 15 minutes / 2 hours / a weekend” task picker.
- [ ] Add “budget available today” recommendations.
- [ ] Add room disruption and cleanup sequencing.
- [ ] Add material lead-time and contractor availability blockers.
- [ ] Add scenario planning for alternative decisions.
- [ ] Add critical-path view for fastest whole-house completion.

## V0.7 — Design system

- [ ] Add whole-house palette/material library.
- [ ] Record current design DNA: White Dove, Pale Oak, Soft Putty, mango wood, bamboo, gold hardware, warm neutrals, dark blue/denim accents.
- [ ] Add product candidate boards by room.
- [ ] Add visual compatibility notes for new purchases.
- [ ] Add links to room mockups / generated design concepts.

## V1.0 — Durable home operating system

- [ ] Add export/import backup.
- [ ] Add optional cloud sync for multiple household members.
- [ ] Add notifications for deliveries, return deadlines, contractor visits, and maintenance.
- [ ] Add recurring home-maintenance schedules.
- [ ] Add insurance/resale improvement history export.
- [ ] Add release signing and production Android build.
- [ ] Complete accessibility and larger-screen pass.
- [ ] Complete end-to-end regression test suite.

## Archive

No archived roadmap items yet.
