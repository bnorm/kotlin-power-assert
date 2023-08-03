// !FUNCTION: infix.dispatch.Wrapper.mustEqual

import infix.dispatch.*

fun box() = verifyMessage(
  """
  Wrapper(1) mustEqual (2 + 4)
  |                       |
  |                       6
  Wrapper
  """.trimIndent()
) {
  Wrapper(1) mustEqual (2 + 4)
}
