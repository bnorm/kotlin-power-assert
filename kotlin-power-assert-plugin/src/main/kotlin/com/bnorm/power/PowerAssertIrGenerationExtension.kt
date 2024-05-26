/*
 * Copyright (C) 2020 Brian Norman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bnorm.power

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.name.FqName

class PowerAssertIrGenerationExtension(
  private val messageCollector: MessageCollector,
  private val functions: Set<FqName>,
) : IrGenerationExtension {
  companion object {
    private val DEPRECATION_EXCEPTION_MESSAGE =
      "Starting with Kotlin 2.0.0, kotlin-power-assert is no longer supported. " +
        "Please switch to the official Kotlin power-assert compiler plugin: https://kotl.in/power-assert" +
        "\n\n" +
        """
          Replace the kotlin-power-assert compiler plugin artifact with one of the following:
           - 'org.jetbrains.kotlin:kotlin-power-assert-compiler-plugin:<kotlin-version>'
           - 'org.jetbrains.kotlin:kotlin-power-assert-compiler-plugin-embeddable:<kotlin-version>'
        """.trimIndent()
  }

  override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
    messageCollector.report(CompilerMessageSeverity.ERROR, DEPRECATION_EXCEPTION_MESSAGE)
  }
}
