plugins {
    java
    pmd
}

repositories {
    mavenCentral()
}

dependencies {

}

pmd {
    toolVersion = "6.55.0"
    ruleSetFiles("pmd.xml")
}
