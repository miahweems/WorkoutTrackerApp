plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "2.0.0-1.0.21"
    alias(libs.plugins.kotlin.compose)
}





android {
    namespace = "com.example.workouttrackerapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.workouttrackerapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("com.google.firebase:firebase-auth-ktx:22.1.1")
    implementation("com.google.firebase:firebase-auth:22.1.1")



    // Compose
    implementation("androidx.compose.ui:ui:1.4.3")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.navigation:navigation-compose:2.6.0")

    // Room
    implementation("androidx.room:room-runtime:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")
    ksp("androidx.room:room-compiler:2.6.0")

    // LiveData
    implementation(libs.androidx.runtime.livedata)

    // Compose BOM
    implementation(platform(libs.androidx.compose.bom))

    // Compose Tooling
    debugImplementation("androidx.compose.ui:ui-tooling:1.4.3")
    debugImplementation(libs.androidx.ui.test.manifest)

    // UI Graphics / Preview
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    // Test
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Extra AndroidX Stuff
    implementation(libs.androidx.monitor)
    implementation(libs.androidx.junit.ktx)


}

apply(plugin = "com.google.gms.google-services")
