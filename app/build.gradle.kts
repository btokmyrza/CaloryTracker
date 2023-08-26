plugins {
    id(Build.androidApplication)
    id(Build.kotlinAndroid)
    kotlin(Build.kotlinKaptPlugin)
    id(Build.daggerHiltGradlePlugin)
}

android {
    namespace = ProjectConfig.namespace
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.appId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "kz.btokmyrza.calorytracker.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeCompilerVersion
    }
    packaging {
        resources {
            excludes += "META-INF/*"
        }
    }
}

dependencies {
    implementation(Compose.compiler)
    implementation(Compose.composeFoundation)
    implementation(Compose.composeAnimation)
    implementation(Compose.ui)
    implementation(Compose.uiToolingPreview)
    debugImplementation(Compose.uiTooling)
    implementation(Compose.material)
    implementation(Compose.composeRuntime)
    implementation(Compose.navigation)
    implementation(Compose.hiltNavigationCompose)
    implementation(Compose.viewModelCompose)
    implementation(Compose.activityCompose)

    implementation(DaggerHilt.daggerHiltAndroid)
    kapt(DaggerHilt.daggerHiltCompiler)

    implementation(project(Modules.coreData))
    implementation(project(Modules.core))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.onboardingPresentation))
    implementation(project(Modules.onboardingDomain))
    implementation(project(Modules.trackerPresentation))
    implementation(project(Modules.trackerDomain))
    implementation(project(Modules.trackerData))

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)

    implementation(Coil.coilCompose)

    implementation(Google.material)

    implementation(Retrofit.retrofit)
    implementation(Retrofit.moshiConverter)
    implementation(OkHttp.okHttp)
    implementation(OkHttp.okHttpLoggingInterceptor)

    kapt(Room.roomCompiler)
    implementation(Room.roomKtx)
    implementation(Room.roomRuntime)

    testImplementation(Testing.junit4)
    testImplementation(Testing.junitAndroidExt)
    testImplementation(Testing.truth)
    testImplementation(Testing.coroutines)
    testImplementation(Testing.turbine)
    testImplementation(Testing.composeUiTest)
    testImplementation(Testing.mockk)
    testImplementation(Testing.mockWebServer)

    androidTestImplementation(Testing.junit4)
    androidTestImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.truth)
    androidTestImplementation(Testing.coroutines)
    androidTestImplementation(Testing.turbine)
    androidTestImplementation(Testing.composeUiTest)
    androidTestImplementation(Testing.mockkAndroid)
    androidTestImplementation(Testing.mockWebServer)
    androidTestImplementation(Testing.hiltTesting)
    kaptAndroidTest(DaggerHilt.daggerHiltCompiler)
    androidTestImplementation(Testing.testRunner)
}