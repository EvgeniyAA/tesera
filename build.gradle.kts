buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(Deps.plugin_gradle)
        classpath(Deps.plugin_kotlin)
        classpath(Deps.plugin_hilt)
    }
}

plugins {
    id("com.google.dagger.hilt.android") version Versions.hilt apply false
    id ("org.jetbrains.kotlin.android") version "1.8.0" apply false
}

allprojects {
    repositories {
        maven(url = "https://jitpack.io")
        google()
        jcenter()
    }
}

val clean by tasks.creating(Delete::class) {
    delete = setOf(rootProject.buildDir)
}
