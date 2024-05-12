plugins {
    id("com.android.library")
    id("kotlin-android")
}
//apply(from = "$rootDir/jacoco.gradle")

android {
    compileSdk = ApplicationConfig.compileSdk
    buildToolsVersion = ApplicationConfig.buildToolsVersion
    namespace = "loan.calculator.core"

    defaultConfig {
        minSdk = ApplicationConfig.minSdk
        targetSdk = ApplicationConfig.targetSdk
        multiDexEnabled = true
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
    implementation(project(ApplicationModules.uiKit))

    //Results Api
    implementation(ApplicationDependencies.resultsLibraries)


    // Lifecycle extensions
    implementation(ApplicationDependencies.lifecycleExtensions)

    implementation(project(ApplicationModules.uiKit))

    //Constraint
    implementation(ApplicationDependencies.appLibraries)

    //Navigation
    implementation(ApplicationDependencies.navigationLibraries)


    //Firebase
    implementation(platform(ApplicationDependencies.firebaseBom))
    implementation(ApplicationDependencies.firebaseLibraries)

    // Logger
    api(ApplicationDependencies.timber)

    // Coroutines
    implementation(ApplicationDependencies.kotlinStdLib)
    implementation(ApplicationDependencies.kotlinReflectLib)

    testImplementation(ApplicationDependencies.testLibraries)
    androidTestImplementation(ApplicationDependencies.androidTestLibraries)
}