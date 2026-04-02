plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}
//apply(from = "$rootDir/jacoco.gradle")

android {
    compileSdk = ApplicationConfig.compileSdk
    buildToolsVersion = ApplicationConfig.buildToolsVersion
    namespace = "loan.calculator.common"

    defaultConfig {
        minSdk = ApplicationConfig.minSdk
        targetSdk = ApplicationConfig.targetSdk
        multiDexEnabled = true
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
    // Coroutines
    implementation(ApplicationDependencies.kotlinCoroutinesCore)
    implementation(project(ApplicationModules.domainModule))

    // Test
    testImplementation(ApplicationDependencies.testLibraries)

    // Dagger Hilt
    implementation(ApplicationDependencies.hiltCore)
    implementation(ApplicationDependencies.hiltAndroid)
    kapt(ApplicationDependencies.hiltCompiler)
    kapt(ApplicationDependencies.kotlinMetadataJvm)

    //Timber
    implementation(ApplicationDependencies.timber)

    //Firebase
    implementation(platform(ApplicationDependencies.firebaseBom))
    implementation(ApplicationDependencies.firebaseLibraries)

    //EncryptedSharedPref
    implementation(ApplicationDependencies.securityCrypto)


    testImplementation(ApplicationDependencies.testLibraries)
    androidTestImplementation(ApplicationDependencies.androidTestLibraries)
}