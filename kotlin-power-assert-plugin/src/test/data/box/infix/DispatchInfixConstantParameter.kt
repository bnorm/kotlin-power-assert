// !FUNCTION: infix.dispatch.Wrapper.mustEqual

import infix.dispatch.*

fun box() = verifyMessage(
  """
  Wrapper(1 + 1) mustEqual 6
  |         |
  |         2
  Wrapper
  """.trimIndent()
) {
  Wrapper(1 + 1) mustEqual 6
}
