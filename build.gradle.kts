plugins {
    java
    pmd
    id("com.gradleup.shadow") version "8.3.0"
}

group = "io.github.sanyavertolet.interview"

pmd {
    toolVersion = "6.55.0"
    ruleSetFiles("pmd.xml")
}
