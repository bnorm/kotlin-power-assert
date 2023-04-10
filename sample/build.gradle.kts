plugins {
  kotlin("multiplatform") version "1.7.0"
  id("com.bnorm.power.kotlin-power-assert") version "0.12.2"
}

repositories {
  mavenCentral()
}

kotlin {
  jvm()
  js(IR) {
    browser()
    nodejs()
  }

  val osName = System.getProperty("os.name")
  val osArch = System.getProperty("os.arch")
  when {
    "Windows" in osName -> mingwX64("native")
    "Mac OS" in osName -> when (osArch) {
      "aarch64" -> macosArm64("native")
      else -> macosX64("native")
    }
    else -> linuxX64("native")
  }

  sourceSets {
    val commonMain by getting {
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
      }
    }
    val jvmTest by getting {
      dependencies {
        implementation(kotlin("test-junit5"))
      }
    }
    val jsTest by getting {
      dependencies {
        implementation(kotlin("test-js"))
      }
    }
    val nativeMain by getting {
      dependsOn(commonMain)
    }
    val nativeTest by getting {
      dependsOn(commonTest)
    }
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

configure<com.bnorm.power.PowerAssertGradleExtension> {
  functions = listOf(
    "kotlin.assert",
    "kotlin.test.assertTrue",
    "kotlin.require",
    "com.bnorm.power.AssertScope.assert",
    "com.bnorm.power.assert",
    "com.bnorm.power.dbg"
  )
  excludedSourceSets = listOf(
    "commonMain",
    "jvmMain",
    "jsMain",
    "nativeMain"
  )
}
