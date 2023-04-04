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

pluginBundle {
  website = "https://github.com/bnorm/kotlin-power-assert"
  vcsUrl = "https://github.com/bnorm/kotlin-power-assert.git"
  tags = listOf("kotlin", "power-assert")
}

gradlePlugin {
  plugins {
    create("kotlinPowerAssert") {
      id = "com.bnorm.power.kotlin-power-assert"
      displayName = "Kotlin Power Assertion Plugin"
      description = "Kotlin Compiler Plugin to add power to your assertions"
      implementationClass = "com.bnorm.power.PowerAssertGradlePlugin"
    }
  }
}

afterEvaluate {
  // com.gradle.plugin-publish uses afterEvaluate to create everything.
  // Note: misleading name, this is actually `PublishPlugin.createAndSetupJarSourcesTask()`.
  tasks.named("publishPluginJar") {
    // https://github.com/gmazzo/gradle-buildconfig-plugin/pull/38
    dependsOn(tasks.generateBuildConfig)
  }
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
}

tasks.register("publish") {
  dependsOn("publishPlugins")
}
