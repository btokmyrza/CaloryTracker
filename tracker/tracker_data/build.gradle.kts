plugins {
    id(Build.androidLibrary)
}

apply {
    from("$rootDir/base-module.gradle")
}

android {
    namespace = "kz.btokmyrza.calorytracker.tracker_data"
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.trackerDomain))

    "implementation"(Retrofit.retrofit)
    "implementation"(Retrofit.moshiConverter)
    "implementation"(OkHttp.okHttp)
    "implementation"(OkHttp.okHttpLoggingInterceptor)

    "kapt"(Room.roomCompiler)
    "implementation"(Room.roomKtx)
    "implementation"(Room.roomRuntime)
}