rootProject.name = "ioc"

pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("multiplatform") version kotlinVersion
    }
}
