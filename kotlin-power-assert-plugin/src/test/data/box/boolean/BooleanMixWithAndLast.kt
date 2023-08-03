fun box() = verifyMessage(
  """
  Assertion failed
  assert((text.length == 1 || text.toLowerCase() == text) && text.length == 1)
          |    |      |       |    |             |  |
          |    |      |       |    |             |  Hello
          |    |      |       |    |             false
          |    |      |       |    hello
          |    |      |       Hello
          |    |      false
          |    5
          Hello
    """.trimIndent()
) {
  val text = "Hello"
  assert((text.length == 1 || text.toLowerCase() == text) && text.length == 1)
}
