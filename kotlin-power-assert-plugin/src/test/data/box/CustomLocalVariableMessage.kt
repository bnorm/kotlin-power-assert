fun box() = verifyMessage(
  """
  Not equal
  assert(1 == 2, lambda)
           |
           false
  """.trimIndent()
) {
  val lambda = { "Not equal" }
  assert(1 == 2, lambda)
}
