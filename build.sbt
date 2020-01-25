scalaVersion := "2.12.7"

organization := "com.example"

lazy val `fs2-toy` = (project in file("."))
  .settings(
    name := "FS2 Toy"
  )

libraryDependencies += "co.fs2" %% "fs2-core" % "2.2.1"
libraryDependencies += "org.specs2" %% "specs2-core" % "4.8.3" % Test
libraryDependencies += "org.specs2" %% "specs2-mock" % "4.8.3" % Test

