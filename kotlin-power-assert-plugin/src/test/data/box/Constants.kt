// !FUNCTION: kotlin.require

fun box() = verifyMessage(
  """
  Assertion failed
  """.trimIndent()
) {
  assert(false)
}
