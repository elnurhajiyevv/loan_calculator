/*
 * Created by Elnur Hajiyev on 7/13/22, 11:23 AM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

import org.gradle.api.artifacts.dsl.DependencyHandler

object ApplicationDependencies {

    //Kotlin lib
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${ApplicationVersions.kotlinBuildVersion}"
    const val kotlinCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${ApplicationVersions.kotlinCoroutinesCore}"
    const val kotlinCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${ApplicationVersions.kotlinCoroutinesCore}"
    const val kotlinReflectLib = "org.jetbrains.kotlin:kotlin-reflect:${ApplicationVersions.kotlinBuildVersion}"


    // Lifecycle extensions
    private const val lifecycleExtension = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    private const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${ApplicationVersions.lifecycle}"
    private const val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:${ApplicationVersions.lifecycle}"
    private const val lifecycleViewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${ApplicationVersions.lifecycle}"


    //Net client
    const val okhttp3 = "com.squareup.okhttp3:okhttp:4.9.1"
    const val retrofit2 = "com.squareup.retrofit2:retrofit:2.9.0"
    const val retrofit2SerializationConverter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.1"

    //Moshi
    const val moshi = "com.squareup.moshi:moshi:1.10.0"
    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:1.10.0"
    const val retrofit2ConverterMoshi = "com.squareup.retrofit2:converter-moshi:2.9.0"

    const val kotlinxSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0"


    const val securityCrypto = "androidx.security:security-crypto:1.1.0-alpha03"

    const val shimmer = "com.facebook.shimmer:shimmer:0.5.0"

    //Core components
    const val appcompat = "androidx.appcompat:appcompat:${ApplicationVersions.appcompat}"
    private const val coreKtx = "androidx.core:core-ktx:${ApplicationVersions.coreKtx}"
    private const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${ApplicationVersions.constraintLayout}"
    private const val materialDesign =
        "com.google.android.material:material:${ApplicationVersions.materialDesign}"

    //Test components
    private const val junit = "junit:junit:${ApplicationVersions.junit}"
    private const val extJUnit = "androidx.test.ext:junit-ktx:${ApplicationVersions.extJunit}"
    private const val espressoCore = "androidx.test.espresso:espresso-core:${ApplicationVersions.espresso}"
    private const val mockito = "org.mockito:mockito-core:${ApplicationVersions.mockitoCore}"
    private const val mockk = "io.mockk:mockk:1.12.0"
    private const val robolectric =    "org.robolectric:robolectric:4.6.1"
    //Dagger Hilt
    const val hiltAndroid = "com.google.dagger:hilt-android:${ApplicationVersions.hiltVersion}"
    const val hiltCore = "com.google.dagger:hilt-core:${ApplicationVersions.hiltVersion}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${ApplicationVersions.hiltVersion}"

    //Results API
    private const val activityResult = "androidx.activity:activity-ktx:1.2.0"
    private const val fragmentResult = "androidx.fragment:fragment-ktx:1.3.0"

    //Navigation Lib
    private const val uiNavigation = "androidx.navigation:navigation-ui-ktx:${ApplicationVersions.safeArgsVersion}"
    private const val fragmentNavigation = "androidx.navigation:navigation-fragment-ktx:${ApplicationVersions.safeArgsVersion}"

    //Timber Library
    const val timber = "com.jakewharton.timber:timber:${ApplicationVersions.timberVersion}"


    const val splashScreen = "androidx.core:core-splashscreen:${ApplicationVersions.splahScreenVersion}"

    const val firebaseBom = "com.google.firebase:firebase-bom:31.1.0"
    const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
    private const val analytics = "com.google.firebase:firebase-analytics-ktx"
    private const val cloudMessaging = "com.google.firebase:firebase-messaging"


    // biometric auth
    const val biometric = "androidx.biometric:biometric:1.0.1"

    // animation
    const val dynamicanimation = "androidx.dynamicanimation:dynamicanimation-ktx:1.0.0-alpha03"

    //lottie animation
    const val lottie = "com.airbnb.android:lottie:6.4.0"

    //SMS retriever
    private const val googlePlayAuth = "com.google.android.gms:play-services-auth:20.4.0"
    private const val googlePlayAuthPhone = "com.google.android.gms:play-services-auth-api-phone:17.5.1"
    private const val googlePlayAuthBase = "com.google.android.gms:play-services-base:18.1.0"
    const val googlePlayAds = "com.google.android.gms:play-services-ads:22.1.0"


    const val pinOtpView = "io.github.chaosleung:pinview:1.4.4"

    const val libphonenumber = "com.googlecode.libphonenumber:libphonenumber:8.12.42"


    const val glide = "com.github.bumptech.glide:glide:4.11.0"
    const val glideCompiler = "com.github.bumptech.glide:compiler:4.11.0"

    // persistent storage
    const val roomRuntime = "androidx.room:room-runtime:${ApplicationVersions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${ApplicationVersions.room}"
    const val roomKtx = "androidx.room:room-ktx:${ApplicationVersions.room}"

    val appLibraries = arrayListOf<String>().apply {
        add(coreKtx)
        add(appcompat)
        add(constraintLayout)
        add(materialDesign)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
        add(mockito)
        add(mockk)
        add(robolectric)
    }

    val resultsLibraries = arrayListOf<String>().apply {
        add(activityResult)
        add(fragmentResult)
    }

    val navigationLibraries = arrayListOf<String>().apply {
        add(uiNavigation)
        add(fragmentNavigation)
    }

    val firebaseLibraries = arrayListOf<String>().apply {
        add(crashlytics)
        add(analytics)
        add(cloudMessaging)
    }

    val lifecycleExtensions = arrayListOf<String>().apply {
        add(lifecycleExtension)
        add(lifecycleCommon)
        add(lifecycleRuntime)
        add(lifecycleViewmodel)
    }

    val phoneAuthLibraries = arrayListOf<String>().apply {
        add(googlePlayAuth)
        add(googlePlayAuthPhone)
        add(googlePlayAuthBase)
        add(googlePlayAds)
    }
}

//util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.kapt(libPath: String) {
    add("kapt", libPath)
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}
