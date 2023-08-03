// !FUNCTION: kotlin.test.assertEquals

import kotlin.test.assertEquals

fun box() = verifyMessage(
  """
  assertEquals(greeting, name)
               |         |
               |         World
               Hello. Expected <Hello>, actual <World>.
  """.trimIndent()
) {
  val greeting = "Hello"
  val name = "World"
  assertEquals(greeting, name)
}
