fun box() = verifyMessage(
  """
  Assertion failed
  assert(!listOf("Hello", "World").contains("Hello"))
         ||                        |
         ||                        true
         |[Hello, World]
         false
  """.trimIndent()
) {
  assert(!listOf("Hello", "World").contains("Hello"))
}
