// !FUNCTION: kotlin.test.assertTrue

import kotlin.test.assertTrue

fun box() = verifyMessage(
  """
  Assertion failed
  assertTrue(1 != 1)
               |
               false
  """.trimIndent()
) {
  assertTrue(1 != 1)
}
