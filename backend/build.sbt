name := """fdgfd"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.15"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
libraryDependencies += "org.mindrot" % "jbcrypt" % "0.4"

// libraryDependencies ++= Seq(
//   "org.postgresql" % "postgresql" % "42.3.1",
// )

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "org.postgresql" % "postgresql" % "42.5.1"
)

resolvers += "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/"
dependencyOverrides += "org.scala-lang.modules" %% "scala-xml" % "2.2.0"
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
