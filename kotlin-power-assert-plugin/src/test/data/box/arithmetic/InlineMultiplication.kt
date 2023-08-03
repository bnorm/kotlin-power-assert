fun box() = verifyMessage(
  """
  Assertion failed
  assert(1 * 2 == 4)
           |   |
           |   false
           2
  """.trimIndent()
) {
  assert(1 * 2 == 4)
}
