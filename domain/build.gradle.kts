plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
    id("kotlinx-serialization")
}

java {
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
}

dependencies {
    // Coroutines
    implementation(ApplicationDependencies.kotlinCoroutinesCore)

    // Dagger Hilt
    implementation(ApplicationDependencies.hiltCore)
    kapt(ApplicationDependencies.hiltCompiler)

    // Test
    testImplementation(ApplicationDependencies.testLibraries)

    implementation(ApplicationDependencies.kotlinxSerialization)

    implementation(ApplicationDependencies.gsonLib)

}