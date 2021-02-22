val toothpickVersion = "3.1.0"
val SERVER_ENDPOINT = "\"https://api.tesera.ru/\""
plugins {
    id("com.android.application")

    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")
}

androidExtensions {
    isExperimental = true
}

apply(from = "${project.rootDir}/codequality/ktlint.gradle.kts")
android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "com.boardgames.tesera"
        minSdkVersion(24)
        targetSdkVersion(30)
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
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.0")
    implementation("io.github.reactivecircus.flowbinding:flowbinding-android:0.10.2")
    implementation("io.github.reactivecircus.flowbinding:flowbinding-lifecycle:0.10.2")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.4.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
    kapt("com.github.stephanenicolas.toothpick:toothpick-compiler:$toothpickVersion")
}