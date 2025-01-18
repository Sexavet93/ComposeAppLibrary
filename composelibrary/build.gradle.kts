plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotlin.compose)
    id("maven-publish")
    id("com.android.library")
}

android {
    namespace = "com.compose.composelibrary"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

publishing {
    publications {
        create<MavenPublication>("bar"){
            groupId = "com.compose"
            artifactId = "composelibrary"
            version = "1.1"

            afterEvaluate {
                from(components["release"])
            }
        }
    }

    repositories {
        maven {
            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/Sexavet93/ComposeAppLibrary")

            credentials {
                username = (project.findProperty("gpr.user") ?: "").toString()
                password = "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"
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    compileOnly(libs.flutter)
}