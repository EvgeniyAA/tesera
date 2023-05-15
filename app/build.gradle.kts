plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.dagger.hilt.android")
    kotlin("android")
    kotlin("kapt")
}
apply(from = "${project.rootDir}/codequality/ktlint.gradle.kts")
android {
    compileSdk = ProjectSettings.compileSdk
    defaultConfig {
        applicationId = "com.tesera.base"
        minSdk = ProjectSettings.minSdk
        targetSdk = ProjectSettings.targetSdk
        versionCode = ProjectSettings.versionCode
        versionName = ProjectSettings.versionName
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        debug {
        }
        create("performance") {
            initWith(getByName("debug"))
            isDebuggable = false
            isMinifyEnabled = true
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.kotlinComposeCompiler
    }
    namespace = "com.tesera.base"
}
dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":designsystem"))
    implementation(project(":feature:login"))
    implementation(project(":feature:splash"))
    implementation(project(":feature:dashboard"))

    // to remove actionBar
    implementation(Deps.androidxAppCompat)

    implementation(Deps.hilt_navigation)
    implementation(Deps.hilt_android)
    kapt(Deps.hilt_compiler)
}

kapt {
    correctErrorTypes = true
}