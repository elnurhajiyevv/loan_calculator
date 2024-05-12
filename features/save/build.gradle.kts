/*
 * Created by Elnur Hajiyev on on 7/15/22, 4:21 PM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = ApplicationConfig.compileSdk
    buildToolsVersion = ApplicationConfig.buildToolsVersion
    namespace = "loan.calculator.save"

    defaultConfig {
        minSdk = ApplicationConfig.minSdk
        targetSdk = ApplicationConfig.targetSdk
        multiDexEnabled = true
        testInstrumentationRunner = ApplicationConfig.androidTestInstrumentation
        consumerProguardFiles( "consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
        getByName("debug") {
            isMinifyEnabled = false
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
        viewBinding = true
    }
}

dependencies {
    implementation(project(ApplicationModules.coreModule))
    implementation(project(ApplicationModules.domainModule))
    implementation(project(ApplicationModules.commonModule))
    implementation(project(ApplicationModules.uiKit))
    //implementation(project(ApplicationModules.showCase))


    implementation(ApplicationDependencies.appLibraries)
    implementation(ApplicationDependencies.testLibraries)
    implementation(ApplicationDependencies.navigationLibraries)

    implementation(ApplicationDependencies.hiltAndroid)
    implementation(ApplicationDependencies.playServiceAdsLite)
    kapt(ApplicationDependencies.hiltCompiler)
    implementation(ApplicationDependencies.shimmer)
    implementation(ApplicationDependencies.playServiceLocation)
    implementation("com.google.android.libraries.places:places:3.4.0")


    implementation("com.itextpdf:itextg:5.5.10") // iText PDF
    implementation("com.github.barteksc:android-pdf-viewer:2.8.2")

    implementation(project(ApplicationModules.showCase))
}