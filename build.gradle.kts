plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt.gradle) apply false
    alias(libs.plugins.ksp) apply false
    // ./gradlew detekt    ./gradlew detektGenerateConfig
    alias(libs.plugins.detekt)
    // ./gradlew buildHealth
    alias(libs.plugins.dependency.analysis)
}
