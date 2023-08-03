package com.bnorm.power.runners

import com.bnorm.power.box.AbstractBoxTest
import org.jetbrains.kotlin.generators.generateTestGroupSuiteWithJUnit5

fun main() {
  generateTestGroupSuiteWithJUnit5 {
    testGroup(testDataRoot = "src/test/data", testsRoot = "src/test/java") {
      testClass<AbstractBoxTest> {
        model("box")
      }
    }
  }
}
