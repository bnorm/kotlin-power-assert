// !FUNCTION: kotlin.test.assertNotNull

import kotlin.test.assertNotNull

fun box() = verifyMessage(
  """
  Message:
  assertNotNull(name, "Message:")
                |
                null. Expected value to be not null.
  """.trimIndent()
) {
  val name: String? = null
  assertNotNull(name, "Message:")
}
