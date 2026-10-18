# BottomsUp 🎲🍸

**Spin. Dare. Own the night.**

BottomsUp is a Vegas-inspired party and dare game for adults. The app starts with quick party prompts, Truth or Dare, a Vegas-themed deck, Wild Cards, and Most Likely To. Players can save prompts, swap a challenge, or pass at any time. Drink rules are optional; water breaks and no-alcohol play are supported.

## Features in this starter build

- Game-first home screen with BottomsUp colors and branding
- Quick Dare, Sip or Dare, Truth or Dare, Vegas Deck, Most Likely To, and Wild Card modes
- Local player list with round rotation
- Done, Swap, Pass, and save-prompt actions
- Dares and Saved tabs
- Secondary Vegas-themed screen for future venue/nightlife features
- No account or network permission required for the core game

## Build APK with GitHub Actions (recommended)

Push this repository to GitHub and open the **Actions** tab. The `Android APK Build` workflow compiles the debug APK on a GitHub-hosted runner and uploads `bottomsup-debug-apk` as a downloadable artifact. You do not need to install Android SDK or run a build command in Termux.

The workflow invokes the repository's Gradle wrapper inside GitHub Actions because this Kotlin/Android starter is a Gradle project. Gradle is not run in Termux.

## License / attribution

This project is adapted from [Allowexe/TheGreatestCocktailApp](https://github.com/Allowexe/TheGreatestCocktailApp), which is distributed under the MIT License. The original `LICENSE` file is retained. BottomsUp branding and the gameplay/home-screen rewrite are changes in this fork.

## Responsible play

For adults 21+ in the United States. Challenges are optional, passing is always allowed, and no challenge requires alcohol. Never drink and drive.
