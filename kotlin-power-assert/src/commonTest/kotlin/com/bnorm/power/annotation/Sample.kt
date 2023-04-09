package com.bnorm.power.annotation

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import kotlin.jvm.JvmSynthetic

@OptIn(ExperimentalContracts::class)
@Introspected
fun assertTrue(condition: Boolean, message: String? = null) {
  contract {
    returns() implies condition
  }

  if (!condition) {
    val diagram = CallOrigin.get()?.toSimpleDiagram()
    val errorMessage = when {
      message == null -> diagram
      diagram != null -> "$message:\n$diagram"
      else -> null
    }
    throw AssertionError(errorMessage)
  }
}

// Compiler plugin should generate the following function:

@JvmSynthetic
internal fun assertTrue__introspected(condition: Boolean, message: String? = null, callOrigin: CallOrigin) {
  if (!condition) {
    val diagram = callOrigin?.toSimpleDiagram() // CallOrigin.get() replaced with `callOrigin` parameter
    val errorMessage = when {
      message == null -> diagram
      diagram != null -> "$message:\n$diagram"
      else -> null
    }
    throw AssertionError(errorMessage)
  }
}
