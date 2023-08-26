import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("info.picocli:picocli:4.7.4")
}
tasks {
    named<ShadowJar>("shadowJar") {
        manifest {
            attributes["Main-Class"] = "com.encora.apprentice.cliaccounting.CLIAccounting" // Replace with your main class
        }
//
//        // Optionally include/exclude specific resources from the JAR
//        exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
    }
}


tasks.test {
    useJUnitPlatform()
}