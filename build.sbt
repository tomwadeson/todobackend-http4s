lazy val root = (project in file(".")).
  settings(
    name := "todobackend-http4s"
  )

version := "0.1"
scalaVersion := "2.11.8"

val http4sVersion = "0.14.1a"
val circeVersion = "0.4.1"
val logbackVersion = "1.1.7"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,

  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,

  "ch.qos.logback" % "logback-classic" % logbackVersion,

  "org.scalatest" %% "scalatest" % "2.2.6" % Test
)
