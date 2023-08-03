// !FUNCTION: kotlin.check

fun box() = verifyMessage(
  """
  the world is broken
  check(1 == 2) { "the world is broken" }
          |
          false
  """.trimIndent()
) {
  check(1 == 2) { "the world is broken" }
}
