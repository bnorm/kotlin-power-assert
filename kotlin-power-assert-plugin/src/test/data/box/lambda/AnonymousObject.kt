fun box() = verifyMessage(
  """
  Assertion failed
  assert(object { override fun toString() = "ANONYMOUS" }.toString() == "toString()")
         |                                                |          |
         |                                                |          false
         |                                                ANONYMOUS
         ANONYMOUS
  """.trimIndent()
) {
  assert(object { override fun toString() = "ANONYMOUS" }.toString() == "toString()")
}
