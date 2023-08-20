plugins {
    id(Build.androidLibrary)
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = "kz.btokmyrza.calorytracker.onboarding_domain"
}

dependencies {
    "implementation"(project(Modules.core))
}