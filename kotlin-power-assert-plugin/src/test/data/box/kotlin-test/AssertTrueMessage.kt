// !FUNCTION: kotlin.test.assertTrue

import kotlin.test.assertTrue

fun box() = verifyMessage(
  """
  Message:
  assertTrue(1 != 1, "Message:")
               |
               false
  """.trimIndent()
) {
  assertTrue(1 != 1, "Message:")
}
