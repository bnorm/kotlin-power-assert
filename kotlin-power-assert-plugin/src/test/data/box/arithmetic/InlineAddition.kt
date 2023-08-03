fun box() = verifyMessage(
  """
  Assertion failed
  assert(1 + 1 == 4)
           |   |
           |   false
           2
  """.trimIndent()
) {
  assert(1 + 1 == 4)
}
