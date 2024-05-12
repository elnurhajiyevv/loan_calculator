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
    // Coroutines
    implementation(ApplicationDependencies.kotlinCoroutinesCore)
    implementation(project(ApplicationModules.domainModule))

    // Test
    testImplementation(ApplicationDependencies.testLibraries)

    // Dagger Hilt
    implementation(ApplicationDependencies.hiltCore)
    implementation(ApplicationDependencies.hiltAndroid)
    kapt(ApplicationDependencies.hiltCompiler)

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