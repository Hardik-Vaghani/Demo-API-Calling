@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
//    id ("kotlin-kapt")
//    id ("com.google.dagger.hilt.android")
//    id ("org.jetbrains.kotlin.kapt")
//    id ("com.google.dagger.hilt.android")

}

android {
    namespace = "com.hardik.demoapicalling"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hardik.demoapicalling"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
//    packaging{
//        resources{
//            excludes += "/META-INF{AL2.0 ,LGPL2.1}"
//            excludes += "/META-INF/gradle/incremental.annotation.processors"
//        }
//    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation ("androidx.fragment:fragment-ktx:1.3.2")

//    implementation ("com.google.dagger:hilt-android:2.49")
//    kapt ("com.google.dagger:hilt-compiler:2.49")
//    kapt ("androidx.hilt:hilt-compiler:1.0.0")
//    kapt ("com.google.dagger:hilt-android-compiler:2.49")
//    implementation ("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")

//    implementation ("com.google.dagger:hilt-android:2.52")
//    annotationProcessor ("com.google.dagger:hilt-compiler:2.52")

    // Coroutine Lifecycle Scopes
//    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
//    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Coil
//    implementation ("io.coil-kt:coil-compose:1.4.0")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
}
//kapt {
//    correctErrorTypes = true
//}