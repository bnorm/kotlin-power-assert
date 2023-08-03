fun box() = verifyMessage(
  """
  Assertion failed
  assert("Hello, world!" !is String)
                         |
                         false
  """.trimIndent()
) {
  assert("Hello, world!" !is String)
}
