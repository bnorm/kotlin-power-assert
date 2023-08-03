// !FUNCTION: kotlin.require

fun box() = verifyMessage(
  """
  the world is broken
  require(1 == 2) { "the world is broken" }
            |
            false
  """.trimIndent()
) {
  require(1 == 2) { "the world is broken" }
}
