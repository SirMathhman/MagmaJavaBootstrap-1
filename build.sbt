name := "MagmaJavaBootstrap"

version := "0.1"

scalaVersion := "2.13.2"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.+"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.+"

libraryDependencies += "com.google.inject" % "guice" % "4.+"

libraryDependencies += "org.junit.jupiter" % "junit-jupiter-api" % "5.+" % Test
libraryDependencies += "org.junit.jupiter" % "junit-jupiter-engine" % "5.+" % Test