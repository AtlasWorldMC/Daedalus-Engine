plugins {
    id("daedalus.publishing-conventions")
}

dependencies {
    api(project(":daedalus-api"))
    api(libs.gson)
}