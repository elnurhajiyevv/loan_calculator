/*
 * Created by Elnur Hajiyev on 7/13/22, 11:23 AM
 * Copyright (c) 2021 . All rights reserved to vpn
 * This code is copyrighted and using this code without agreement from authors is forbidden
 */

//version constants for the Kotlin DSL dependencies
object ApplicationVersions {
    //Project level versions
    const val gradleVersion = "8.13.2"
    const val kotlinBuildVersion = "2.3.20"
    const val kotlinCoroutinesCore = "1.8.0"
    const val crashLyticsVersion: String = "3.0.6"
    const val gmsVersion: String ="4.4.4"
    const val safeArgsVersion: String ="2.9.7"

    //libs
    const val coreKtx = "1.13.1"
    const val appcompat = "1.6.1"
    const val constraintLayout = "2.1.4"
    const val materialDesign = "1.12.0"

    //test
    const val junit = "4.13.1"
    const val extJunit = "1.1.3"
    const val espresso = "3.3.0"
    const val mockitoCore = "2.28.2"

    // Your project uses AGP 8.13.x.
    // Hilt 2.57.x stays compatible with AGP 8 and works with Kotlin 2.3 metadata
    // (also reinforced via kapt(kotlinMetadataJvm)).
    const val hiltVersion = "2.57.2"

    //Source code checks
    const val detektVersion = "1.23.8"

    //Jacoco version
    const val jacocoVersion = "0.8.8"

    //Timber
    const val timberVersion = "5.0.1"

    //Timber
    const val lifecycle = "2.8.7"

    // google signin
    const val googleAuth = "20.4.0"

    // splash version
    const val splahScreenVersion = "1.0.1"

    const val graphfityVersin = "1.2.0"

    const val room = "2.8.4"
}