plugins {
	`java-library`
	alias(libs.plugins.fabric.loom)
	alias(libs.plugins.mod.publish.plugin)
}

base {
	archivesName = "longview"
}

group = "page.langeweile"
version = libs.versions.longview.get()

repositories {
	mavenCentral()
}

dependencies {
	minecraft(libs.minecraft)
	runtimeOnly(libs.fabric.loader)

	compileOnly(rootProject.project("stubs"))
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}

	withSourcesJar()
}

// TODO - Abstract this into a separate project
loom {
	mods {
		register("longview") {
			sourceSet("main")
		}
	}

	runs {
		named("client") {
			client()
			displayName = "Fabric Client"
		}
	}
}

tasks.processResources {
	filteringCharset = "UTF-8"

	val version = project.version
	inputs.property("version", version)

	filesMatching(listOf("fabric.mod.json", "META-INF/neoforge.mods.toml")) {
		expand("version" to version)
	}
}

publishMods {
	displayName.set("Longview ${version.get()}")
	file = tasks.named<Jar>("jar").get().archiveFile
	modLoaders = listOf("fabric", "neoforge")
	changelog = "To Be Updated"
	type = STABLE

	modrinth {
		accessToken = providers.environmentVariable("MODRINTH_TOKEN")
		projectId = "4lDrPSXX"
		minecraftVersions.addAll(listOf("26.1", "26.1.1", "26.1.2"))
		environment = CLIENT_ONLY
	}

	curseforge {
		accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
		projectId = "1465234"
		minecraftVersions.addAll(listOf("26.1", "26.1.1", "26.1.2"))
		javaVersions.addAll(listOf(JavaVersion.VERSION_25))
		client = true
		changelogType = "markdown"
	}
}
