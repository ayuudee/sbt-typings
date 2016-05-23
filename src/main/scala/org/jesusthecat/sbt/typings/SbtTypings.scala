package org.jesusthecat.sbt.typings

import sbt._
import sbt.Keys._
import com.typesafe.sbt.web._
import com.typesafe.sbt.jse.SbtJsTask
import com.typesafe.sbt.jse.SbtJsEngine
import com.typesafe.sbt.web.pipeline.Pipeline

import scala.concurrent.duration._
import java.io.File


object Import {

  object TypingsKeys {

    val appDir = SettingKey[File]("typings-dir", "The directory containing typings.json")

    val prod = SettingKey[Boolean]("typings-prod", "Install production deps only?")

    // TODO (AD): How do I delegate both typings and typingsStage to use runTypings?
    val typings  = TaskKey[Unit]("typings", "Invoke Typings#install")

    val typingsStage  = TaskKey[Pipeline.Stage]("typings-stage", "Invoke Typings#install as a pipeline stage")

  }

}

object SbtTypings extends AutoPlugin {

  override def requires = SbtJsTask

  override def trigger = AllRequirements

  val autoImport = Import

  import SbtWeb.autoImport._
  import WebKeys._
  import SbtJsTask.autoImport.JsTaskKeys._
  import autoImport.TypingsKeys._
  import SbtJsEngine.autoImport._
  import JsEngineKeys._

  override def projectSettings = Seq(
    appDir       := baseDirectory.value,
    prod         := true,
    typingsStage := runTypings.dependsOn(nodeModules in Plugin).value,
    typings      := runTypings.dependsOn(nodeModules in Plugin).value(Nil)
  )

  def runTypings = Def.task { (mx: Seq[(File,String)]) =>
    val shellSource =
      SbtWeb.copyResourceTo(
        (target in Plugin).value / moduleName.value,
        getClass.getClassLoader.getResource("install.js"),
        streams.value.cacheDirectory / "copy-resource"
      )

    // Hack.
    // https://github.com/sbt/sbt-web/issues/125
    //
    val f1 = (webJarsNodeModulesDirectory in Plugin).value / "Jeremy Stashewsky"
    val f2 = (webJarsNodeModulesDirectory in Plugin).value / "tough-cookie"
    if (f1.exists && !f2.exists) {
      streams.value.log.info(s"Found misnamed NPM package. Moving '$f1' to '$f2'.")
      IO.move(f1, f2)
    }

    val typingsF = appDir.value / "typings.json"
    if (!typingsF.exists) {
      streams.value.log.warn(s"No typings configuration at $typingsF. Add one?")
    } else {
      streams.value.log.info(s"Found typings.json in $appDir. Running ...")
      SbtJsTask.executeJs(
        (state in typings).value,
        (engineType in typings).value,
        (command in typings).value,
        (nodeModuleDirectories in Plugin).value.map(_.getCanonicalPath),
        shellSource,
        Seq(
          (appDir in typings).value.getPath,
          (prod in typings).value.toString),
        30.seconds)
    }
    mx
  }

}
