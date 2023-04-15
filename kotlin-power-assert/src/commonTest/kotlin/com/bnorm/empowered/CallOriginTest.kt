package com.bnorm.empowered

import kotlin.test.Test
import kotlin.test.assertEquals

class CallOriginTest {
  @Test
  fun test_simple_diagram() {
    val origin = CallOrigin(
      source = "assert(1 == 2)",
      parameters = listOf(
        CallOrigin.Node.Branch(
          source = "1 == 2",
          offset = 9,
          result = false,
          name = "EQEQ",
          children = listOf(
            CallOrigin.Node.Value("1", 7, 1, const = false),
            CallOrigin.Node.Value("2", 12, 2, const = false),
          ),
        ),
      ),
    )

    assertEquals(
      expected = """
        assert(1 == 2)
               | |  |
               1 |  2
                 false
      """.trimIndent(),
      actual = origin.toSimpleDiagram(),
    )
  }

  @Test
  fun test_simple_diagram_with_constants() {
    val origin = CallOrigin(
      source = "assert(1 == 2)",
      parameters = listOf(
        CallOrigin.Node.Branch(
          source = "1 == 2",
          offset = 9,
          result = false,
          name = "EQEQ",
          children = listOf(
            CallOrigin.Node.Value("1", 7, 1, const = true),
            CallOrigin.Node.Value("2", 12, 2, const = true),
          ),
        ),
      ),
    )

    assertEquals(
      expected = """
        assert(1 == 2)
                 |
                 false
      """.trimIndent(),
      actual = origin.toSimpleDiagram(),
    )
  }

  @Test
  fun test_simple_diagram_with_strings() {
    val origin = CallOrigin(
      source = "assert(\"Hello\" == \"World\")",
      parameters = listOf(
        CallOrigin.Node.Branch(
          source = "\"Hello\" == \"World\"",
          offset = 15,
          result = false,
          name = "EQEQ",
          children = listOf(
            CallOrigin.Node.Value("\"Hello\"", 7, "Hello", const = true),
            CallOrigin.Node.Value("\"World\"", 17, "World", const = true),
          ),
        ),
      ),
    )

    // TODO what should the diff look like?
    assertEquals(
      expected = """
        assert("Hello" == "World")
                       |
                       false
      """.trimIndent(),
      actual = origin.toSimpleDiagram(),
    )
  }

  @Test
  fun test_simple_diagram_with_lists() {
    val origin = CallOrigin(
      source = "assert(listOf(1, 2, 3) == listOf(1, 3, 4))",
      parameters = listOf(
        CallOrigin.Node.Branch(
          source = "listOf(1, 2, 3) == listOf(1, 3, 4)",
          offset = 23,
          result = false,
          name = "EQEQ",
          children = listOf(
            CallOrigin.Node.Value("listOf(1, 2, 3)", 7, listOf(1, 2, 3)),
            CallOrigin.Node.Value("listOf(1, 3, 4)", 26, listOf(1, 3, 4)),
          ),
        ),
      ),
    )

    // TODO what should the diff look like?
    assertEquals(
      expected = """
        assert(listOf(1, 2, 3) == listOf(1, 3, 4))
               |               |  |
               [1, 2, 3]       |  [1, 3, 4]
                               false
      """.trimIndent(),
      actual = origin.toSimpleDiagram(),
    )
  }

  @Test
  fun test_simple_diagram_with_sets() {
    val origin = CallOrigin(
      source = "assert(setOf(1, 2, 3) == setOf(1, 3, 4))",
      parameters = listOf(
        CallOrigin.Node.Branch(
          source = "setOf(1, 2, 3) == setOf(1, 3, 4)",
          offset = 22,
          result = false,
          name = "EQEQ",
          children = listOf(
            CallOrigin.Node.Value("setOf(1, 2, 3)", 7, setOf(1, 2, 3)),
            CallOrigin.Node.Value("setOf(1, 3, 4)", 25, setOf(1, 3, 4)),
          ),
        ),
      ),
    )

    assertEquals(
      expected = """
        assert(setOf(1, 2, 3) == setOf(1, 3, 4))
               |              |  |
               [1, 2, 3]      |  [1, 3, 4]
                              missing: [4], extra: [2]
      """.trimIndent(),
      actual = origin.toSimpleDiagram(),
    )
  }

  @Test
  fun test_diagram_compaction() {
    val origin = CallOrigin(
      source = "assert(name.length == otherName.length)",
      parameters = listOf(
        CallOrigin.Node.Branch(
          source = "name.length == otherName.length",
          offset = 19,
          result = false,
          name = "EQEQ",
          children = listOf(
            CallOrigin.Node.Branch(
              source = "name.length",
              offset = 12,
              result = 5,
              name = "length",
              children = listOf(
                CallOrigin.Node.Value(
                  source = "name",
                  offset = 7,
                  result = "Smith",
                ),
              ),
            ),
            CallOrigin.Node.Branch(
              source = "otherName.length",
              offset = 32,
              result = 3,
              name = "length",
              children = listOf(
                CallOrigin.Node.Value(
                  source = "otherName",
                  offset = 22,
                  result = "Doe",
                ),
              ),
            ),
          ),
        ),
      ),
    )

    assertEquals(
      expected = """
        assert(name.length == otherName.length)
               |    |      |  |         |
               |    5      |  Doe       3
               Smith       false
      """.trimIndent(),
      actual = origin.toSimpleDiagram(),
    )
  }
}
