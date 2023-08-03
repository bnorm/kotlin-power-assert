fun box() = verifyMessage(
  """
  Assertion failed
  assert(null is String)
              |
              false
  """.trimIndent()
) {
  assert(null is String)
}
