# CineVerse

This is a bachelor's degree project for Mobile Device Programming course (academic year 2023/24).
CineVerse is an Android application for discovering movies and TV series, searching content, managing a personal watchlist, and writing reviews.

## Overview

The app combines:

- **TMDB integration** for movie/TV discovery, details, genres, trends, and search
- **Firebase Authentication** for account management (email/password + Google Sign-In)
- **Firebase Realtime Database** for cloud user data such as watchlists and reviews
- **Room** for local persistence (content caching and search history)

The codebase is organized around an **MVVM + Repository** architecture.

## Main Features

- User authentication (register, login, forgot password, Google login)
- Email verification flow before accessing the main dashboard
- Home feed with categorized sections for movies and TV series (popular, trending, top rated, upcoming, airing, etc.)
- Genre-based browsing
- Content details screen with metadata, cast/crew, videos, and reviews
- Full-text content search with:
  - keyword suggestions
  - local search history
  - voice input (speech-to-text)
- Personal watchlist management
- User reviews and ratings
- In-app settings (theme, language, username, about)

## Tech Stack

- **Language**: Java
- **UI**: AndroidX, Material Components
- **Architecture**: MVVM + Repository
- **Networking**: Retrofit
- **Image loading**: Glide
- **Persistence**: Room
- **Backend services**: Firebase Auth + Firebase Realtime Database
- **Build system**: Gradle (Android application module `:app`)

## Project Structure

High-level source layout:

- `app/src/main/java/com/example/cineverse/view` – Activities/Fragments (UI layer)
- `app/src/main/java/com/example/cineverse/viewmodel` – ViewModels
- `app/src/main/java/com/example/cineverse/repository` – Repositories (domain/data coordination)
- `app/src/main/java/com/example/cineverse/data` – Models, local DB, and data sources
- `app/src/main/java/com/example/cineverse/service/api` – Retrofit API interfaces
- `app/src/main/res` – Layouts, strings, navigation graphs, and resources

Additional material:

- `STYLE.md` – design/style references used by the project
- `demo/CineVerse_demo.mp4` – demo video
- `presentazione_documentazione/` – project documentation assets (written in Italian)

## Prerequisites

Before building the app, ensure you have:

- Android Studio (latest stable recommended)
- Android SDK configured
- JDK compatible with your Android Gradle Plugin (AGP 8.2.0)
- A Firebase project configured for this app
- TMDB API credentials

## Configuration

### 1) `local.properties`

Create or update:

`local.properties` (in the project root)

with the required keys:

```properties
sdk.dir=/path/to/Android/Sdk
access_token_auth=YOUR_TMDB_BEARER_TOKEN
api_key_auth=YOUR_TMDB_API_KEY
```

> `access_token_auth` and `api_key_auth` are required by the app build and TMDB calls.

### 2) Firebase `google-services.json`

Add your Firebase configuration file:

`app/google-services.json`

This file is intentionally gitignored and must be provided locally.

## Build & Run

From project root:

```bash
./gradlew assembleDebug
```

For installing/running through Android Studio, open the root folder and run the `app` configuration on an emulator/device.

## Testing

Unit tests:

```bash
./gradlew test
```

Android instrumentation tests (requires emulator/device):

```bash
./gradlew connectedAndroidTest
```

Lint:

```bash
./gradlew lint
```

## Team

- [Milanesi Luca](https://github.com/Luca-02) (886279)
- [Pirnau Ion](https://github.com/Ion-Pirnau) (887465)
- [Polignone Alessandro](https://github.com/AlessandroPolignone) (886000)
