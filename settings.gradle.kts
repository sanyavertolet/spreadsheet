import org.ajoberstar.reckon.core.Scope
import org.ajoberstar.reckon.gradle.ReckonExtension

rootProject.name = "spreadsheet"

plugins {
    id("org.ajoberstar.reckon.settings") version "0.18.2"
}

extensions.configure<ReckonExtension> {
    setDefaultInferredScope(Scope.MINOR.name)
    snapshots()
    setScopeCalc(calcScopeFromProp())
    setStageCalc(calcStageFromProp())
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include("desktop-client")
include("server")
include("common")
