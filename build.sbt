name := """play24-scala-template"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  evolutions,
  specs2 % Test,
  "mysql" % "mysql-connector-java" % "5.1.39",
  "com.h2database"  %  "h2"                           % "1.4.192",
  "org.scalikejdbc" %% "scalikejdbc"                  % "2.4.2",
  "org.scalikejdbc" %% "scalikejdbc-config"           % "2.4.2",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.5.1"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

scalikejdbcSettings

