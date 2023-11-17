plugins {
    kotlin("jvm") version "1.9.20"
    application
}

group = "sap.assignment"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.vertx:vertx-core:4.4.6")
    implementation("io.vertx:vertx-web:4.4.6")
    implementation("io.vertx:vertx-lang-kotlin:4.4.6")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}