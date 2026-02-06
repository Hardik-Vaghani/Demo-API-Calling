// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        // other repositories...
        mavenCentral()
    }
    dependencies {
        // other plugins...
//        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.52")
    }
}
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
//    id ("com.google.dagger.hilt.android") version ("2.49") apply false
//    id ("org.jetbrains.kotlin.kapt") version ("1.6.20") apply false
//    id ("com.google.dagger.hilt.android") version ("2.52") apply false
}
true // Needed to make the Suppress annotation work for the plugins block