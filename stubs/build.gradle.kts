plugins {
	`java-library`
}

group = "page.langeweile"
version = libs.versions.longview.get()

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}
