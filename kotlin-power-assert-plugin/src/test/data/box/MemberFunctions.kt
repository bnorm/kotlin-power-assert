fun box() = verifyMessage(
  """
  Assertion failed
  assert(hello.length == "World".substring(1, 4).length)
         |     |      |          |               |
         |     |      |          |               3
         |     |      |          orl
         |     |      false
         |     5
         Hello
  """.trimIndent()
) {
  val hello = "Hello"
  assert(hello.length == "World".substring(1, 4).length)
}
