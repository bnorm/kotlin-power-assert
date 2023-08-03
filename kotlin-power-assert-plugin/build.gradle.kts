import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  java
  kotlin("jvm")
  kotlin("kapt")
  id("org.jetbrains.dokka")

  signing
  `maven-publish`
  id("org.jmailen.kotlinter")
}

dependencies {
  compileOnly("org.jetbrains.kotlin:kotlin-compiler")

  kapt("com.google.auto.service:auto-service:1.0.1")
  compileOnly("com.google.auto.service:auto-service-annotations:1.0.1")

  testImplementation(kotlin("test-junit5"))
  testImplementation(enforcedPlatform("org.junit:junit-bom:5.9.1"))

  testImplementation("org.jetbrains.kotlin:kotlin-compiler")
  testImplementation("org.jetbrains.kotlin:kotlin-reflect")
  testImplementation("org.jetbrains.kotlin:kotlin-compiler-internal-test-framework")
  testImplementation("junit:junit:4.13.2")

  testRuntimeOnly("org.jetbrains.kotlin:kotlin-test")
  testRuntimeOnly("org.jetbrains.kotlin:kotlin-script-runtime")
  testRuntimeOnly("org.jetbrains.kotlin:kotlin-annotations-jvm")

  testImplementation("org.junit.jupiter:junit-jupiter")
  testImplementation("org.junit.platform:junit-platform-commons")
  testImplementation("org.junit.platform:junit-platform-launcher")
  testImplementation("org.junit.platform:junit-platform-runner")
  testImplementation("org.junit.platform:junit-platform-suite-api")
}

tasks.withType<KotlinCompile> {
  kotlinOptions.freeCompilerArgs += listOf(
    "-opt-in=org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi",
  )
}

tasks.withType<Test> {
  useJUnitPlatform()
  systemProperty("idea.ignore.disabled.plugins", "true")
  systemProperty("idea.home.path", project.rootDir)
  doFirst {
    setLibraryProperty("org.jetbrains.kotlin.test.kotlin-stdlib", "kotlin-stdlib")
    setLibraryProperty("org.jetbrains.kotlin.test.kotlin-stdlib-jdk8", "kotlin-stdlib-jdk8")
    setLibraryProperty("org.jetbrains.kotlin.test.kotlin-reflect", "kotlin-reflect")
    setLibraryProperty("org.jetbrains.kotlin.test.kotlin-test", "kotlin-test")
    setLibraryProperty("org.jetbrains.kotlin.test.kotlin-script-runtime", "kotlin-script-runtime")
    setLibraryProperty("org.jetbrains.kotlin.test.kotlin-annotations-jvm", "kotlin-annotations-jvm")

    setLibraryProperty("org.opentest4j.opentest4j", "opentest4j")
    setLibraryProperty("org.junit.jupiter.junit-jupiter-api", "junit-jupiter-api")
    setLibraryProperty("org.junit.platform.junit-platform-commons", "junit-platform-commons")
  }
}

tasks.create<JavaExec>("generateTests") {
  classpath = sourceSets.test.get().runtimeClasspath
  mainClass.set("com.bnorm.power.runners.GenerateTestsKt")
}

tasks.register("sourcesJar", Jar::class) {
  group = "build"
  description = "Assembles Kotlin sources"

  archiveClassifier.set("sources")
  from(sourceSets.main.get().allSource)
  dependsOn(tasks.classes)
}

tasks.register("dokkaJar", Jar::class) {
  group = "documentation"
  description = "Assembles Kotlin docs with Dokka"

  archiveClassifier.set("javadoc")
  from(tasks.dokkaHtml)
  dependsOn(tasks.dokkaHtml)
}

signing {
  setRequired(provider { gradle.taskGraph.hasTask("publish") })
  sign(publishing.publications)
}

publishing {
  publications {
    create<MavenPublication>("default") {
      from(components["java"])
      artifact(tasks["sourcesJar"])
      artifact(tasks["dokkaJar"])

      pom {
        name.set(project.name)
        description.set("Kotlin compiler plugin to enable power assertions in the Kotlin programming language")
        url.set("https://github.com/bnorm/kotlin-power-assert")

        licenses {
          license {
            name.set("Apache License 2.0")
            url.set("https://github.com/bnorm/kotlin-power-assert/blob/master/LICENSE.txt")
          }
        }
        scm {
          url.set("https://github.com/bnorm/kotlin-power-assert")
          connection.set("scm:git:git://github.com/bnorm/kotlin-power-assert.git")
        }
        developers {
          developer {
            name.set("Brian Norman")
            url.set("https://github.com/bnorm")
          }
        }
      }
    }
  }

  repositories {
    if (
      hasProperty("sonatypeUsername") &&
      hasProperty("sonatypePassword") &&
      hasProperty("sonatypeSnapshotUrl") &&
      hasProperty("sonatypeReleaseUrl")
    ) {
      maven {
        val sonatypeUrlProperty = when {
          version.toString().endsWith("-SNAPSHOT") -> "sonatypeSnapshotUrl"
          else -> "sonatypeReleaseUrl"
        }
        setUrl(property(sonatypeUrlProperty) as String)
        credentials {
          username = property("sonatypeUsername") as String
          password = property("sonatypePassword") as String
        }
      }
    }
    maven {
      name = "test"
      url = uri(rootProject.layout.buildDirectory.dir("localMaven"))
    }
  }
}

fun Test.setLibraryProperty(propName: String, jarName: String) {
  val path = project.configurations
    .testRuntimeClasspath.get()
    .files
    .find { """$jarName-\d.*jar""".toRegex().matches(it.name) }
    ?.absolutePath
    ?: return
  systemProperty(propName, path)
}
