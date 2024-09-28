plugins {
    id("daedalus.runtime-bukkit-conventions")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.2.3"
}

dependencies {
    // server api
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")

    // resource-pack plugin api
    compileOnly("team.unnamed:creative-central-api:1.4.0")

    // daedalus-engine dependencies
    implementation(project(":daedalus-api"))
    implementation(project(":daedalus-reader-blockbench"))
    implementation(project(":daedalus-runtime-bukkit-api"))
    implementation(project(":daedalus-runtime-bukkit-adapt-v1_20_R3", configuration = "reobf"))
}

tasks {
    runServer {
        downloadPlugins {
            modrinth("central", "1.3.0") // creative-central
        }

        minecraftVersion("1.20.4")
    }
    shadowJar {
        dependencies {
            exclude(dependency("team.unnamed:creative-api"))
            exclude(dependency("com.google.code.gson:gson"))
            exclude(dependency("net.kyori:adventure-api"))
            exclude(dependency("net.kyori:examination-.*"))
            exclude(dependency("org.jetbrains:annotations"))
        }
    }
}