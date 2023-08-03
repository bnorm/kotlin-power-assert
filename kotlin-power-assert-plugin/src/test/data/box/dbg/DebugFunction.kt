// !FUNCTION: dbg

fun box() = verifyMessage(
  """
  dbg(1 + 2 + 3)
        |   |
        |   6
        3
  """.trimIndent(),
) {
  dbg(1 + 2 + 3)
}

fun <T> dbg(value: T): T = value
fun <T> dbg(value: T, msg: String): T = throw RuntimeException(msg)
