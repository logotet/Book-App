Book Info App (Kotlin Multiplatform)

📖 Overview

Book Info App is a Kotlin Multiplatform (KMP) application that provides book details from an API. The app runs on Android, Desktop and iOS, sharing business logic while using platform-specific UI implementations.

📌 Features

✅ Search for books by title or author 

✅ View book details (description, rating, cover image)

✅ Save books to favorites (local database using Room)

✅ Works on Android, Desktp & iOS with shared business logic


🛠️ Tech Stack

Kotlin Multiplatform (KMP) - Shared business logic

Jetpack Compose - UI for Android

Ktor Client - Networking

Room - Local database

Coil - Image loading

Kotlinx Serialization - JSON parsing

🚀 Getting Started

Prerequisites

Android Studio (for Android development)

Xcode (for iOS development)

Kotlin Multiplatform Plugin

Build & Run

Android

./gradlew androidApp:installDebug

iOS

cd iosApp && pod install
open iosApp.xcworkspace

Run the app via Xcode on an iOS simulator or device.

Desktop

./gradlew run
