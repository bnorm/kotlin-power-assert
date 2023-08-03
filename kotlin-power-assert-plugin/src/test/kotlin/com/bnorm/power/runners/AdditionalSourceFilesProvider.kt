package com.bnorm.power.runners

import org.jetbrains.kotlin.test.directives.AdditionalFilesDirectives
import org.jetbrains.kotlin.test.directives.model.DirectivesContainer
import org.jetbrains.kotlin.test.directives.model.RegisteredDirectives
import org.jetbrains.kotlin.test.model.TestFile
import org.jetbrains.kotlin.test.model.TestModule
import org.jetbrains.kotlin.test.services.AdditionalSourceProvider
import org.jetbrains.kotlin.test.services.TestServices
import java.io.File

class AdditionalSourceFilesProvider(testServices: TestServices) : AdditionalSourceProvider(testServices) {
  override val directiveContainers: List<DirectivesContainer> =
    listOf(AdditionalFilesDirectives)

  @OptIn(ExperimentalStdlibApi::class)
  override fun produceAdditionalFiles(globalDirectives: RegisteredDirectives, module: TestModule): List<TestFile> {
    return buildList {
      add(File("src/test/data/helpers/InfixDispatch.kt").toTestFile())
      add(File("src/test/data/helpers/InfixExtension.kt").toTestFile())
      add(File("src/test/data/helpers/utils.kt").toTestFile())
    }
  }
}
