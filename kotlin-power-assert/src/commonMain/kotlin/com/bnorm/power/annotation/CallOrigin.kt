package com.bnorm.power.annotation

class CallOrigin(
  val source: String,
  val dispatchReceiver: Node? = null,
  val extensionReceiver: Node? = null,
  val parameters: List<Node> = emptyList(),
) {
  companion object {
    /**
     * Always returns `null`. Functions annotated with [`@Introspected`][Introspected] have this function replaced by a
     * parameter within a generated function, while the original function remains unchanged.
     *
     * Calls to [Introspected] functions will then be replaced with the generated function by the `kotlin-power-assert`
     * compiler plugin. If the plugin is not added, these function calls will not be replaced, so the original function
     * ***must*** be able handle a `null` value.
     */
    fun get(): CallOrigin? = null
  }

  sealed class Node {
    /** Offset within source where expression appears. */
    abstract val offset: Int

    /** The resulting value of the expression. */
    abstract val result: Any?

    class Value(
      override val offset: Int,
      override val result: Any?,
      val const: Boolean = false,
    ) : Node()

    class Branch(
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
  for ((row, rowSource) in source.split('\n').withIndex()) {
    val rowDisplays = valuesByRow[row].orEmpty().sortedBy { it.indent }
    val indentations = rowDisplays.map { it.indent }

    if (row != 0) appendLine()

    append(rowSource)
    if (indentations.isNotEmpty()) {
      appendLine().indent(indentations, indentations.last() + 1)
    }
    for (display in rowDisplays.asReversed()) {
      appendLine().indent(indentations, display.indent)
      append(display.value)
    }
  }
}

private data class Display(val value: String, val indent: Int)

private fun List<CallOrigin.Node>.gatherRows(
  source: String,
  valuesByRow: MutableMap<Int, MutableList<Display>> = mutableMapOf(),
): Map<Int, List<Display>> {
  for (node in this) {
    if (node.offset >= 0) {
      val substring = source.substring(0, node.offset)
      val row = substring.count { it == '\n' }
      val indent = node.offset - (substring.lastIndexOf('\n') + 1)

      if (node !is CallOrigin.Node.Value || !node.const) {
        valuesByRow.getOrPut(row, ::ArrayList).add(Display(getResultString(node), indent))
      }
    }

    if (node is CallOrigin.Node.Branch) {
      node.children.gatherRows(source, valuesByRow)
    }
  }

  return valuesByRow
}

private fun getResultString(node: CallOrigin.Node): String {
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
      val missing = right - left
      val extra = left - right
      return "missing: $missing, extra: $extra"
    }
  }

  return node.result.toString()
}

private fun StringBuilder.indent(
  indentations: List<Int> = emptyList(),
  indent: Int
) {
  var last = -1
  for (i in indentations) {
    if (i == indent) break
    if (i > last) indent(i - last - 1).append('|')
    last = i
  }
  indent(indent - last - 1)
}

private fun StringBuilder.indent(indentation: Int): StringBuilder {
  repeat(indentation) { append(' ') }
  return this
}
