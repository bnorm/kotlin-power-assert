// !FUNCTION: kotlin.test.assertTrue

import kotlin.test.assertTrue

fun box() = verifyMessage(
  """
  Message:
  assertTrue(1 != 1, message)
               |
               false
  """.trimIndent()
) {
  val message = "Message:"
  assertTrue(1 != 1, message)
}
