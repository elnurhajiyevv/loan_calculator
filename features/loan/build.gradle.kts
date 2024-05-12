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
    namespace = "loan.calculator.loan"

    defaultConfig {
        minSdk = ApplicationConfig.minSdk
        targetSdk = ApplicationConfig.targetSdk
        multiDexEnabled = true
        testInstrumentationRunner = ApplicationConfig.androidTestInstrumentation
        consumerProguardFiles( "consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            resValue("string","admob_id","ca-app-pub-5232085807124057/4750798955")
            isMinifyEnabled = true
        }
        getByName("debug") {
            resValue("string","admob_id","ca-app-pub-3940256099942544/9214589741")
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
    implementation(project(ApplicationModules.showCase))
    //implementation("com.github.mreram:showcaseview:1.4.1")

    implementation(ApplicationDependencies.appLibraries)
    implementation(ApplicationDependencies.testLibraries)
    implementation(ApplicationDependencies.navigationLibraries)


    implementation(ApplicationDependencies.hiltAndroid)
    implementation(ApplicationDependencies.playServiceAdsLite)
    kapt(ApplicationDependencies.hiltCompiler)
    implementation(ApplicationDependencies.playServiceLocation)
    implementation("com.google.android.libraries.places:places:3.4.0")

    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.28")

}