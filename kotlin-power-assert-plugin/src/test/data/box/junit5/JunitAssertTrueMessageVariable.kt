// !FUNCTION: org.junit.jupiter.api.Assertions.assertTrue
// !WITH_JUNIT5

import org.junit.jupiter.api.Assertions.assertTrue

fun box() = verifyMessage(
  """
  Message:
  assertTrue(1 != 1, message)
               |
               false ==> expected: <true> but was: <false>
  """.trimIndent()
) {
  val message = "Message:"
  assertTrue(1 != 1, message)
}
