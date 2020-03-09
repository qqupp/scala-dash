import sbt._

object Dependencies {
  
  lazy val scalaTest = Seq("org.scalatest" %% "scalatest" % "3.0.8" % "test")

  lazy val scalaCheck = Seq("org.scalacheck" %% "scalacheck" % "1.14.1" % "test")

  lazy val scalaCheckMagnolia = Seq("com.github.chocpanda" %% "scalacheck-magnolia" % "0.3.1" % "test")
  
  lazy val sttp = Seq("com.softwaremill.sttp.client" %% "core" % "2.0.3" % "e2e")

  lazy val diffson = Seq("org.gnieh" %% "diffson-circe" % "4.0.0" % "test")


  val circeVersion = "0.13.0"

  lazy val circe = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser",
    "io.circe" %% "circe-literal",
    "io.circe" %% "circe-optics"
  ).map(_ % circeVersion)

}
