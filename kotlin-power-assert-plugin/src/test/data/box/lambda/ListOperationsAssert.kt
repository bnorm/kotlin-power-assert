fun box() = verifyMessage(
  """
  Assertion failed
  assert(list.map { "Doe, ${"$"}it" }.any { it == "Scott, Michael" })
         |    |                  |
         |    |                  false
         |    [Doe, Jane, Doe, John]
         [Jane, John]
  """.trimIndent()
) {
  val list = listOf("Jane", "John")
  assert(list.map { "Doe, $it" }.any { it == "Scott, Michael" })
}
