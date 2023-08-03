fun box() = verifyMessage(
  """
  Assertion failed
  assert(text != null && text.length == 1)
         |    |
         |    false
         null
    """.trimIndent()
) {
  val text: String? = null
  assert(text != null && text.length == 1)
}
