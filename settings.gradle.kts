plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "daedalus-engine"

includePrefixed("api")
includePrefixed("reader-blockbench")
includePrefixed("runtime-bukkit:api")
includePrefixed("runtime-bukkit:adapt-v1_20_R3")
includePrefixed("runtime-bukkit:test-plugin")
includePrefixed("runtime-minestom")

fun includePrefixed(name: String) {
    val kebabName = name.replace(':', '-')
    val path = name.replace(':', '/')

    include("daedalus-$kebabName")
    project(":daedalus-$kebabName").projectDir = file(path)
}