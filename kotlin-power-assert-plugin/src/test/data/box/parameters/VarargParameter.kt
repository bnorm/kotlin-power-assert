fun box() = verifyMessage(
  """
  Assertion failed
  assert(listOf("a", "b", "c") == listOf(i++, i++, i++))
         |                     |  |      |    |    |
         |                     |  |      |    |    2
         |                     |  |      |    1
         |                     |  |      0
         |                     |  [0, 1, 2]
         |                     false
         [a, b, c]
  """.trimIndent()
) {
  var i = 0
  assert(listOf("a", "b", "c") == listOf(i++, i++, i++))
}
