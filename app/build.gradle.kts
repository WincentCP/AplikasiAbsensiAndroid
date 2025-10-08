plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "edu.uph.m23si1.demoproject"
    compileSdk = 34 // Using 34 as a stable version

    defaultConfig {
        applicationId = "edu.uph.m23si1.demoproject"
        minSdk = 28
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
        // The provided code works best with Java 8
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    // THIS IS THE MISSING PIECE THAT FIXES THE BINDING ERROR
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Default dependencies
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    // Firebase Bill of Materials (BoM) - Manages versions for all Firebase libraries
    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))

    // Firebase Authentication (You already had this)
    implementation("com.google.firebase:firebase-auth")

    // MISSING DEPENDENCY: Firebase Firestore for the database
    implementation("com.google.firebase:firebase-firestore")

    // MISSING DEPENDENCIES: For the UI list (RecyclerView and CardView)
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

