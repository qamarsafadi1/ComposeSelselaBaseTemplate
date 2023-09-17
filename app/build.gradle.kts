import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.gms.googleServices)
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")

}
val baseUrl: String = gradleLocalProperties(rootDir).getProperty("baseUrl")

android {
    namespace = "com.selsela.cpapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.selsela.composebaseselsela"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
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
        compose = true
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.graphics)
    implementation(libs.ui.util)
    implementation(libs.ui.viewbinding)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.material)
    implementation(libs.material.icons)
    implementation(libs.accompanist.navigation.material)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.coil.compose)
    implementation(libs.lottie.compose)
    implementation(libs.styleabletoast)
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    // Data store
    implementation(libs.androidx.preference.ktx)
    // Networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    // Fcm
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.dynamic.links)
    // DI
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.play.services.location)
    implementation(libs.androidx.leanback)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.lifecycle)
    kapt(libs.hilt.android.compiler)
    // Room
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.compose.state.events)
    implementation(libs.kpermissions)
    // Google Maps
    implementation(libs.maps.compose)
    implementation(libs.play.services.maps)
    // Alerter
    implementation(libs.alerter)
    // in app language
    implementation(libs.androidx.appcompat)
    implementation(libs.zoomable)
    implementation(libs.androidx.runtime.tracing)
    implementation(libs.accompanist.webview)
    implementation("com.github.ihsanbal:LoggingInterceptor:3.1.0-rc5") {
        exclude(
            group = "org.json", module = "json"
        )
    }
    implementation(libs.accompanist.permissions)
    implementation(libs.barcode.scanning)
    implementation (libs.androidx.camera.camera2)
    implementation (libs.androidx.camera.lifecycle)
    implementation (libs.androidx.camera.view)
    implementation (libs.barcode.scanning)

    kapt(libs.compiler)
    implementation(libs.glide)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}