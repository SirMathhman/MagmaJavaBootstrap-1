name := "MagmaBootstrap"

version := "1.0"

scalaVersion := "2.12.2"

javacOptions ++= Seq("--enable-preview", "-source", "14")

libraryDependencies += "org.junit.jupiter" % "junit-jupiter-api" % "5.+" % Test
libraryDependencies += "org.junit.jupiter" % "junit-jupiter-engine" % "5.+" % Test