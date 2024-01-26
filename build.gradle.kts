import net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.*;

plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
    id("xyz.jpenilla.run-paper") version "2.2.2"
    id("io.freefair.lombok") version "8.4"
}

group = "me.voper"
version = "1.0.0"

bukkit {
    name = "ExtraUtilities"
    version = project.version.toString()
    description = "A Slimefun addon that adds utility commands and tools"
    author = "Voper"
    main = "me.voper.extrautilities.ExtraUtilities"
    apiVersion = "1.20"
    depend = listOf("Slimefun")
    softDepend = listOf("Networks", "InfinityExpansion")

    permissions {
        register("extrautilities.anyone.*") {
            children = listOf("extrautilities.anyone.restore", "extrautilities.anyone.restoreall")
            childrenMap = mapOf(
                    "extrautilities.anyone.restore" to true,
                    "extrautilities.anyone.restoreall" to true
            )
            default = NOT_OP
        }
        register("extrautilities.anyone.restore") {
            description = "Allows you to restore a storage"
        }
        register("extrautilities.anyone.restoreall") {
            description = "Allows you to restore all the storages in your inventory"
        }

        register("extrautilities.admin.*") {
            children = listOf("extrautilities.admin.setstorageamount", "extrautilities.admin.setcontent")
            default = OP
        }
        register("extrautilities.admin.setstorageamount") {
            description = "Allows you to set the amount of the item storage in the storage you are holding"
        }
        register("extrautilities.admin.setcontent") {
            description = "Allows you to set the item stored inside the storage you are holding"
        }
    }
}

repositories {
    mavenCentral()
    maven("https://jitpack.io/")
    maven("https://repo.aikar.co/content/groups/aikar/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("com.github.Slimefun:Slimefun4:RC-36")
    compileOnly("com.github.Sefiraat:Networks:3de3c9d608")
    compileOnly("com.github.Mooy1:InfinityExpansion:d995144")
    implementation("co.aikar:acf-paper:0.5.1-SNAPSHOT")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
    options.isFork = true
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    archiveClassifier = "original"
}

tasks.shadowJar {
    dependsOn(tasks.test)
    relocate("co.aikar.commands", "me.voper.extrautilities.acf") { exclude("META-INF/**") }
    relocate("co.aikar.locales", "me.voper.extrautilities.locales") { exclude("META-INF/**") }
    minimize()
    archiveClassifier = ""
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.runServer {
    downloadPlugins {
        url("https://blob.build/dl/Slimefun4/Dev/1116")
        url("https://thebusybiscuit.github.io/builds/SchnTgaiSpock/SlimeHUD/master/SlimeHUD-11.jar")
        url("https://thebusybiscuit.github.io/builds/Sefiraat/Networks/master/Networks-50.jar")
        url("https://thebusybiscuit.github.io/builds/Mooy1/InfinityExpansion/master/InfinityExpansion-144.jar")
        url("https://ci.ender.zone/job/EssentialsX/lastSuccessfulBuild/artifact/jars/EssentialsX-2.21.0-dev+25-fbfd7e9.jar")
    }
    maxHeapSize = "2G"
    minecraftVersion("1.20.1")
}