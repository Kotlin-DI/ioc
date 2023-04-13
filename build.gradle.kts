import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion: String by project
val commonVersion: String by project

plugins {
    kotlin("jvm")
    java
    `java-library`
    `maven-publish`
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1"
    id("org.jetbrains.dokka")
    id("me.qoomon.git-versioning") version "6.3.0"
}

group = "com.github.kotlinDI"
version = "0.0.0-SNAPSHOT"
gitVersioning.apply {
    refs {
        branch(".+") {
            version = "\${ref}-SNAPSHOT"
        }
        tag("v(?<version>.*)") {
            version = "\${ref.version}"
        }
    }

    // optional fallback configuration in case of no matching ref configuration
    rev {
        version = "\${commit}"
    }
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
    maven("https://jitpack.io")
    mavenLocal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("com.github.Kotlin-DI:common:$commonVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0-Beta")
    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.jetbrains.kotlinx", "kotlinx-coroutines-test", "1.6.4")
    testImplementation("org.junit.jupiter:junit-jupiter")
}

ktlint {
    outputToConsole.set(true)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-opt-in=kotlin.RequiresOptIn")
            jvmTarget = "17"
        }
        dependsOn("ktlintFormat")
    }
    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            from(components["java"])
        }
    }
}