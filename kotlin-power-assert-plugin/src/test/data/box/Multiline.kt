fun box() = verifyMessage(
  """
  Assertion failed
  assert(
    text
    |
    Hello
      == null ||
      |
      false
      (
        text.length == 5 &&
        |    |      |
        |    |      true
        |    5
        Hello
          text.toLowerCase() == text
          |    |             |  |
          |    |             |  Hello
          |    |             false
          |    hello
          Hello
        )
  )
  """.trimIndent()
) {
  val text: String? = "Hello"
  assert(
    text
      == null ||
      (
        text.length == 5 &&
          text.toLowerCase() == text
        )
  )
}
