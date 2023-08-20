plugins {
    id(Build.androidLibrary)
}

apply {
    from("$rootDir/compose-module.gradle")
}

android {
    namespace = "kz.btokmyrza.calorytracker.onboarding_presentation"
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.onboardingDomain))
}