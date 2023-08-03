// !FUNCTION: org.junit.jupiter.api.Assertions.assertTrue
// !WITH_JUNIT5

import org.junit.jupiter.api.Assertions.assertTrue

fun box() = verifyMessage(
  """
  Message:
  assertTrue(1 != 1, "Message:")
               |
               false ==> expected: <true> but was: <false>
  """.trimIndent()
) {
  assertTrue(1 != 1, "Message:")
}
