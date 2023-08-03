// !FUNCTION: kotlin.test.assertFalse

import kotlin.test.assertFalse

fun box() = verifyMessage(
  """
  Assertion failed
  assertFalse(1 == 1)
                |
                true
  """.trimIndent()
) {
  assertFalse(1 == 1)
}
