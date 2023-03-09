// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
    }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.app.plugin) apply false
    alias(libs.plugins.android.lib.plugin)  apply false
    alias(libs.plugins.kotlin.plugin) apply false
    alias(libs.plugins.kotlin.javavm.plugin) apply false

    alias(libs.plugins.hilt.plugin) apply false
    alias(libs.plugins.kotlin.serialization.plugin)
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}

//Workaround for "Expecting an expression" build error
println("")