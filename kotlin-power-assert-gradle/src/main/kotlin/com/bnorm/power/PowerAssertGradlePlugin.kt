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

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

class PowerAssertGradlePlugin : Plugin<Project> {
  companion object {
    private val DEPRECATION_EXCEPTION_MESSAGE =
      "Starting with Kotlin 2.0.0, kotlin-power-assert is no longer supported. " +
        "Please switch to the official Kotlin power-assert compiler plugin: https://kotl.in/power-assert" +
        "\n\n" +
        """
          Replace the kotlin-power-assert Gradle plugin with one of the following:
          
          plugins {
              kotlin("plugin.power-assert") version "<kotlin-version>" // Kts format
              id 'org.jetbrains.kotlin.plugin.power-assert' version '<kotlin-version>' // Groovy format
          }
        """.trimIndent()
  }

  override fun apply(target: Project) {
    throw GradleException(DEPRECATION_EXCEPTION_MESSAGE)
  }
}
