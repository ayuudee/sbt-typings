import org.jesusthecat.sbt.typings.SbtTypings.autoImport._
lazy val root = (project in file(".")).
  enablePlugins(SbtWeb).
  settings(
    TypingsKeys.typingsFile := baseDirectory.value / "typings.json"
  )
