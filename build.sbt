name := """backprop"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.4"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
// routesGenerator := InjectedRoutesGenerator

fork in run := true

libraryDependencies ++= Seq(
  guice,
  "org.mockito" % "mockito-core" % "1.9.5" % Test,
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.1" % Test,
)
