fun box() = verifyMessage(
  """
  Assertion failed
  assert(hello.reversed() == emptyList<String>())
         |     |          |  |
         |     |          |  []
         |     |          false
         |     [World, Hello]
         [Hello, World]
  """.trimIndent()
) {
  val hello = listOf("Hello", "World")
  assert(hello.reversed() == emptyList<String>())
}
