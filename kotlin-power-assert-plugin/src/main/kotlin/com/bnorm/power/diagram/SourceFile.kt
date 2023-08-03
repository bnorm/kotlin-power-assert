package com.bnorm.power.diagram

import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.SourceRangeInfo
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.path
import org.jetbrains.kotlin.ir.expressions.IrCall
import java.io.File

data class SourceFile(
  private val irFile: IrFile,
) {
  companion object {
    private val KOTLIN_POWER_ASSERT_ADD_SRC_ROOTS = System.getProperty("KOTLIN_POWER_ASSERT_ADD_SRC_ROOTS") ?: ""
    private val sourceRoots = listOf(".") + KOTLIN_POWER_ASSERT_ADD_SRC_ROOTS.split(",").map { it.trim() }
  }

  private val source: String = run {
    File(irFile.path).also { file ->
      if (file.exists()) {
        return@run file.readText()
      }
    }

    for (root in sourceRoots) {
      val file = File(root, irFile.path)
      if (file.exists()) {
        return@run file.readText()
      }
    }
    throw IllegalArgumentException("Unable to find source for IrFile: ${irFile.path}")
  }
    .replace("\r\n", "\n") // https://youtrack.jetbrains.com/issue/KT-41888

  fun getSourceRangeInfo(element: IrElement): SourceRangeInfo {
    var range = element.startOffset..element.endOffset
    when (element) {
      is IrCall -> {
        val receiver = element.extensionReceiver ?: element.dispatchReceiver
        if (element.symbol.owner.isInfix && receiver != null) {
          // When an infix function is called *not* with infix notation, the startOffset will not include the receiver.
          // Force the range to include the receiver, so it is always present
          range = receiver.startOffset..element.endOffset

          // The offsets of the receiver will *not* include surrounding parentheses so these need to be checked for
          // manually.
          val substring = safeSubstring(receiver.startOffset - 1, receiver.endOffset + 1)
          if (substring.startsWith('(') && substring.endsWith(')')) {
            range = receiver.startOffset - 1..element.endOffset
          }
        }
      }
    }
    return irFile.fileEntry.getSourceRangeInfo(range.first, range.last)
  }

  fun getText(info: SourceRangeInfo): String {
    return safeSubstring(info.startOffset, info.endOffset)
  }

  private fun safeSubstring(start: Int, end: Int): String =
    source.substring(maxOf(start, 0), minOf(end, source.length))

  fun getCompilerMessageLocation(element: IrElement): CompilerMessageLocation {
    val info = getSourceRangeInfo(element)
    val lineContent = getText(info)
    return CompilerMessageLocation.create(irFile.path, info.startLineNumber, info.startColumnNumber, lineContent)!!
  }
}
