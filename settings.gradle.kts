rootProject.name = "ioc"

pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
        id("org.jetbrains.dokka") version kotlinVersion
    }
}
