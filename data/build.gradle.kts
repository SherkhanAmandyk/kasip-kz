plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
}

android {
    namespace = "kz.kasip.data"
    compileSdk = 34
    defaultConfig {
        minSdk = 22
        multiDexEnabled = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(libs.androidx.datastore.preferences)

    implementation(libs.androidx.multidex)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestorm)
    implementation(libs.firebase.storage)
}