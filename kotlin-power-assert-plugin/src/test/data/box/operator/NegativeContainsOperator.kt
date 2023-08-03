fun box() = verifyMessage(
  """
  Assertion failed
  assert("Hello" !in listOf("Hello", "World"))
                 |   |
                 |   [Hello, World]
                 false
  """.trimIndent()
) {
  assert("Hello" !in listOf("Hello", "World"))
}
