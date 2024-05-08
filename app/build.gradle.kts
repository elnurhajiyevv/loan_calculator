plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    kotlin("kapt")
}

android {
    compileSdkVersion(ApplicationConfig.compileSdk)
    buildToolsVersion(ApplicationConfig.buildToolsVersion)

    defaultConfig {
        applicationId = ApplicationConfig.applicationId
        minSdkVersion(ApplicationConfig.minSdk)
        targetSdkVersion(ApplicationConfig.targetSdk)
        versionCode = ApplicationConfig.versionCode
        versionName = ApplicationConfig.frameworkVersion
        testInstrumentationRunner = ApplicationConfig.androidTestInstrumentation
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = "elha"
            keyPassword = "test1234"
            storeFile = file("../debug.jks")
            storePassword = "test1234"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            lintOptions {
                isCheckReleaseBuilds = false
                isAbortOnError = false
            }
            buildConfigField("String","APP_NAME","\"loan calculator\"")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName("debug") {
            isMinifyEnabled = false
            buildConfigField("String","APP_NAME","\"loan calculator\"")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    flavorDimensions += "default"

    flavorDimensions("default")

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
    implementation(project(ApplicationModules.dataModule))
    implementation(project(ApplicationModules.commonModule))
    implementation(project(ApplicationModules.saveModule))
    implementation(project(ApplicationModules.loanModule))
    implementation(project(ApplicationModules.settingModule))
    implementation(project(ApplicationModules.uiKit))
    implementation(project(ApplicationModules.showCase))

    // Dagger Hilt
    implementation(ApplicationDependencies.hiltAndroid)
    kapt(ApplicationDependencies.hiltCompiler)

    //Results Api
    implementation(ApplicationDependencies.resultsLibraries)

    // Navigation
    implementation(ApplicationDependencies.navigationLibraries)

    implementation(ApplicationDependencies.kotlinStdLib)
    implementation(ApplicationDependencies.appLibraries)

    //Firebase
    implementation(platform(ApplicationDependencies.firebaseBom))
    implementation(ApplicationDependencies.firebaseLibraries)

    testImplementation(ApplicationDependencies.testLibraries)
    androidTestImplementation(ApplicationDependencies.androidTestLibraries)

    //OneSignal
    implementation("com.onesignal:OneSignal:[4.0.0, 4.99.99]")

    // splash
    implementation(ApplicationDependencies.splashScreen)

    implementation("com.google.android.gms:play-services-auth:20.4.0")
    implementation("com.google.android.gms:play-services-base:18.1.0")
    implementation("com.google.firebase:firebase-bom:31.1.1")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.startapp:inapp-sdk:4.11.+")


    api(ApplicationDependencies.googlePlayAds)
}
