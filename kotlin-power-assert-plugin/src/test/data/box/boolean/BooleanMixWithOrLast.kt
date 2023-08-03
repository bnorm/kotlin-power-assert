fun box() = verifyMessage(
  """
  Assertion failed
  assert((text.length == 5 && text.toLowerCase() == text) || text.length == 1)
          |    |      |       |    |             |  |        |    |      |
          |    |      |       |    |             |  |        |    |      false
          |    |      |       |    |             |  |        |    5
          |    |      |       |    |             |  |        Hello
          |    |      |       |    |             |  Hello
          |    |      |       |    |             false
          |    |      |       |    hello
          |    |      |       Hello
          |    |      true
          |    5
          Hello
    """.trimIndent()
) {
  val text = "Hello"
  assert((text.length == 5 && text.toLowerCase() == text) || text.length == 1)
}
