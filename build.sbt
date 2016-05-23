sbtPlugin     := true
organization  := "org.jesusthecat"
name          := "sbt-typings"
version       := "0.1.0"
licenses      +=("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))
scalaVersion  := "2.10.5"
resolvers     ++= Seq(
  Resolver.typesafeRepo("releases"),
  Resolver.sbtPluginRepo("releases"),
  Resolver.sonatypeRepo("releases"),
  Resolver.mavenLocal
)
scalacOptions ++= Seq(
  "-feature",
  "-encoding", "UTF8",
  "-deprecation",
  "-unchecked",
  "-Xlint",
  "-Ywarn-dead-code",
  "-Ywarn-adapted-args"
)
libraryDependencies ++= Seq(
  "org.webjars.npm" % "typescript"        % "1.8.10",
  "org.webjars.npm" % "typings-core"      % "1.0.1",
  // The below are necessary transitive deps that aren't properly resolved.
  "org.webjars.npm" % "write-file-atomic" % "1.1.4",
  // "org.webjars.npm" % "tough-cookie"      % "2.2.1",
  "org.webjars.npm" % "minimatch"         % "2.0.0"
)
addSbtPlugin("com.typesafe.sbt" %% "sbt-js-engine" % "1.1.4")

scriptedSettings
scriptedLaunchOpts <+= version apply { v => s"-Dproject.version=$v" }

// publishMavenStyle := false
// bintrayRepository in bintray := "sbt-plugins"
// bintrayOrganization in bintray := None
// bintrayVcsUrl := Some("git@github.com:ayuudee/sbt-typings.git")
