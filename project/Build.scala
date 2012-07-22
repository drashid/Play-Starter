import sbt._
import Keys._
import PlayProject._

import sbt.Defaults._
import sbt.Package.ManifestAttributes

object ApplicationBuild extends Build {

  val appName         = "ProductExplorer"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "com.google.inject" % "guice" % "3.0",
    "com.yammer.metrics" % "metrics-core" % "2.1.2",
    "com.yammer.metrics" % "metrics-guice" % "2.1.2",
    "redis.clients" % "jedis" % "2.1.0"
  )

  val outputPath = artifactPathSetting(artifact);

  val requireOptimizeTask = TaskKey[Unit]("optimizejs")

  val requireJsSettings = requireOptimizeTask <<= outputPath map { (outputPath: File) =>  
    val path = outputPath.getParent() + "/classes/public/" 
    "java -classpath project/js.jar org.mozilla.javascript.tools.shell.Main project/r.js -o project/app.build.js dir=" + path !
  }

  // val requireJsSettings = requireOptimizeTask := {  
  //   "java -classpath project/js.jar org.mozilla.javascript.tools.shell.Main project/r.js -o project/app.build.js" !
  // }

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
    requireJsSettings, 
    (packageBin in Compile) <<= (packageBin in Compile).dependsOn(requireOptimizeTask)
  )

}