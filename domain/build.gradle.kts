plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
    id("kotlinx-serialization")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
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

    implementation("com.google.code.gson:gson:2.8.5")

}