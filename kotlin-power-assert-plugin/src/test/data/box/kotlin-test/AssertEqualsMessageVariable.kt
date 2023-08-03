// !FUNCTION: kotlin.test.assertEquals

import kotlin.test.assertEquals

fun box() = verifyMessage(
  """
  Message:
  assertEquals(greeting, name, message)
               |         |
               |         World
               Hello. Expected <Hello>, actual <World>.
  """.trimIndent()
) {
  val greeting = "Hello"
  val name = "World"
  val message = "Message:"
  assertEquals(greeting, name, message)
}
