import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    id("java")
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
}

group = "io.github.carlosdiamon.shadowcoord"
version = "1.0-SNAPSHOT"

paper {
    name = "ShadowCoord"
    version = "1.0"
    description = "A simple plugin to show the player's coordinates in the action bar"
    author = "CarlosDiamon"
    website = "https://github.com/carlosdiamon"
    apiVersion = "1.20"
    foliaSupported = false
		main = "io.github.carlosdiamon.shadowcoord.Core"
		loader = "io.github.carlosdiamon.shadowcoord.CoreLoader"
	serverDependencies {
		register("packetevents") {
			required = true
		}
		register("MiniPlaceholders") {
			required = false
			joinClasspath = true
			load = PaperPluginDescription.RelativeLoadOrder.BEFORE
		}
	}
}

repositories {
    mavenCentral()
    maven("https://repo.codemc.io/repository/maven-releases/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")
    compileOnly("com.github.retrooper.packetevents:spigot:2.3.0")
    compileOnly("org.spongepowered:configurate-hocon:4.1.2")
		compileOnly("org.jetbrains:annotations:24.1.0")

		compileOnly("io.github.miniplaceholders:miniplaceholders-api:2.2.3")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.test {
    useJUnitPlatform()
}