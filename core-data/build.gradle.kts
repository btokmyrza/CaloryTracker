plugins {
    id(Build.androidLibrary)
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = "kz.btokmyrza.calorytracker.core_data"
}

dependencies {
    implementation(project(Modules.core))
}