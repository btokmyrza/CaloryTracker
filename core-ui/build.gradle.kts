plugins {
    id(Build.androidLibrary)
}

apply {
    from("$rootDir/compose-module.gradle")
}

android {
    namespace = "kz.btokmyrza.calorytracker.core_ui"
}