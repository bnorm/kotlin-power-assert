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

import org.gradle.api.Project
import org.gradle.api.logging.LogLevel
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

class PowerAssertGradlePlugin : KotlinCompilerPluginSupportPlugin {
  private var pluginVersion: String = BuildConfig.PLUGIN_VERSION

  override fun apply(target: Project): Unit = with(target) {
    extensions.create("kotlinPowerAssert", PowerAssertGradleExtension::class.java)

    // Try and determine the Kotlin version being used to provide a compatible version of kotlin-power-assert
    pluginVersion = runCatching {
      determineCompatiblePluginVersion(target)
    }.getOrDefault(BuildConfig.PLUGIN_VERSION)
  }

  override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean = true

  override fun getCompilerPluginId(): String = "com.bnorm.kotlin-power-assert"

  override fun getPluginArtifact(): SubpluginArtifact = SubpluginArtifact(
    groupId = BuildConfig.PLUGIN_GROUP_ID,
    artifactId = BuildConfig.PLUGIN_ARTIFACT_ID,
    version = pluginVersion
  )

  override fun getPluginArtifactForNative(): SubpluginArtifact = SubpluginArtifact(
    groupId = BuildConfig.PLUGIN_GROUP_ID,
    artifactId = BuildConfig.PLUGIN_ARTIFACT_ID + "-native",
    version = pluginVersion
  )

  override fun applyToCompilation(
    kotlinCompilation: KotlinCompilation<*>
  ): Provider<List<SubpluginOption>> {
    val project = kotlinCompilation.target.project
    val extension = project.extensions.getByType(PowerAssertGradleExtension::class.java)
    return project.provider {
      extension.functions.map {
        SubpluginOption(key = "function", value = it)
      }
    }
  }

  private fun determineCompatiblePluginVersion(project: Project): String {
    val version = project.getKotlinPluginVersion()
    val (major, minor, patch) = "(\\d+)\\.(\\d+)\\.(\\d+).*".toRegex().matchEntire(version)?.destructured
      ?: throw IllegalArgumentException("Cannot parse Kotlin compiler version: $version")
    val kotlinCompilerVersion = KotlinVersion(major.toInt(), minor.toInt(), patch.toInt())
    val pluginVersion = when {
      /* 0.0.0 <= */ kotlinCompilerVersion < KotlinVersion(1, 3, 60) ->
        throw IllegalArgumentException("Unsupported Kotlin version $kotlinCompilerVersion")
      /* 1.3.60 <= */ kotlinCompilerVersion < KotlinVersion(1, 3, 70) -> "0.2.0"
      /* 1.3.70 <= */ kotlinCompilerVersion < KotlinVersion(1, 4, 0) -> "0.3.1"
      /* 1.4.0 <= */ kotlinCompilerVersion < KotlinVersion(1, 4, 20) -> "0.5.3"
      /* 1.4.20 <= */ kotlinCompilerVersion < KotlinVersion(1, 4, 30) -> "0.6.1"
      /* 1.4.30 <= */ kotlinCompilerVersion < KotlinVersion(1, 5, 0) -> "0.7.0"
      /* 1.5.0 <= */ kotlinCompilerVersion < KotlinVersion(1, 5, 10) -> "0.8.1"
      /* 1.5.10 <= */ kotlinCompilerVersion < KotlinVersion(1, 5, 20) -> "0.9.0"
      /* 1.5.20 <= */ kotlinCompilerVersion < KotlinVersion(1, 6, 0) -> "0.10.0"
      /* 1.6.0 <= */ else -> BuildConfig.PLUGIN_VERSION
    }

    if (pluginVersion != BuildConfig.PLUGIN_VERSION) {
      project.logger.log(
        LogLevel.WARN,
        "Downgrading compiler plugin artifact version to $pluginVersion to be compatible with Kotlin version $kotlinCompilerVersion"
      )
    }

    return pluginVersion
  }
}
