// !FUNCTION: infix.dispatch.Wrapper.mustEqual

import infix.dispatch.*

fun box() = verifyMessage(
  """
  Wrapper(2).mustEqual(6)
  |
  Wrapper
  """.trimIndent()
) {
  Wrapper(2).mustEqual(6)
}
