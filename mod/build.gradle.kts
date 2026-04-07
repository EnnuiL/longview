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
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
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
			configName = "Fabric Client"
			ideConfigGenerated(true)
			runDir("run")
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
	file = tasks.named<Jar>("jar").get().archiveFile
	modLoaders = listOf("fabric", "neoforge")
	changelog = "To Be Updated"
	type = STABLE

	github {
		accessToken = providers.environmentVariable("GITHUB_TOKEN")
		repository = "EnnuiL/longview"
		commitish = "main"
	}

	modrinth {
		accessToken = providers.environmentVariable("MODRINTH_TOKEN")
		projectId = "4lDrPSXX"
	}

	curseforge {
		accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
		projectId = "1465234"
	}
}
