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
    compileSdkVersion(ApplicationConfig.compileSdk)
    buildToolsVersion(ApplicationConfig.buildToolsVersion)

    defaultConfig {
        minSdkVersion(ApplicationConfig.minSdk)
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
    implementation(project(ApplicationModules.domainModule))
    implementation(project(ApplicationModules.commonModule))

    implementation(ApplicationDependencies.appLibraries)
    implementation(ApplicationDependencies.testLibraries)
    implementation(ApplicationDependencies.navigationLibraries)

    implementation(ApplicationDependencies.hiltAndroid)
    kapt(ApplicationDependencies.hiltCompiler)
}