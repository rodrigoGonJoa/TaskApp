

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.gradle)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
    // ./gradlew detekt    ./gradlew detektGenerateConfig
}

android {
    namespace = "rodrigo.taskapp"
    defaultConfig {
        applicationId = "rodrigo.taskapp"
        versionCode = Integer.parseInt(libs.versions.versionCode.get())
        versionName = libs.versions.versionName.get()

        compileSdk = Integer.parseInt(libs.versions.androidSdkCompile.get())
        minSdk = Integer.parseInt(libs.versions.androidSdkMin.get())
        targetSdk = Integer.parseInt(libs.versions.androidSdkTarget.get())
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {useSupportLibrary = true}
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    lint {
        // Enable all lint checks that are considered warnings
        checkAllWarnings = true
        // Treat all warnings as errors
        warningsAsErrors = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        aidl = false
        buildConfig = false
        renderScript = false
        shaders = false
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // General dependencies
    implementation(libs.kotlin.logging)
    implementation(libs.slf4j.simple)

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.foundation)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Dynamic Feature don't recognize BOM
    implementation(libs.androidx.activity.compose)

    // Hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Room
    runtimeOnly(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Quality
    // detekt -p detekt-compose-<VERSION>-all.jar -c your/config/detekt.yml
    // detekt -p "C:\Program Files\Java\Resources\detekt-compose-0.3.11-all.jar" -c "config/detekt/detekt.yml"
    detektPlugins(libs.detekt.compose)
    debugImplementation(libs.leakcanary.android)
}
