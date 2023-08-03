fun box() = verifyMessage(
  """
  Assertion failed
  assert(i-- == 4)
         |   |
         |   false
         3
  """.trimIndent()
) {
  var i = 3
  assert(i-- == 4)
}
