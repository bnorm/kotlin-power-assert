fun box() = verifyMessage(
  """
  Assertion failed
  assert(3 - 1 == 4)
           |   |
           |   false
           2
  """.trimIndent()
) {
  assert(3 - 1 == 4)
}
