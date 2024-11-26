plugins {
    alias(libs.plugins.android.application) // Plugin untuk aplikasi Android
    alias(libs.plugins.jetbrains.kotlin.android) // Plugin Kotlin untuk Android
    kotlin("kapt") // Plugin Kapt untuk annotation processing
}

android {
    namespace = "com.example.eatwise"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.eatwise"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // CameraX
    val cameraxVersion = "1.5.0-alpha01"
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view.v150alpha01)

    // Core AndroidX Libraries
    implementation(libs.androidx.core.ktx.v1131)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout.v214)
    implementation(libs.androidx.navigation.fragment.ktx.v284)
    implementation(libs.androidx.navigation.ui.ktx.v284)

    // ViewBindingPropertyDelegate
    implementation(libs.viewbindingpropertydelegate.noreflection)

    // Retrofit and Gson for Networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Glide for Image Loading
    implementation(libs.glide)

    // Room Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    // Coroutines for Asynchronous Programming
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Testing Libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
