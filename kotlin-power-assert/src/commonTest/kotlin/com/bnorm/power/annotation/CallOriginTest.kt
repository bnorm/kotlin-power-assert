package com.bnorm.power.annotation

import kotlin.test.Test
import kotlin.test.assertEquals

class CallOriginTest {
  @Test
  fun test_simple_diagram() {
    val source = "assert(1 == 2)"
    val parameter = CallOrigin.Node.Branch(
      offset = 9, result = false, name = "EQEQ",
      children = listOf(
        CallOrigin.Node.Value(7, 1, const = false),
        CallOrigin.Node.Value(12, 2, const = false)
      )
    )

    assertEquals(
      expected = """
        assert(1 == 2)
               | |  |
               | |  2
               | false
               1
        """.trimIndent(),
      actual = CallOrigin(source, parameters = listOf(parameter)).toSimpleDiagram()
    )
  }

  @Test
  fun test_simple_diagram_with_constants() {
    val source = "assert(1 == 2)"
    val parameter = CallOrigin.Node.Branch(
      offset = 9, result = false, name = "EQEQ",
      children = listOf(
        CallOrigin.Node.Value(7, 1, const = true),
        CallOrigin.Node.Value(12, 2, const = true)
      )
    )

    assertEquals(
      expected = """
        assert(1 == 2)
                 |
                 false
      """.trimIndent(),
      actual = CallOrigin(source, parameters = listOf(parameter)).toSimpleDiagram()
    )
  }

  @Test
  fun test_simple_diagram_with_strings() {
    val source = "assert(\"Hello\" == \"World\")"
    val parameter = CallOrigin.Node.Branch(
      offset = 15, result = false, name = "EQEQ",
      children = listOf(
        CallOrigin.Node.Value(7, "Hello", const = true),
        CallOrigin.Node.Value(17, "World", const = true)
      )
    )

    // TODO what should the diff look like?
    assertEquals(
      expected = """
        assert("Hello" == "World")
                       |
                       false
      """.trimIndent(),
      actual = CallOrigin(source, parameters = listOf(parameter)).toSimpleDiagram()
    )
  }

  @Test
  fun test_simple_diagram_with_lists() {
    val source = "assert(listOf(1, 2, 3) == listOf(1, 3, 4))"
    val parameter = CallOrigin.Node.Branch(
      offset = 23, result = false, name = "EQEQ",
      children = listOf(
        CallOrigin.Node.Value(7, listOf(1, 2, 3)),
        CallOrigin.Node.Value(26, listOf(1, 3, 4))
      )
    )

    // TODO what should the diff look like?
    assertEquals(
      expected = """
        assert(listOf(1, 2, 3) == listOf(1, 3, 4))
               |               |  |
               |               |  [1, 3, 4]
               |               false
               [1, 2, 3]
      """.trimIndent(),
      actual = CallOrigin(source, parameters = listOf(parameter)).toSimpleDiagram()
    )
  }

  @Test
  fun test_simple_diagram_with_sets() {
    val source = "assert(setOf(1, 2, 3) == setOf(1, 3, 4))"
    val parameter = CallOrigin.Node.Branch(
      offset = 22, result = false, name = "EQEQ",
      children = listOf(
        CallOrigin.Node.Value(7, setOf(1, 2, 3)),
        CallOrigin.Node.Value(25, setOf(1, 3, 4))
      )
    )

    assertEquals(
      expected = """
        assert(setOf(1, 2, 3) == setOf(1, 3, 4))
               |              |  |
               |              |  [1, 3, 4]
               |              missing: [4], extra: [2]
               [1, 2, 3]
      """.trimIndent(),
      actual = CallOrigin(source, parameters = listOf(parameter)).toSimpleDiagram()
    )
  }
}
