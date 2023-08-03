package com.bnorm.power.runners

import org.jetbrains.kotlin.cli.jvm.config.addJvmClasspathRoot
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.test.builders.TestConfigurationBuilder
import org.jetbrains.kotlin.test.directives.model.DirectivesContainer
import org.jetbrains.kotlin.test.directives.model.SimpleDirectivesContainer
import org.jetbrains.kotlin.test.model.TestModule
import org.jetbrains.kotlin.test.services.EnvironmentBasedStandardLibrariesPathProvider
import org.jetbrains.kotlin.test.services.EnvironmentConfigurator
import org.jetbrains.kotlin.test.services.RuntimeClasspathProvider
import org.jetbrains.kotlin.test.services.TestServices
import java.io.File

fun TestConfigurationBuilder.enableJunit() {
  useConfigurators(::JunitEnvironmentConfigurator)
  useCustomRuntimeClasspathProviders(::JunitRuntimeClassPathProvider)
}

object JunitDirectives : SimpleDirectivesContainer() {
  val WITH_JUNIT5 by directive("Add JUnit5 to classpath")
}

class JunitEnvironmentConfigurator(testServices: TestServices) : EnvironmentConfigurator(testServices) {
  override val directiveContainers: List<DirectivesContainer>
    get() = listOf(JunitDirectives)

  override fun configureCompilerConfiguration(configuration: CompilerConfiguration, module: TestModule) {
    if (JunitDirectives.WITH_JUNIT5 in module.directives) {
      configuration.addJvmClasspathRoot(EnvironmentBasedStandardLibrariesPathProvider.getFile("org.opentest4j.opentest4j"))
      configuration.addJvmClasspathRoot(EnvironmentBasedStandardLibrariesPathProvider.getFile("org.junit.jupiter.junit-jupiter-api"))
      configuration.addJvmClasspathRoot(EnvironmentBasedStandardLibrariesPathProvider.getFile("org.junit.platform.junit-platform-commons"))
    }
  }
}

class JunitRuntimeClassPathProvider(testServices: TestServices) : RuntimeClasspathProvider(testServices) {
  override fun runtimeClassPaths(module: TestModule): List<File> {
    return if (JunitDirectives.WITH_JUNIT5 in module.directives) {
      listOf(
        EnvironmentBasedStandardLibrariesPathProvider.getFile("org.opentest4j.opentest4j"),
        EnvironmentBasedStandardLibrariesPathProvider.getFile("org.junit.jupiter.junit-jupiter-api"),
        EnvironmentBasedStandardLibrariesPathProvider.getFile("org.junit.platform.junit-platform-commons"),
      )
    } else {
      emptyList()
    }
  }
}
