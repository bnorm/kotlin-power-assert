fun box() = verifyMessage(
  """
  Assertion failed
  assert(++i == 4)
         |   |
         |   false
         2
  """.trimIndent()
) {
  var i = 1
  assert(++i == 4)
}
