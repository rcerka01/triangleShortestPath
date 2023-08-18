ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "triangles"
  )

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % "3.5.1",
  "co.fs2" %% "fs2-core" % "3.8.0",
  "org.scalatest" %% "scalatest" % "3.2.16" % "test"
)
