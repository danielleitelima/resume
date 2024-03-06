plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false

    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization)

    `kotlin-dsl`
}

dependencies {
    implementation(libs.kotlin.serialization)
    implementation(libs.kotlin.gradle)
}