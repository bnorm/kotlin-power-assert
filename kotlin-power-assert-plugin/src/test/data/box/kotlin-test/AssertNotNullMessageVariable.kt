// !FUNCTION: kotlin.test.assertNotNull

import kotlin.test.assertNotNull

fun box() = verifyMessage(
  """
  Message:
  assertNotNull(name, message)
                |
                null. Expected value to be not null.
  """.trimIndent()
) {
  val name: String? = null
  val message = "Message:"
  assertNotNull(name, message)
}
