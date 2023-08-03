fun box() = verifyMessage(
  """
  Not equal
  assert(1 == 2) { "Not equal" }
           |
           false
  """.trimIndent()
) {
  assert(1 == 2) { "Not equal" }
}
