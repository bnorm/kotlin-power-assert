fun box() = verifyMessage(
  """
  Assertion failed
  assert("Name" in listOf("Hello", "World"))
                |  |
                |  [Hello, World]
                false
  """.trimIndent()
) {
  assert("Name" in listOf("Hello", "World"))
}
