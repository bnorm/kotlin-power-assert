plugins {
  kotlin("multiplatform")
  id("org.jmailen.kotlinter")
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
    "Mac OS" in osName -> when {
      "aarch64" in osArch -> macosArm64("native")
      else -> macosX64("native")
    }

    else -> linuxX64("native")
  }

  sourceSets {
    val commonMain by getting {
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }
  }
}
