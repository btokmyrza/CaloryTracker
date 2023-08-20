apply {
    from("$rootDir/base-module.gradle")
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