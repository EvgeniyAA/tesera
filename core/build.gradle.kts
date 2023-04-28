import org.jetbrains.kotlin.config.KotlinCompilerVersion
val SERVER_ENDPOINT = "\"https://api.tesera.ru/\""

plugins {
    id("com.android.library")
    id("com.google.dagger.hilt.android")
    kotlin("android")
    kotlin("kapt")
}
apply(from = "${project.rootDir}/codequality/ktlint.gradle.kts")
android {
    compileSdk = ProjectSettings.compileSdk
    defaultConfig {
        minSdk = ProjectSettings.minSdk
        targetSdk = ProjectSettings.targetSdk
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "ENDPOINT", SERVER_ENDPOINT)

    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    namespace = "com.tesera.core"

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.kotlinComposeCompiler
    }
}
dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2022.10.00")
    api(composeBom)
    androidTestImplementation(composeBom)
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))
    //Android Specific
    api(Deps.timber)

    api(Deps.compose_activity)
    api(Deps.compose_constraintlayout)

    api(Deps.compose_viewmodel)
    api(Deps.compose_material3)
    api(Deps.compose_material)
    api(Deps.compose_navigation)
    api(Deps.compose_ui_preview)
    debugApi(Deps.compose_tooling)

    api(platform(Deps.okhttp_bom))
    api(Deps.okhttp)
    api(Deps.okhttp_logging)
    api(Deps.retrofit)
    api(Deps.retrofit_gson)
    api(Deps.coil)
    api(Deps.hilt_navigation)
    api(Deps.hilt_android)
    kapt(Deps.hilt_compiler)
    api(Deps.paging_runtime)
    api(Deps.paging_compose)

    //Tests
    testApi(Deps.junit)
    testApi(Deps.mockito)
    testApi(Deps.mockK)
}

kapt {
    correctErrorTypes = true
}