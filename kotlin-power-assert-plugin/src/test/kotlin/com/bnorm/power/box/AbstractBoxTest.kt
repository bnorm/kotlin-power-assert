package com.bnorm.power.box

import com.bnorm.power.runners.AdditionalSourceFilesProvider
import com.bnorm.power.runners.PowerAssertConfigurationDirectives
import com.bnorm.power.runners.PowerAssertExtensionRegistrarConfigurator
import com.bnorm.power.runners.enableJunit
import org.jetbrains.kotlin.test.builders.TestConfigurationBuilder
import org.jetbrains.kotlin.test.directives.CodegenTestDirectives.IGNORE_DEXING
import org.jetbrains.kotlin.test.directives.ConfigurationDirectives.WITH_STDLIB
import org.jetbrains.kotlin.test.directives.JvmEnvironmentConfigurationDirectives.FULL_JDK
import org.jetbrains.kotlin.test.runners.codegen.AbstractIrBlackBoxCodegenTest
import org.jetbrains.kotlin.test.services.EnvironmentBasedStandardLibrariesPathProvider
import org.jetbrains.kotlin.test.services.KotlinStandardLibrariesPathProvider
import java.io.File

open class AbstractBoxTest : AbstractIrBlackBoxCodegenTest() {
  override fun createKotlinStandardLibrariesPathProvider(): KotlinStandardLibrariesPathProvider {
    return EnvironmentBasedStandardLibrariesPathProvider
  }

  override fun configure(builder: TestConfigurationBuilder) {
    super.configure(builder)

    // TODO there has got to be a better way?
    val sourceRoots = File("src/test/data/")
      .walkTopDown()
      .filter { it.isDirectory }
      .joinToString(",") { it.path }
    System.setProperty("KOTLIN_POWER_ASSERT_ADD_SRC_ROOTS", sourceRoots)

    with(builder) {
      defaultDirectives {
        +FULL_JDK
        +WITH_STDLIB
        +IGNORE_DEXING
      }
      useDirectives(PowerAssertConfigurationDirectives)

      useConfigurators(
        ::PowerAssertExtensionRegistrarConfigurator,
      )

      useAdditionalSourceProviders(
        ::AdditionalSourceFilesProvider,
      )

      enableJunit()
    }
  }
}
