package com.bnorm.power.runners

import org.jetbrains.kotlin.test.directives.model.SimpleDirectivesContainer

object PowerAssertConfigurationDirectives : SimpleDirectivesContainer() {
  val FUNCTION by stringDirective(
    description = "Functions targeted by power-assert transformation",
    multiLine = true,
  )
}
