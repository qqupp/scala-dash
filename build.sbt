import Dependencies._

ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.github"
ThisBuild / organizationName := "qqupp"

lazy val root = (project in file("."))
  .configs(EndToEndTest)
  .settings(
    name := "scaladash",
    libraryDependencies ++= circe ++ scalaCheck ++ scalaCheckMagnolia ++ scalaTest,
    EndToEndTestSettings
  )
  .enablePlugins(DockerComposePlugin)

lazy val EndToEndTest = config("e2e").extend(Test)
val EndToEndTestSettings =
  inConfig(EndToEndTest)(Defaults.testSettings) ++ Seq(
    EndToEndTest / fork := true,
    EndToEndTest / parallelExecution := false,
    libraryDependencies ++= sttp
  )


// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
