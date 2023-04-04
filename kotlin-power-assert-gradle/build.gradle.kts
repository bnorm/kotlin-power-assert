import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("java-gradle-plugin")
  kotlin("jvm")
  id("com.gradle.plugin-publish")
  id("com.github.gmazzo.buildconfig")
}

dependencies {
  implementation(kotlin("stdlib"))
  implementation(kotlin("gradle-plugin-api"))
}

buildConfig {
  val project = project(":kotlin-power-assert-plugin")
  packageName(project.group.toString())
  buildConfigField("String", "PLUGIN_GROUP_ID", "\"${project.group}\"")
  buildConfigField("String", "PLUGIN_ARTIFACT_ID", "\"${project.name}\"")
  buildConfigField("String", "PLUGIN_VERSION", "\"${project.version}\"")
}

gradlePlugin {
  website.set("https://github.com/bnorm/kotlin-power-assert")
  vcsUrl.set("https://github.com/bnorm/kotlin-power-assert.git")
  plugins {
    create("kotlinPowerAssert") {
      id = "com.bnorm.power.kotlin-power-assert"
      displayName = "Kotlin Power Assertion Plugin"
      description = "Kotlin Compiler Plugin to add power to your assertions"
      implementationClass = "com.bnorm.power.PowerAssertGradlePlugin"
      tags.set(listOf("kotlin", "power-assert"))
    }
  }
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
}

tasks.named("publish") {
  dependsOn("publishPlugins")
}
