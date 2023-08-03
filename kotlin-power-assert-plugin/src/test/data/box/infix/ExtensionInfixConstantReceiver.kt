// !FUNCTION: infix.extension.mustEqual

import infix.extension.*

fun box() = verifyMessage(
  """
  1 mustEqual (2 + 4)
                 |
                 6
  """.trimIndent()
) {
  1 mustEqual (2 + 4)
}
