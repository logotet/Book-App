plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.room)
    id("kotlin-kapt")
}

android {
    namespace = "com.logotet.bookapp.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.logotet.bookapp.android"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.compose.ui.tooling)

    // Ktor
    implementation(libs.ktor.client.okhttp)
    implementation(libs.bundles.ktor)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)
    api(libs.koin.core)

    // Navigation
    implementation(libs.jetbrains.compose.navigation)
    implementation(libs.kotlinx.serialization.json)

    // Coil
    implementation(libs.bundles.coil)

    // Room
    implementation(libs.sqlite.bundled)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
}