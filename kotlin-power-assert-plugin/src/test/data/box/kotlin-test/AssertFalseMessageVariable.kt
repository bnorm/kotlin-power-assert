// !FUNCTION: kotlin.test.assertFalse

import kotlin.test.assertFalse

fun box() = verifyMessage(
  """
  Message:
  assertFalse(1 == 1, message)
                |
                true
  """.trimIndent()
) {
  val message = "Message:"
  assertFalse(1 == 1, message)
}
