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
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
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
        kotlinCompilerExtensionVersion = "1.3.2"
    }
}
dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2022.10.00")
    api(composeBom)
    androidTestImplementation(composeBom)
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))
    //Android Specific
    api("com.jakewharton.timber:timber:4.7.1")
    api("com.facebook.stetho:stetho:1.5.1")

    api("androidx.activity:activity-compose:1.6.1")
    api("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    api("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    api("androidx.compose.material3:material3")
    api("androidx.navigation:navigation-compose:2.5.3")

    api(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    // define any required OkHttp artifacts without version
    api("com.squareup.okhttp3:okhttp")
    api("com.squareup.okhttp3:logging-interceptor")
    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("com.squareup.retrofit2:converter-gson:2.9.0")


    api("androidx.compose.ui:ui-tooling-preview:1.3.3")
    debugApi("androidx.compose.ui:ui-tooling:1.3.3")

    //Image load and cache
    api("io.coil-kt:coil:2.2.2")

    // DI
    val hilt = "2.44.2"
    val hiltNav = "1.0.0"
    api("androidx.hilt:hilt-navigation-compose:$hiltNav")
    kapt("com.google.dagger:hilt-compiler:$hilt")
    api("com.google.dagger:hilt-android:$hilt")

    //Tests
    testImplementation("junit:junit:4.13.2")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")
}

kapt {
    correctErrorTypes = true
}