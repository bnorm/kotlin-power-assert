// !FUNCTION: dbg

fun box() = verifyMessage(
  """
  Message:
  dbg(1 + 2 + 3, "Message:")
        |   |
        |   6
        3
  """.trimIndent(),
) {
  dbg(1 + 2 + 3, "Message:")
}

fun <T> dbg(value: T): T = value
fun <T> dbg(value: T, msg: String): T = throw RuntimeException(msg)
