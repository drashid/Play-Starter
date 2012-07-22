import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

  val appName         = "ProductExplorer"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "com.google.inject" % "guice" % "3.0",
    "com.yammer.metrics" % "metrics-core" % "2.1.2",
    "com.yammer.metrics" % "metrics-guice" % "2.1.2",
    "redis.clients" % "jedis" % "2.1.0"
    // ,
    // "rhino" % "js" % "1.7R2"
  )

  val requireOptimizeTask = TaskKey[Unit]("optimizejs")

  val requireJsSettings = requireOptimizeTask := {
    "java -classpath project/rhino.jar org.mozilla.javascript.tools.shell.Main project/r.js -o project/app.build.js" !
  }

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
    requireJsSettings, 
    (packageBin in Compile) <<= (packageBin in Compile).dependsOn(requireOptimizeTask)
  )

}