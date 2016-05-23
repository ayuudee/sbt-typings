sbt-typings
==========

A very simple [sbt-web] plugin for installing TypeScript [typings](https://github.com/typings/typings) definitions.

Largely based off the [install command](https://github.com/typings/typings/blob/master/docs/commands.md).

Add plugin
----------

```scala
addSbtPlugin("org.jesusthecat" % "sbt-typings" % "0.1.0")
```

Your project's build file also needs to enable sbt-web plugins. For example with build.sbt:

    lazy val root = (project in file(".")).enablePlugins(SbtWeb)

Configuration options
-------------

Option              | Description
--------------------|------------
appDir              | [Typings](https://github.com/typings/typings) expects to find typings.json in the root of this directory.
prod                | Install only production dependencies (omits dev dependencies)

For example, to enable `prod`

```scala
TypingsKeys.prod in Assets := true
```

Commands
--------------

Command             | Description
--------------------|------------
typings             | Grab project typings
typingsStage        | As per `typings` but as a pipeline-stage


See the file LICENCE for copying permission.
