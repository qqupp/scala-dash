import sbt._

object Dependencies {
  lazy val scalaTest = Seq("org.scalatest" %% "scalatest" % "3.0.8" % "test")

  lazy val scalaCheck = Seq("org.scalacheck" %% "scalacheck" % "1.14.1" % "test")

  lazy val scalaCheckMagnolia = Seq("com.github.chocpanda" %% "scalacheck-magnolia" % "0.3.1" % "test")
  
  val circeVersion = "0.13.0"

  lazy val circe = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser",
    "io.circe" %% "circe-literal",
    "io.circe" %% "circe-optics"
  ).map(_ % circeVersion)

}
