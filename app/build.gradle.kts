plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.buzidroidapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.buzidroidapplication"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
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
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    // Android Ktx
    implementation(libs.bundles.androidKtx)
    // Jetpack Navigation
    implementation(libs.bundles.navigation)
    // KotlinX Serialization
    implementation(libs.kotlinx.serialization.json)
    // TensorFlow Lite
    implementation(libs.tensorflow.lite)
    // Ktor Client
    implementation(libs.bundles.ktorClient)
    // Preferences DataStore
    implementation(libs.datastore.preferences)
    // Dagger 2
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    // Coil
    implementation(libs.coil)
}