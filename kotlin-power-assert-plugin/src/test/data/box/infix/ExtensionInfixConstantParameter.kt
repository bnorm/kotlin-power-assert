// !FUNCTION: infix.extension.mustEqual

import infix.extension.*

fun box() = verifyMessage(
  """
  (1 + 1) mustEqual 6
     |
     2
  """.trimIndent()
) {
  (1 + 1) mustEqual 6
}
