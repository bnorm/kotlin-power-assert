fun box() = verifyMessage(
  """
  Assertion failed
  assert(text == null || text.length == 1 || text.toLowerCase() == text)
         |    |          |    |      |       |    |             |  |
         |    |          |    |      |       |    |             |  Hello
         |    |          |    |      |       |    |             false
         |    |          |    |      |       |    hello
         |    |          |    |      |       Hello
         |    |          |    |      false
         |    |          |    5
         |    |          Hello
         |    false
         Hello
    """.trimIndent()
) {
  val text: String? = "Hello"
  assert(text == null || text.length == 1 || text.toLowerCase() == text)
}
