// !FUNCTION: infix.extension.mustEqual

import infix.extension.*

fun box() = verifyMessage(
  """
  (1 + 1) mustEqual (2 + 4)
     |                 |
     |                 6
     2
  """.trimIndent()
) {
  (1 + 1) mustEqual (2 + 4)
}
