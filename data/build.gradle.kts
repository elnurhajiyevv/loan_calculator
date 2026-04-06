plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
}
//apply(from = "$rootDir/jacoco.gradle")

android {
    compileSdk = ApplicationConfig.compileSdk
    buildToolsVersion = ApplicationConfig.buildToolsVersion
    namespace = "loan.calculator.data"

    defaultConfig {
        minSdk = ApplicationConfig.minSdk
        multiDexEnabled = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlin {
        jvmToolchain(21)
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }
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
    implementation(project(ApplicationModules.commonModule))

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

    implementation("com.google.code.gson:gson:2.13.2")

    // Dagger Hilt
    implementation(ApplicationDependencies.hiltAndroid)
    kapt(ApplicationDependencies.hiltCompiler)
    kapt(ApplicationDependencies.kotlinMetadataJvm)

}