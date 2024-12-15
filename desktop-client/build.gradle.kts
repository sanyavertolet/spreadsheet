plugins {
    java
    application
    pmd
    id("com.gradleup.shadow") version "8.3.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.common)

    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.4")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.4")

    implementation("ch.qos.logback:logback-classic:1.5.7")

    compileOnly("org.jetbrains:annotations:26.0.1")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("io.github.sanyavertolet.interview.Application")
}

tasks.shadowJar {
    archiveClassifier.set("")
    dependsOn("distTar", "distZip")
}

pmd {
    toolVersion = "6.55.0"
    ruleSetFiles("pmd.xml")
}
