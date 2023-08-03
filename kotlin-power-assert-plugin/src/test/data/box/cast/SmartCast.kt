fun box() = verifyMessage(
  """
  Assertion failed
  assert(greeting is String && greeting.length == 2)
         |        |            |        |      |
         |        |            |        |      false
         |        |            |        5
         |        |            hello
         |        true
         hello
  """.trimIndent()
) {
  val greeting: Any = "hello"
  assert(greeting is String && greeting.length == 2)
}
