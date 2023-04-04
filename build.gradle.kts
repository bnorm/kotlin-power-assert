plugins {
  kotlin("jvm") version "1.7.0" apply false
  id("org.jetbrains.dokka") version "1.7.0" apply false
  id("com.gradle.plugin-publish") version "0.11.0" apply false
  id("com.github.gmazzo.buildconfig") version "3.1.0" apply false
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
