plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    kotlin("kapt")
}

android {
    namespace = ApplicationConfig.applicationId
    compileSdk = ApplicationConfig.compileSdk

    defaultConfig {
        resValue("string","admob_developer","")
        minSdk = ApplicationConfig.minSdk
        versionCode = ApplicationConfig.versionCode
        targetSdk = ApplicationConfig.targetSdk
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
            isMinifyEnabled = false
            lintOptions {
                isCheckReleaseBuilds = false
                isAbortOnError = false
            }
            resValue("string","admob_developer","ca-app-pub-5232085807124057~6063880628")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName("debug") {
            isMinifyEnabled = false
            resValue("string","admob_developer","ca-app-pub-3940256099942544~3347511713")
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

    implementation("com.google.android.gms:play-services-auth:21.1.1")
    implementation("com.google.android.gms:play-services-base:18.4.0")
    implementation("com.google.firebase:firebase-bom:33.0.0")
    implementation("com.google.firebase:firebase-auth")


    api(ApplicationDependencies.googlePlayAds)
}
