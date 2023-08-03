// !FUNCTION: kotlin.require

fun box() = verifyMessage(
  """
  Assertion failed
  require(
    value = list
            |
            [Jane, John]
      .map { "Doe, ${'$'}it" }
       |
       [Doe, Jane, Doe, John]
      .any { it == "Scott, Michael" }
       |
       false
  )
  """.trimIndent()
) {
  val list = listOf("Jane", "John")
  require(
    value = list
      .map { "Doe, $it" }
      .any { it == "Scott, Michael" }
  )
}
