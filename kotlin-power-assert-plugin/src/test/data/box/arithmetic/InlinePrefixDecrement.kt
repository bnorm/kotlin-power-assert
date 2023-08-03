fun box() = verifyMessage(
  """
  Assertion failed
  assert(--i == 4)
         |   |
         |   false
         2
  """.trimIndent()
) {
  var i = 3
  assert(--i == 4)
}
