plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
    id("kotlinx-serialization")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    // Coroutines
    implementation(ApplicationDependencies.kotlinCoroutinesCore)

    // Dagger Hilt
    implementation(ApplicationDependencies.hiltCore)
    kapt(ApplicationDependencies.hiltCompiler)
    kapt(ApplicationDependencies.kotlinMetadataJvm)

    // Test
    testImplementation(ApplicationDependencies.testLibraries)

    implementation(ApplicationDependencies.kotlinxSerialization)

    implementation(ApplicationDependencies.gsonLib)

}