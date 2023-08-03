// !FUNCTION: dbg

fun box() = verifyMessage(
  """
  false=true
  Message:
  dbg(
    key = greeting != null && greeting.length == 5,
          |        |
          |        false
          null
    value = name == null || name.length == 5,
            |    |
            |    true
            null
    msg = "Message:"
  )
  """.trimIndent(),
) {
  val greeting: String? = null
  val name: String? = null
  dbg(
    key = greeting != null && greeting.length == 5,
    value = name == null || name.length == 5,
    msg = "Message:"
  )
}

fun <T> dbg(key: Any, value: T): T = value
fun <T> dbg(key: Any, value: T, msg: String): T = throw RuntimeException(key.toString() + "=" + value + "\n" + msg)
