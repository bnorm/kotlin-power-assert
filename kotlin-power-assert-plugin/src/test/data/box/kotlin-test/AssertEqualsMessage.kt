// !FUNCTION: kotlin.test.assertEquals

import kotlin.test.assertEquals

fun box() = verifyMessage(
  """
  Message:
  assertEquals(greeting, name, "Message:")
               |         |
               |         World
               Hello. Expected <Hello>, actual <World>.
  """.trimIndent()
) {
  val greeting = "Hello"
  val name = "World"
  assertEquals(greeting, name, "Message:")
}
