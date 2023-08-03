fun box() = verifyMessage(
  """
  Assertion failed
  assert(listOf("Hello", "World").contains("Name"))
         |                        |
         |                        false
         [Hello, World]
  """.trimIndent()
) {
  assert(listOf("Hello", "World").contains("Name"))
}
