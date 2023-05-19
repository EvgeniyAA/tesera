plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.tesera.feature.dashboard"
    compileSdk = ProjectSettings.compileSdk

    defaultConfig {
        minSdk = ProjectSettings.minSdk
        targetSdk = ProjectSettings.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.kotlinComposeCompiler
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":designsystem"))
    implementation(project(":feature:home"))
    implementation(project(":feature:search"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:games"))
    implementation(project(":feature:gameDetails"))
    implementation(project(":feature:media"))
    implementation(project(":feature:comments"))
    implementation(project(":feature:news"))
    implementation(project(":feature:newsDetails"))
    implementation(project(":feature:users"))
    implementation(project(":feature:market"))

    implementation(Deps.hilt_navigation)
    implementation(Deps.hilt_android)
    kapt(Deps.hilt_compiler)
}

kapt {
    correctErrorTypes = true
}