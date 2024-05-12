/*
 * Created by Elnur Hajiyev on 7/13/22, 11:19 AM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */
plugins {
    id("io.gitlab.arturbosch.detekt").version(ApplicationVersions.detektVersion)
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id ("com.github.ivancarras.graphfity") version "1.0.0"
}
apply(from = "$rootDir/dynatrace.gradle")

buildscript {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }

    }

    dependencies {
        classpath("com.android.tools.build:gradle:${ApplicationVersions.gradleVersion}")
        classpath("com.dynatrace.tools.android:gradle-plugin:8.+")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${ApplicationVersions.kotlinBuildVersion}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${ApplicationVersions.kotlinBuildVersion}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${ApplicationVersions.hiltVersion}")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${ApplicationVersions.detektVersion}")
        classpath("com.google.gms:google-services:${ApplicationVersions.gmsVersion}")
        classpath("com.google.firebase:firebase-crashlytics-gradle:${ApplicationVersions.crashLyticsVersion}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${ApplicationVersions.safeArgsVersion}")
        classpath("com.github.ivancarras:graphfity-plugin:${ApplicationVersions.graphfityVersin}")
    }
}

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")


    detekt {
        input = files("src/main/java", "src/main/kotlin")
        parallel = true
        config = files("$rootDir/.detekt/detekt-config.yml")
        reports {
            html {
                enabled = true
                destination = file("build/reports/detekt.html")
            }
        }
    }

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
    }

    configurations.all {
        resolutionStrategy.force("org.objenesis:objenesis:2.6")
    }
}


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}