plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.dicodingg.bangkit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dicodingg.bangkit"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // Definisikan SENDGRID_API_KEY dalam buildConfigField

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


    buildFeatures {
        //noinspection DataBindingWithoutKapt
        dataBinding = true

        viewBinding = true
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
    // Firebase Authentication
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation("com.google.firebase:firebase-auth:21.0.3")
    implementation("com.google.android.gms:play-services-auth:21.2.0")

    // Material Components
    implementation("com.google.android.material:material:1.9.0" )


    implementation ("com.github.bumptech.glide:glide:4.11.0") // Glide for image loading
    implementation ("androidx.recyclerview:recyclerview:1.2.1") // RecyclerView dependency

    implementation ("com.android.support:cardview-v7:26.1.0")

    implementation ("androidx.work:work-runtime-ktx:2.8.1")

    implementation ("androidx.databinding:databinding-runtime:7.4.0")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.3")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.3")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    implementation ("com.github.kirich1409:viewbindingpropertydelegate:1.5.3")

    implementation ("com.github.bumptech.glide:glide:4.15.0")

    

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")

    // AndroidX dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.viewpager2)
    implementation(libs.firebase.database)
    implementation(libs.play.services.auth)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.androidx.cardview)
    implementation ("androidx.datastore:datastore-preferences:1.1.1")
    implementation(libs.androidx.datastore.preferences.core.jvm)

    // Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
