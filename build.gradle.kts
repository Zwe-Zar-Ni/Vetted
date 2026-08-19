// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // ? For navigation
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.serialization") version "2.2.0"

    // ? For Room Database
    id("com.google.devtools.ksp") version "2.3.4" apply false

}