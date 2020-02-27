import Dependencies._

ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.github"
ThisBuild / organizationName := "qqupp"

lazy val root = (project in file("."))
  .settings(
    name := "scaladash",
    libraryDependencies ++= circe ++ scalaCheck ++ scalaTest
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
