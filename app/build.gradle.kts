plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.dagger.hilt.android")
    kotlin("android")
    kotlin("kapt")
}
apply(from = "${project.rootDir}/codequality/ktlint.gradle.kts")
android {
    compileSdk = 33
    defaultConfig {
        applicationId = "com.tesera.base"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    namespace = "com.tesera.base"
}
dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":feature:login"))
    implementation(project(":feature:splash"))
    val hiltNav = "1.0.0"
    implementation("androidx.hilt:hilt-navigation-compose:$hiltNav")
    implementation("com.google.dagger:hilt-android:2.44.2")
    kapt("com.google.dagger:hilt-compiler:2.44.2")
}

kapt {
    correctErrorTypes = true
}