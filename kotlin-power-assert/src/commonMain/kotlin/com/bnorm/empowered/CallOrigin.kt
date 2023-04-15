package com.bnorm.empowered

import kotlin.jvm.JvmSynthetic

class CallOrigin(
  val source: String,
  val dispatchReceiver: Node? = null,
  val extensionReceiver: Node? = null,
  val parameters: List<Node> = emptyList(),
) {
  companion object {
    /**
     * Always returns `null`. Functions annotated with [Empowered] will have calls to this function replaced by a
     * parameter within a generated function, while the original function will have calls replaced with a constant
     * `null`.
     *
     * Calls to [Empowered] functions will then be replaced with the generated function by the `kotlin-power-assert`
     * compiler plugin. If the plugin is not added, these function calls will not be replaced, so the original function
     * ***must*** be able handle a `null` value.
     */
    fun get(): CallOrigin? = null

    /**
     * Used to get a [CallOrigin.Node] for the specified expression. This allows not only to get the
     * [result of the expression][CallOrigin.Node.result], but also the [source code][CallOrigin.Node.source] and tree
     * of the expression.
     *
     * This function will ***always*** fail if the `kotlin-power-assert` compiler plugin is not applied to the project.
     */
    @Empowered
    fun <T> of(expression: T): Node =
      error("kotlin-power-assert plugin must be applied to project")

    /**
     * Manually generated empowered delegate for [of] function, since the compiler plugin does not run against its
     * own support library.
     */
    @Suppress("FunctionName")
    @JvmSynthetic
    internal fun <T> of__empowered(expression: T, callOrigin: CallOrigin): Node =
      callOrigin.parameters[0]
  }

  sealed class Node {
    abstract val source: String

    /** Offset within source where expression appears. */
    abstract val offset: Int

    /** The resulting value of the expression. */
    abstract val result: Any?

    class Value(
      override val source: String,
      override val offset: Int,
      override val result: Any?,
      val const: Boolean = false,
    ) : Node()

    class Branch(
      override val source: String,
      override val offset: Int,
      override val result: Any?,
      val name: String,
      val children: List<Node>,
    ) : Node()
  }
}

fun CallOrigin.toSimpleDiagram(): String = buildString {
  val nodes = listOfNotNull(dispatchReceiver, extensionReceiver) + parameters
  val valuesByRow = nodes.gatherRows(source)
  for ((codeRowIndex, codeRowSource) in source.split('\n').withIndex()) {
    val intermediates = valuesByRow[codeRowIndex].orEmpty().sortedBy { it.indent }

    if (codeRowIndex != 0) appendLine()
    append(codeRowSource)

    if (intermediates.isNotEmpty()) {
      appendLine()
      run {
        var currentIndent = 0
        for (indent in intermediates.map { it.indent }) {
          repeat(indent - currentIndent) { append(' ') }
          append('|')
          currentIndent = indent + 1
        }
      }

      val remaining = intermediates.toMutableList()
      while (remaining.isNotEmpty()) {
        appendLine()

        // Figure out which displays will fit on the next row
        val displayRow = remaining.windowed(2, partialWindows = true)
          .filter { it.size == 1 || it[0].value.length < (it[1].indent - it[0].indent) }
          .map { it[0] }
          .toSet()

        var currentIndent = 0
        for (intermediate in remaining) {
          repeat(intermediate.indent - currentIndent) { append(' ') }
          val display = if (intermediate in displayRow) intermediate.value else "|"
          append(display)
          currentIndent = intermediate.indent + display.length
        }

        remaining -= displayRow.toSet()
      }
    }
  }
}

private data class Intermediate(val value: String, val indent: Int)

private fun List<CallOrigin.Node>.gatherRows(
  source: String,
  valuesByRow: MutableMap<Int, MutableList<Intermediate>> = mutableMapOf(),
): Map<Int, List<Intermediate>> {
  for (node in this) {
    if (node.offset >= 0) {
      val substring = source.substring(0, node.offset)
      val row = substring.count { it == '\n' }
      val indent = node.offset - (substring.lastIndexOf('\n') + 1)

      if (node !is CallOrigin.Node.Value || !node.const) {
        valuesByRow.getOrPut(row, ::ArrayList).add(Intermediate(getResultString(node), indent))
      }
    }

    if (node is CallOrigin.Node.Branch) {
      node.children.gatherRows(source, valuesByRow)
    }
  }

  return valuesByRow
}

private fun getResultString(node: CallOrigin.Node): String {
  // TODO what other expressions should be enhanced with different display values?

  if (node.result == false && node is CallOrigin.Node.Branch && node.name == "EQEQ" && node.children.size == 2) {
    val left = node.children[0].result
    val right = node.children[1].result

    if (left is String && right is String) {
      // TODO calc string diff
      return node.result.toString()
    }

    if (left is List<*> && right is List<*>) {
      // TODO calc list diff?
      return node.result.toString()
    }

    if (left is Set<*> && right is Set<*>) {
      // TODO which is expected and which is actual? ie, which is missing and which is extra?
      val missing = right - left
      val extra = left - right
      return "missing: $missing, extra: $extra"
    }
  }

  return node.result.toString()
}
