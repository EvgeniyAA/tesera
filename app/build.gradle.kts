val toothpickVersion = "3.1.0"
val SERVER_ENDPOINT = "\"https://api.tesera.ru/\""
plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")

    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")
}
apply(from = "${project.rootDir}/codequality/ktlint.gradle.kts")
android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "com.gemini.base"
        minSdkVersion(23)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            buildConfigField("String", "ENDPOINT", SERVER_ENDPOINT)
        }
        getByName("debug") {
            buildConfigField("String", "ENDPOINT", SERVER_ENDPOINT)
        }
    }
}
dependencies {
    implementation(project(":core"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
    implementation("io.github.reactivecircus.flowbinding:flowbinding-android:0.10.2")
    implementation("io.github.reactivecircus.flowbinding:flowbinding-lifecycle:0.10.2")
    kapt("com.github.stephanenicolas.toothpick:toothpick-compiler:$toothpickVersion")
}