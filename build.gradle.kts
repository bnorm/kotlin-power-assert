import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
  id("com.github.ben-manes.versions") version "0.46.0"
  kotlin("jvm") version "1.8.20-RC" apply false
  id("org.jetbrains.dokka") version "1.8.10" apply false
  id("com.gradle.plugin-publish") version "0.11.0" apply false
  id("com.github.gmazzo.buildconfig") version "2.0.2" apply false
}

allprojects {
  group = "com.bnorm.power"
  version = "0.13.0-SNAPSHOT"
}

subprojects {
  repositories {
    mavenCentral()
  }
}

fun isNonStable(version: String): Boolean {
  val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
  val regex = "^[0-9,.v-]+(-r)?$".toRegex()
  val isStable = stableKeyword || regex.matches(version)
  return isStable.not()
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates") {
  rejectVersionIf {
    isNonStable(candidate.version) && !isNonStable(currentVersion)
  }
  // optional parameters
  gradleReleaseChannel = "current"
  checkForGradleUpdate = true
  outputFormatter = "json"
  outputDir = "build/dependencyUpdates"
  reportfileName = "report"
}

