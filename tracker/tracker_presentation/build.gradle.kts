plugins {
    id(Build.androidLibrary)
}

apply {
    from("$rootDir/compose-module.gradle")
}

android {
    namespace = "kz.btokmyrza.calorytracker.tracker_presentation"
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.trackerDomain))

    "implementation"(Coil.coilCompose)
}