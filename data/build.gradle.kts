plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
}
apply(from = "$rootDir/jacoco.gradle")

android {
    compileSdkVersion(ApplicationConfig.compileSdk)
    buildToolsVersion(ApplicationConfig.buildToolsVersion)

    defaultConfig {
        minSdkVersion(ApplicationConfig.minSdk)
        targetSdkVersion(ApplicationConfig.targetSdk)
        multiDexEnabled = true
    }

    buildTypes {
        getByName("release") {
            buildConfigField("String","APP_KEY","\"a4532da10d1c4ab49e371129231808\"")
            isMinifyEnabled = true
        }
        getByName("debug") {
            buildConfigField("String","APP_KEY","\"a4532da10d1c4ab49e371129231808\"")
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

    // Coroutines
    implementation(ApplicationDependencies.kotlinCoroutinesCore)
    implementation(ApplicationDependencies.kotlinCoroutinesAndroid)
    implementation(ApplicationDependencies.kotlinxSerialization)

    // Net client
    api(ApplicationDependencies.okhttp3)
    api(ApplicationDependencies.retrofit2)
    api(ApplicationDependencies.retrofit2SerializationConverter)
    api(ApplicationDependencies.loggingInterceptor)
    api(ApplicationDependencies.kotlinxSerialization)
    implementation(project(mapOf("path" to ":common")))

    // Unit Test
    testImplementation(ApplicationDependencies.testLibraries)

    // Moshi
    api(ApplicationDependencies.moshi)
    api(ApplicationDependencies.moshiKotlin)
    api(ApplicationDependencies.retrofit2ConverterMoshi)

    // persistent storage
    implementation(ApplicationDependencies.roomRuntime)
    kapt(ApplicationDependencies.roomCompiler)
    implementation(ApplicationDependencies.roomKtx)

    implementation("com.google.code.gson:gson:2.8.5")

    // Dagger Hilt
    implementation(ApplicationDependencies.hiltAndroid)
    kapt(ApplicationDependencies.hiltCompiler)

}