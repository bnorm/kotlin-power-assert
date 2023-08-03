fun box() = verifyMessage(
  """
  Assertion failed
  assert(text?.length?.minus(2) == 1)
         |     |       |        |
         |     |       |        false
         |     |       3
         |     5
         Hello
    """.trimIndent()
) {
  val text: String? = "Hello"
  assert(text?.length?.minus(2) == 1)
}
