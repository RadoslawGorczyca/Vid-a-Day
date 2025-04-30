import com.android.build.api.dsl.DefaultConfig
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.junit5)
    alias(libs.plugins.paparazzi)
    alias(libs.plugins.sqldelight)
}

val releaseSigningPropertiesFile = file("$rootDir/keyz/release/backbones.properties")
val isRunningOnCI = System.getenv("GITHUB_CI") != null

android {

    namespace = "com.gorczycait.vidaday"

    defaultConfig {
        applicationId = "com.gorczycait.vidaday"
        minSdk = 29
        targetSdk = 35
        compileSdk = 35
        versionCode = System.getenv("CI_PIPELINE_IID")?.toIntOrNull() ?: 1
        versionName = "1.0.0"
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = "androiddebug"
            keyPassword = "androiddebug"
            storePassword = "androiddebug"
            storeFile = file("$rootDir/keyz/debug/keystore.jks")
        }
        create("release") {
            if (releaseSigningPropertiesFile.exists()) {
                val props = Properties().apply { load(FileInputStream(releaseSigningPropertiesFile)) }
                storeFile = file("$rootDir/keyz/release/backbones.jks")
                storePassword = props["storePass"]?.toString()
                keyAlias = props["keyAlias"]?.toString()
                keyPassword = props["keyPass"]?.toString()
            } else {
                println(
                    "You have to specify a valid signing properties file. " +
                        "Contact product owner to claim one.",
                )
            }
        }
    }

    buildTypes {

        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("release") {
            // release versions should be signed on CI/CD
            isMinifyEnabled = true
            isShrinkResources = true
            if (releaseSigningPropertiesFile.exists()) {
                signingConfig = signingConfigs.getByName("release")
            }
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions.add("api")

    productFlavors {
        create("dev") {
            dimension = "api"
            applicationIdSuffix = ".dev"
        }

        create("prod") {
            dimension = "api"
        }
    }

    kotlinOptions { jvmTarget = "17" }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(libs.bundles.media3)

    implementation(libs.coroutines)

    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.immutable)

    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.lifecycle.process)

    implementation(libs.bundles.compose)

    implementation(libs.splashscreen)
    implementation(libs.activity.compose)
    implementation(libs.lottie.compose)
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)
    implementation(libs.destinations.compose)
    ksp(libs.destinations.compose.ksp)
    implementation(libs.timber)
    implementation(libs.shimmer)
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.permissions)

    implementation(libs.sqldelight.driver)

    detektPlugins(libs.detekt.rules)
    detekt(libs.detekt.cli)

    implementation(libs.bundles.camera)

    testImplementation(libs.test.coroutines)
    testImplementation(libs.test.kotest)
    testImplementation(libs.test.turbine)
    testImplementation(libs.test.mockk)
    testImplementation(libs.bundles.test.junit5)
    testRuntimeOnly(libs.junit5.vintage)
    testImplementation(libs.param.injector)
    implementation(libs.showkase)
    ksp(libs.showkase.processor)
    kspTest(libs.showkase.processor)
    kspAndroidTest(libs.showkase.processor)
    testImplementation(libs.showkase.screenshot.testing)
    testImplementation(libs.showkase.screenshot.testing.paparazzi)
}

ksp {
    arg("skipPrivatePreviews", "true")
}

apply(from = "$rootDir/verification/detekt.gradle")
apply(from = "$rootDir/verification/ktlint-rules.gradle")

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    version.set("1.3.1")
    debug.set(true)
    verbose.set(true)
    android.set(true)
    outputToConsole.set(true)
    outputColorName.set("RED")
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.SARIF)
    }

    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}

fun DefaultConfig.loadManifestKeyFromLocalProperties(property: String) {
    val localProperties = Properties().apply {
        file("$rootDir/local.properties")
            .takeIf { it.exists() }
            ?.run { load(FileInputStream(this)) }
    }
    manifestPlaceholders[property] = localProperties.getProperty(property) ?: System.getenv(property)
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.gorczycait.vidaday")
        }
    }
}
