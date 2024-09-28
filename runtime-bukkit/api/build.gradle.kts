plugins {
    id("daedalus.runtime-bukkit-conventions")
    id("daedalus.publishing-conventions")
}

dependencies {
    api(project(":daedalus-api"))
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}