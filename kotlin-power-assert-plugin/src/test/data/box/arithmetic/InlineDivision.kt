fun box() = verifyMessage(
  """
  Assertion failed
  assert(2 / 1 == 4)
           |   |
           |   false
           2
  """.trimIndent()
) {
  assert(2 / 1 == 4)
}
