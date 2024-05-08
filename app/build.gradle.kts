plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.sumitranjan.videoaddproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sumitranjan.videoaddproject"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("androidx.media3:media3-exoplayer:1.3.1")
    implementation("androidx.media3:media3-exoplayer-dash:1.3.1")
    implementation("androidx.media3:media3-ui:1.3.1")
    // ViewModel
    implementation( libs.androidx.lifecycle.viewmodel.ktx.v270)
    // LiveData
    implementation( libs.androidx.lifecycle.livedata.ktx.v270)
    implementation( libs.androidx.lifecycle.runtime.ktx.v270)
    // Saved state module for ViewModel
    implementation( libs.androidx.lifecycle.viewmodel.savedstate.v270)
    implementation( libs.androidx.lifecycle.common.java8.v270)
    //coroutine
    implementation( libs.kotlinx.android)
    implementation( libs.kotlinx.core)
}