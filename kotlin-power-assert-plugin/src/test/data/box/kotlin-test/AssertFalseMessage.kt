// !FUNCTION: kotlin.test.assertFalse

import kotlin.test.assertFalse

fun box() = verifyMessage(
  """
  Message:
  assertFalse(1 == 1, "Message:")
                |
                true
  """.trimIndent()
) {
  assertFalse(1 == 1, "Message:")
}
