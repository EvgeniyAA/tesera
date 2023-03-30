object ProjectSettings {
    const val compileSdk = 33
    const val minSdk = 24
    const val targetSdk = 33
    const val versionCode = 1
    const val versionName = "1.0.0"
}

object Versions {
    const val kotlin = "1.7.20"
    const val kotlinComposeCompiler = "1.4.0"
    const val gradle = "7.4.2"

    const val compose = "1.4.0"
    const val composeAcitivty = " 1.7.0"
    const val composeConstraintLayout = "1.0.1"
    const val composeViewModel = "2.6.1"
    const val composeNavigation = "2.5.3"
    const val okhttp = "4.10.0"
    const val retrofit = "2.9.0"

    const val timber = "4.7.1"

    const val hiltNav = "1.0.0"
    const val hilt = "2.44.2"
    const val coil = "2.2.2"
    const val securityCrypto = "1.1.0-alpha05"

    const val junit = "4.13.2"
    const val mockito = "2.2.0"
}


object Deps {
    // DI
    const val hilt_navigation = "androidx.hilt:hilt-navigation-compose:${Versions.hiltNav}"
    const val hilt_android = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hilt_compiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"

    const val compose_tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val compose_ui_preview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    const val compose_navigation = "androidx.navigation:navigation-compose:${Versions.composeNavigation}"
    const val compose_viewmodel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModel}"
    const val compose_constraintlayout =
        "androidx.constraintlayout:constraintlayout-compose:${Versions.composeConstraintLayout}"
    const val compose_activity = "androidx.activity:activity-compose:${Versions.composeAcitivty}"
    const val compose_material3 = "androidx.compose.material3:material3"

    const val okhttp_bom = "com.squareup.okhttp3:okhttp-bom:${Versions.okhttp}"
    const val okhttp = "com.squareup.okhttp3:okhttp"
    const val okhttp_logging = "com.squareup.okhttp3:logging-interceptor"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val plugin_gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val plugin_kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val plugin_hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"

    const val security_crypto = "androidx.security:security-crypto-ktx:${Versions.securityCrypto}"
    const val junit = "junit:junit:${Versions.junit}"
    const val mockito = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockito}"
}