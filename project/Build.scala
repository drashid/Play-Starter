import sbt._
import Keys._
import PlayProject._

import sbt.Defaults._
import sbt.Package.ManifestAttributes

object ApplicationBuild extends Build {

  val appName         = "Play-Starter"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "com.google.inject" % "guice" % "3.0",
    "com.google.inject.extensions" % "guice-assistedinject" % "3.0",
    "com.yammer.metrics" % "metrics-core" % "2.1.2",
    "com.yammer.metrics" % "metrics-guice" % "2.1.2",
    "redis.clients" % "jedis" % "2.1.0"
  )

  val requireOptimizeTask = TaskKey[Unit]("optimizejs")

  val requireJsSettings = requireOptimizeTask := {   
    "node project/r.js -o project/app.build.js" !
  }

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
    requireJsSettings
  )

}