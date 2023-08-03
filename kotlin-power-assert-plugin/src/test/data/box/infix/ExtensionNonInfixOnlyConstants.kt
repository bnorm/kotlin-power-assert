// !FUNCTION: infix.extension.mustEqual

import infix.extension.*

fun box() = verifyMessage(
  """
  Assertion failed
  """.trimIndent()
) {
  2.mustEqual(6)
}
