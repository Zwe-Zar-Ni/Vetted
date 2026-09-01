plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)

    // ? serialization for navigation
    id("org.jetbrains.kotlin.plugin.serialization") version "2.4.0"

    // ? For Room Databasse
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.vaddshah2626.vetted"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.vaddshah2626.vetted"
        minSdk = 34
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                isMinifyEnabled = true
                isShrinkResources = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // ? Navigation
    implementation("androidx.navigation:navigation-compose:2.9.8")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")

    // ? Icons
    implementation("androidx.compose.material:material-icons-extended")

    // ? Koin Core & Android support
    implementation("io.insert-koin:koin-android:4.2.2")
    // ? Koin Jetpack Compose integration (provides koinViewModel())
    implementation("io.insert-koin:koin-androidx-compose:4.2.2")

    // ? Room Database
    implementation("androidx.room:room-runtime:2.8.4")
    ksp("androidx.room:room-compiler:2.8.4")

    // ? Coil for image loading
    implementation("io.coil-kt.coil3:coil-compose:3.5.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.0")

    // ? For AppWidgets support
    implementation("androidx.glance:glance-appwidget:1.2.0")
    // ? For interop APIs with Material 3
    implementation("androidx.glance:glance-material3:1.2.0")

    // ? Charts
    implementation ("io.github.ehsannarmani:compose-charts:1.0.0")

}
