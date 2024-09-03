plugins {
    java
    application
    id("com.gradleup.shadow") version "8.3.0"
    id("pmd")
}

group = "com.sanyavertolet.interview"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.4")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.4")

    implementation("ch.qos.logback:logback-classic:1.5.7")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("com.sanyavertolet.interview.Application")
}

tasks.shadowJar {
    archiveBaseName.set("spreadsheet")
    archiveClassifier.set("")
    archiveVersion.set("")
}
